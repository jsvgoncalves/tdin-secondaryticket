package controller;

import java.util.ArrayList;

import javax.swing.UIManager;

import model.Department;
import network.NetworkManager;
import resources.Icons;
import view.ApplicationFrame;

public class Main {

	public static void main(String[] args) {
		  try {
			UIManager.setLookAndFeel(
			            UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		  
		Icons.initResources();
		
		
		NetworkManager netMgr = NetworkManager.createNetworkManager(null, null);
	
		ArrayList<Department> list = new ArrayList<Department>();
		DataStore store = DataStore.getInstance();
		
		store.manager = netMgr;
		
		netMgr.getDepartmentsList(list);
		
		for(Department d : list)
		{
			if( d != null && d.getId() != null)
				store.departments.put(d.getId(), d);
		}
		
		new ApplicationFrame();
	}

}
