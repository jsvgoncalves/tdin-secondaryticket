package controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import network.NetworkManager;

import model.Department;
import model.SecondaryTicket;

public class DataStore implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6341947420833999298L;
	
	
	public static final String DEPARTMENT_PASSWORD_KEY = "123456";
	private static DataStore _instance = null;
	
	
	public transient HashMap<String, Department> departments = new HashMap<String, Department>();
	public transient NetworkManager manager = null;
	public transient String currentLoggedDepartment = null;
	public transient HashMap<String,SecondaryTicket> secTicketsDB = new HashMap<String, SecondaryTicket>();
	
	
	public String lastSyncDate = null;
	

	public HashMap<String, String> departmentsSyncDate = new HashMap<String, String>();
	public HashMap<String, List<SecondaryTicket>> secTickets = new HashMap<String, List<SecondaryTicket>>();
	


	
	
	private DataStore()
	{
		
	}
	
	
	public static DataStore getInstance()
	{
		if( _instance == null )
		{
			_instance = new DataStore();
		}
		
		return _instance;
	}
	
}
