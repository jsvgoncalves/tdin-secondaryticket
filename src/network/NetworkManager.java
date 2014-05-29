package network;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.logging.Level;

import model.Department;

import org.json.*;

import com.sun.istack.internal.logging.Logger;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class NetworkManager {


    private static final String host = "http://ticket.algumavez.com";
    private static final String URL_DEPARTMENTS = "/departments.json";
    
    
    private static final String DEPARTMENTS_KEY = "departments";
    private static final String DEPARTMENT_KEY = "Department";
    
    private static final int TIMEOUT = 30000;

    private String networkLogin = null;

    private NetworkManager(String username, String password)
    {
    	this.networkLogin = Base64.encode( (username + ":" + password).getBytes(Charset.forName("UTF-8")));
    }



    private JSONObject _performGetRequest(String url, String method)
    {

        try {
            URL _url = new URL(host + url);
            
            System.out.println(_url);


            HttpURLConnection c = (HttpURLConnection) _url.openConnection();
            c.setRequestMethod(method);
            c.setRequestProperty("Content-length", "0");
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);
            c.setConnectTimeout(TIMEOUT);
            c.setReadTimeout(TIMEOUT);
            c.connect();
            int status = c.getResponseCode();

            switch (status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line+"\n");
                    }
                    br.close();

                    String out = sb.toString();
                    System.out.println("Received: '" + out + "'");
                    
                    return new JSONObject(out);
            }

        } catch (MalformedURLException ex) {
            Logger.getLogger(NetworkManager.class).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NetworkManager.class).log(Level.SEVERE, null, ex);
        }
        return null;


    }


    public boolean getDepartmentsList(ArrayList<Department> fillList)
    {
    	if( fillList == null )
    		return false;
    	
    	try {
    		JSONObject obj = _performGetRequest(URL_DEPARTMENTS, "GET");
    		
    		if(    obj != null 
    			&& obj.has(DEPARTMENTS_KEY) )
    		{
        		JSONArray array = obj.getJSONArray(DEPARTMENTS_KEY);
        	    			
    			fillList.clear();

    			for( int i = 0; i < array.length(); i++)
    			{
    				try {
    					JSONObject dep = array.getJSONObject(i).getJSONObject(DEPARTMENT_KEY);
    					fillList.add(new Department(dep));
    				} catch(Exception ex) {
    				}
    			}
    		}
    		
    		return true;
    		
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

