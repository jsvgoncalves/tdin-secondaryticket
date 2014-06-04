package controller;

import java.io.IOException;

import javax.swing.UIManager;

import network.NetworkManager;
import resources.Icons;
import view.ApplicationFrame;

public class Main {

	public static void main(String[] args) throws IOException {
		  try {
			UIManager.setLookAndFeel(
			            UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		  
		Icons.initResources();
		
		
		DataStore store = DataStore.getInstance(null);
		
		store.manager = NetworkManager.createNetworkManager(null, null);
		
		if( store.fetchDepartments() )
			store.writeStore(null);
		
		SyncController.getInstance().start();
		
		new ApplicationFrame();
	}

}
