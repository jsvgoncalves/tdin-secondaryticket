package controller;

import java.io.Serializable;
import java.util.HashMap;

import network.NetworkManager;

import model.Department;
import model.Ticket;

public class DataStore implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6341947420833999298L;
	
	public static final String DEPARTMENT_PASSWORD_KEY = "123456";
	
	private static DataStore _instance = null;
	
	
	
	//key = uuid
	public transient HashMap<String, Ticket> tickets = new HashMap<String, Ticket>();
	public transient HashMap<String, Department> departments = new HashMap<String, Department>();
	public transient NetworkManager manager = null;

	
	
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
