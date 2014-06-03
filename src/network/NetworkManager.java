package network;


import java.io.BufferedReader;
import java.io.IOException;
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

import org.json.*;

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
    
    private static final String URL_SEC_TICKETS = "/department/secondaryTickets.json";
    
    
    private static final String DEPARTMENTS_KEY = "departments";
    private static final String DEPARTMENT_KEY = "Department";
    
    private static final String SEC_TICKETS_KEY = "secondaryTickets";
   
    
    
    private static final String CHARTSET = "UTF-8";
    
    private static final int TIMEOUT = 30000;

    private String networkLogin = null;

    private NetworkManager(String username, String password)
    {
    	this.networkLogin = Base64.encode( (username + ":" + password).getBytes(Charset.forName(CHARTSET)));
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
            		
            		sb.append(URLEncoder.encode(e.getKey(), CHARTSET));
            		sb.append("=");
            		sb.append(URLEncoder.encode(e.getValue(), CHARTSET));
            		
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
            c.setAllowUserInteraction(false);
            c.setConnectTimeout(TIMEOUT);
            c.setReadTimeout(TIMEOUT);
            
            if( networkLogin != null )
            	c.setRequestProperty ("Authorization", "Basic " + networkLogin);
            
            if( isSimple )
            	c.setRequestProperty("Content-length", "0");
            
            else
            {
            	c.setRequestProperty("Accept-Charset", CHARTSET);
            	c.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + CHARTSET);
            	
            	if( query != null )
            	{
                	c.setDoOutput(true); // Triggers POST.
                	
                    try (OutputStream output = c.getOutputStream()) {
                        output.write(query.getBytes(CHARTSET));
                    }
            	}
            }
            
            c.setRequestMethod(method.toUpperCase());

            c.connect();
            
            
            response.statusCode = c.getResponseCode();
            response.contentType = c.getContentType();
            
            System.out.println(response.contentType);
            
//            for(Entry<String, List<String>> e : c.getHeaderFields().entrySet())
//            {
//            	System.out.println(e.getKey() + ": " + e.getValue());
//            }
            
            BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line+"\n");
            }
            br.close();
            response.rawResponse = sb.toString();
            
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
    private NetworkResponse _performSimpleRequest(String url, String method, Map<String, String> params)
    {
    	return _performRequest(url, true, method, params);
    }
    
    
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
     			&& res.jsonResponse.has(DEPARTMENTS_KEY) )
    		{
        		JSONArray array = res.jsonResponse.getJSONArray(DEPARTMENTS_KEY);
        	    			
    			fillList.clear();

    			for( int i = 0; i < array.length(); i++)
    			{
    				try {
    					JSONObject dep = array.getJSONObject(i).getJSONObject(DEPARTMENT_KEY);
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
    
    
    
    
    public boolean getDepartmentTickets(ArrayList<SecondaryTicket> fillList, String departament, long lastSync)
    {
    	if( fillList == null )
    		return false;
    	
    	try {
    		HashMap<String, String> query = new HashMap<String, String>();
    		query.put("department", departament);
    		query.put("lastTime", "" + lastSync);
    		
    		NetworkResponse res = _performSimpleRequest(URL_SEC_TICKETS, "GET", query);
     		if(    res.ok
         		&& res.jsonResponse != null
	    		&& res.jsonResponse.has(SEC_TICKETS_KEY) )
    		{
        		JSONArray array = res.jsonResponse.getJSONArray(SEC_TICKETS_KEY);
        	    			
    			fillList.clear();

    			for( int i = 0; i < array.length(); i++)
    			{
    				try {
    					JSONObject s_tick = array.getJSONObject(i);
    					fillList.add(new SecondaryTicket(s_tick));
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

