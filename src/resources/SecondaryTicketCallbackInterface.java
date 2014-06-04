package resources;

import model.SecondaryTicket;

public interface SecondaryTicketCallbackInterface {
	
	public void clearAll();
	public void appendSecondaryTicket(SecondaryTicket ticket);
}
