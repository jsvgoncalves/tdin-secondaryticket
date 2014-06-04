package network;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

import model.Department;
import model.SecondaryTicket;
import model.Ticket;

import org.json.*;

import resources.JSONHelper;

import com.sun.istack.internal.logging.Logger;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class NetworkManager {
	
	
	private static class NetworkResponse {
		int statusCode = -1;
		String contentType = null;
		String rawResponse = null;
		JSONObject jsonResponse = null;
		boolean ok = false;
	}


    private static final String host = "http://ticket.algumavez.com";
    private static final String URL_DEPARTMENTS = "/departments.json";
    
    private static final String URL_SEC_TICKETS = "/secondary_tickets/getLastTickets/%s/%s.json";
    
    
    private static final String DEPARTMENT_LIST_KEY = "departments";
    private static final String DEPARTMENT_OBJECT_KEY = "Department";
    
    private static final String SEC_TICKETS_LIST_KEY = "secondaryTickets";
    
    private static final String SEC_TICKET_OBJECT_KEY = "SecondaryTicket";
    private static final String SEC_TICKET_OBJECT_TICKET_KEY = "Ticket";
    
    private static final String SEC_TICKET_SYNC_KEY = "request_date";    
    
    private static final String DEFAULT_CHARTSET = "UTF-8";
    
    private static final Charset CHARSET = Charset.forName(DEFAULT_CHARTSET);
    
    		
    
    private static final int TIMEOUT = 30000;

    private String networkLogin = null;

    private NetworkManager(String username, String password)
    {
    	this.networkLogin = Base64.encode( (username + ":" + password).getBytes(CHARSET));
    }



    
    
    
    private NetworkResponse _performRequest(String url, boolean isSimple, String method, Map<String, String> params)
    {
    	NetworkResponse response = new NetworkResponse();
    	
        try {
            URL _url = null;
            String query = null;
            
            if(params != null && !params.isEmpty())
            {
            	StringBuilder sb = new StringBuilder("?");
            	
            	int _count = 0;
            	
            	for(Entry<String, String> e: params.entrySet())
            	{
            		if( _count > 0 )
            			sb.append("&");
            		
            		sb.append(URLEncoder.encode(e.getKey(), DEFAULT_CHARTSET));
            		sb.append("=");
            		sb.append(URLEncoder.encode(e.getValue(), DEFAULT_CHARTSET));
            		
            		_count++;            		
            	}
            	query = sb.toString();
            }
            
            if( !isSimple || query == null )
            	_url = new URL(host + url);
            else
            	_url = new URL(host + url + query);
            
            System.out.println(_url);


            HttpURLConnection c = (HttpURLConnection) _url.openConnection();
            
            c.setUseCaches(false);
            c.setDoInput(true);
            c.setAllowUserInteraction(false);
            c.setConnectTimeout(TIMEOUT);
            c.setReadTimeout(TIMEOUT);
            
            if( networkLogin != null )
            	c.setRequestProperty ("Authorization", "Basic " + networkLogin);
            
            if( isSimple )
            	c.setRequestProperty("Content-length", "0");
            
            else
            {
            	c.setRequestProperty("Accept-Charset", DEFAULT_CHARTSET);
            	c.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + DEFAULT_CHARTSET);
            	
            	if( query != null )
            	{
                	c.setDoOutput(true); // Triggers POST.
                	
                    try (OutputStream output = c.getOutputStream()) {
                        output.write(query.getBytes(DEFAULT_CHARTSET));
                    }
            	}
            }
            
            c.setRequestMethod(method.toUpperCase());

            c.connect();
            
            
            response.statusCode = c.getResponseCode();
            response.contentType = c.getContentType();
            
            System.out.println("[" + response.statusCode + "] " + response.contentType);
            
//            for(Entry<String, List<String>> e : c.getHeaderFields().entrySet())
//            {
//            	System.out.println(e.getKey() + ": " + e.getValue());
//            }
            InputStream is = null;
            
            if (response.statusCode >= 400) {
                is = c.getErrorStream();
            } else {
                is = c.getInputStream();
            }
            
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line+"\n");
            }
            br.close();
            response.rawResponse = sb.toString();
            
            //System.out.println(response.rawResponse);
            
            try {
	            String[] ctype = response.contentType.split("\\;");
	            if( ctype.length > 0 )
	            {
	            	if( ctype[0].trim().equalsIgnoreCase("application/json") )
	            	{
	            		response.jsonResponse = new JSONObject(response.rawResponse);
	            	}
	            }
            } catch(Exception ex) {
            }
            
            
            switch (response.statusCode) {
            case 200:
            case 201:
            	response.ok = true;
            	break;
            }

        } catch (MalformedURLException ex) {
            Logger.getLogger(NetworkManager.class).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NetworkManager.class).log(Level.SEVERE, null, ex);
        }
        return response;
    }
    
    
    
    
    
    
    private NetworkResponse _performSimpleRequest(String url, String method)
    {
    	return _performRequest(url, true, method, null);
    }
    @SuppressWarnings("unused")
	private NetworkResponse _performSimpleRequest(String url, String method, Map<String, String> params)
    {
    	return _performRequest(url, true, method, params);
    }
    
    
    @SuppressWarnings("unused")
	private NetworkResponse _performContentRequest(String url, String method)
    {
    	return _performRequest(url, false, method, null);
    }
    private NetworkResponse _performContentRequest(String url, String method, Map<String, String> params)
    {
    	return _performRequest(url, false, method, params);
    }
    
    
    
    
    
    


    public boolean getDepartmentsList(ArrayList<Department> fillList)
    {
    	if( fillList == null )
    		return false;
    	
    	try {
    		
    		NetworkResponse res = _performSimpleRequest(URL_DEPARTMENTS, "GET");
    		
     		if(    res.ok
     			&& res.jsonResponse != null
     			&& res.jsonResponse.has(DEPARTMENT_LIST_KEY) )
    		{
        		JSONArray array = res.jsonResponse.getJSONArray(DEPARTMENT_LIST_KEY);
        	    			
    			fillList.clear();

    			for( int i = 0; i < array.length(); i++)
    			{
    				try {
    					JSONObject dep = array.getJSONObject(i).getJSONObject(DEPARTMENT_OBJECT_KEY);
    					fillList.add(new Department(dep));
    				} catch(Exception ex) {
    				}
    			}
    			
	    		return true;
    		}
    		
    	} catch(Exception ex) {
    		Logger.getLogger(NetworkManager.class).log(Level.SEVERE, null, ex);
    	}
    	return false;
    }
    
    
    
    
    public String getDepartmentTickets(ArrayList<SecondaryTicket> secTicketList, String departamentID, String lastSyncDate)
    {
    	if( secTicketList == null )
    		return null;
    	
    	try {
    		
    		String url = String.format(URL_SEC_TICKETS, departamentID,
    														Base64.encode(lastSyncDate.getBytes(CHARSET)));
    		String lastSync = null;
    		
    		NetworkResponse res = _performSimpleRequest(url, "GET");
     		if(    res.ok
         		&& res.jsonResponse != null
	    		&& res.jsonResponse.has(SEC_TICKETS_LIST_KEY) )
    		{
     			lastSync = JSONHelper.getString(res.jsonResponse, SEC_TICKET_SYNC_KEY);
        		JSONArray array = res.jsonResponse.getJSONArray(SEC_TICKETS_LIST_KEY);
        	    			
        		secTicketList.clear();
        		
        		

    			for( int i = 0; i < array.length(); i++)
    			{
    				try {
    					JSONObject tick = array.getJSONObject(i);
    					
    					SecondaryTicket lastSecTicket = null;
    					
    					if( tick.has(SEC_TICKET_OBJECT_KEY) )
    					{
    						lastSecTicket = new SecondaryTicket(tick.getJSONObject(SEC_TICKET_OBJECT_KEY));
    						secTicketList.add(lastSecTicket);
    					}

    					if(    tick.has(SEC_TICKET_OBJECT_TICKET_KEY)
    						&& lastSecTicket != null )
    					{
    						lastSecTicket.setAssociatedTicket(
    											new Ticket(tick.getJSONObject(SEC_TICKET_OBJECT_TICKET_KEY)) );
    					}
    				} catch(Exception ex) {
    				}
    			}
	    		
	    		return lastSync;
    		}
    		
    	} catch(Exception ex) {
    		Logger.getLogger(NetworkManager.class).log(Level.SEVERE, null, ex);
    	}
    	
    	return null;
    }
    
    public boolean registerDepartment(String departament, String solver, String description, String key)
    {
    	
    	try {
    		HashMap<String, String> query = new HashMap<String, String>();
    		query.put("department", departament);
    		query.put("solverName", solver);
    		query.put("description", description);
    		query.put("departmentKey", key);
    		
    		NetworkResponse res = _performContentRequest(URL_DEPARTMENTS, "POST", query);
    		
    		return res.ok;
    		
    	} catch(Exception ex) {
    		Logger.getLogger(NetworkManager.class).log(Level.SEVERE, null, ex);
    	}
    	
    	return false;
    }
    
    


    public static NetworkManager createNetworkManager(String username, String password)
    {
    	return new NetworkManager(username, password);
    }



}

