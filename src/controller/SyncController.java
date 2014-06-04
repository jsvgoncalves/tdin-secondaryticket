package controller;

import view.ApplicationFrame;

public class SyncController extends Thread {
	
	public static final long SLEEP_TIME_MILLIS = 5000;
	
	private DataStore store = null;
	
	private static SyncController instance = null;
	public boolean running = true;
	
	private SyncController()
	{
	}
	
	
	public static SyncController getInstance()
	{
		if( instance == null )
			instance = new SyncController();
		
		return instance;
	}
	
	
	public void run()
	{
		if( store == null )
			store = DataStore.getInstance();
		
		while( running )
		{
			if( store.currentLoggedDepartment != null )
			{ 
				synchronized (store.getSyncMutex()) {
					
					if( store.fetchDepartmentTickets(ApplicationFrame.getSecondaryTicketListener()) )
					{
						ApplicationFrame.redraw();
					}
				}
			}
			
			try {
				Thread.sleep(SLEEP_TIME_MILLIS);
			} catch (InterruptedException e) {
				running = false;
				e.printStackTrace();
			}
		}
		
	}
	

}
