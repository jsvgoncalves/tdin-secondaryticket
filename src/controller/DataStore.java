package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import resources.SecondaryTicketCallbackInterface;

import com.sun.istack.internal.logging.Logger;

import network.NetworkManager;

import model.Department;
import model.SecondaryTicket;
import model.SecondaryTicket.STATUS;

public class DataStore {	
	
	public static final String DATASTORE_DEFAULT_FILE = "datastore.db";
	public static final String DEPARTMENT_PASSWORD_KEY = "123456";
	private static DataStore _instance = null;
	
	public static final int DEBUG_LEVEL = 1;
		
	private transient final Object syncMutex = new Object();
	
	public transient NetworkManager manager = null;
	public transient String currentLoggedDepartment = null;
	public transient HashMap<String,SecondaryTicket> secTicketsDB = new HashMap<String, SecondaryTicket>();
	

	public HashMap<String, Department> departments = null;
	public HashMap<String, String> departmentsSyncDate = null;
	public HashMap<String, List<SecondaryTicket>> secTickets = null;

	
	
	private DataStore()
	{
		departments = new HashMap<String, Department>();
		departmentsSyncDate = new HashMap<String, String>();
		secTickets = new HashMap<String, List<SecondaryTicket>>();
	}
	
	@SuppressWarnings("unchecked")
	private DataStore(ObjectInputStream iis) throws ClassNotFoundException, IOException
	{
		this.departments = (HashMap<String, Department>) iis.readObject();
		this.departmentsSyncDate = (HashMap<String, String>) iis.readObject();
		this.secTickets = (HashMap<String, List<SecondaryTicket>>) iis.readObject();
		
		for(List<SecondaryTicket> l: this.secTickets.values())
		{
			if( l != null )
			{
				for(SecondaryTicket s: l)
				{
					this.secTicketsDB.put(s.getID(), s);
				}
			}
		}
	}
	
	

	
	public void writeStore(String filename)
	{
		if( filename == null )
			filename = DATASTORE_DEFAULT_FILE;
		
		try (FileOutputStream out = new FileOutputStream(filename);
			 ObjectOutputStream oout = new ObjectOutputStream(out))
		{
			oout.writeObject(departments);
			oout.writeObject(departmentsSyncDate);
			oout.writeObject(secTickets);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public Object getSyncMutex()
	{
		return this.syncMutex;
	}
	public boolean fetchDepartmentTickets(SecondaryTicketCallbackInterface callback)
	{
		if( manager == null )
			return false;
		
		ArrayList<SecondaryTicket> newSecTickets = new ArrayList<SecondaryTicket>();
		
		// get last department sync
		String lastSync = departmentsSyncDate.get(this.currentLoggedDepartment);
		
		if( lastSync == null )
			lastSync = "0";
		
		try {
			// get new tickets and current sync date
			String newSync = this.manager.getDepartmentTickets(newSecTickets, this.currentLoggedDepartment, lastSync);
			
			// save current sync date
			if( newSync != null )
				departmentsSyncDate.put(this.currentLoggedDepartment, newSync);
			
			
			// get old department ticket list
			List<SecondaryTicket> list = this.secTickets.get( this.currentLoggedDepartment );
			
			// if there is none, create it
			if( list == null )
			{
				list = new ArrayList<SecondaryTicket>();
				this.secTickets.put( this.currentLoggedDepartment, list );
			}
			
			long count = 0;
			
			for(SecondaryTicket s : newSecTickets)
			{
				if( s.getStatus() != STATUS.WAITING.CODE )
					continue;
				
				// only add if its not already on list
				if( this.secTicketsDB.put(s.getID(), s) == null )
				{
					count++;
					list.add(s);
					
					if( callback != null )
						callback.appendSecondaryTicket(s);
				}
			}
			
			if( count > 0 )
				this.writeStore(null);
			
			return count > 0;
		} catch(Exception ex) {
			Logger.getLogger(DataStore.class).log(Level.SEVERE, null, ex);
		}
		
		return false;
	}
	
	public boolean fetchDepartments()
	{
		if( manager == null )
			return false;
		
		ArrayList<Department> list = new ArrayList<Department>();
		int count = 0;
		
		if( manager.getDepartmentsList(list) )
		{
			for(Department d : list)
			{
				if( d != null && d.getId() != null)
				{
					count++;
					this.departments.put(d.getId(), d);
				}
			}
		}
		return count > 0;
	}
	
	
	public static DataStore getInstance()
	{
		if( _instance == null )
			_instance = new DataStore();
		
		return _instance;
	}
	

	public static DataStore getInstance(String filename) throws IOException
	{
		_instance = null;
		
		if( filename == null )
			filename = DATASTORE_DEFAULT_FILE;
		
		try (FileInputStream is = new FileInputStream(filename);
			 ObjectInputStream iis = new ObjectInputStream(is))
		{

//			DataStore store = (DataStore) iis.readObject();
//			store.manager = null;
//			store.currentLoggedDepartment = null;
//			store.secTicketsDB = new HashMap<String, SecondaryTicket>();
//			store.syncMutex = new Object();
			
			_instance = new DataStore(iis);
		} catch (FileNotFoundException e) {
			System.out.println("DataStore does not exist, instanciating new");
			_instance = new DataStore();	
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return _instance;
	}
}
