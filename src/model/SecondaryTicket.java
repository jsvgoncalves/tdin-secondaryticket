package model;

import java.io.Serializable;

import org.json.JSONObject;

import resources.JSONHelper;

public class SecondaryTicket implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4983561656489887289L;
	
	
	
	private String ID, ticketID, departmentID, created, modified, title, description, reply;
	private int status;
	private Ticket associatedTicket = null;
	
	
	public static enum STATUS {
		WAITING(2),
		SOLVED(3);
		
		public final int CODE;
		private STATUS(int status)
		{
			this.CODE = status;
		}
	};

	
	public SecondaryTicket(JSONObject obj)
	{
		this.ID = JSONHelper.getString(obj, "id", "").trim();
		this.ticketID = JSONHelper.getString(obj, "ticket_id", "").trim();
		this.departmentID = JSONHelper.getString(obj, "department_id", "").trim();
		try {
			this.status = Integer.valueOf( JSONHelper.getString(obj, "status", "0").trim() );
		} catch(NumberFormatException e) {
			this.status = STATUS.WAITING.CODE;
		}
		this.created = JSONHelper.getString(obj, "created", "").trim();
		this.modified = JSONHelper.getString(obj, "modified", "").trim();
		this.title = JSONHelper.getString(obj, "title", "").trim();
		this.description = JSONHelper.getString(obj, "description", "").trim();
		this.reply = JSONHelper.getString(obj, "reply", "").trim();
	}
	
	
	public String getID()
	{
		return ID;
	}
	public void setID(String ID)
	{
		this.ID = ID;
	}
	

	public String getDepartmentID() {
		return departmentID;
	}
	public void setDepartmentID(String departmentID) {
		this.departmentID = departmentID;
	}


	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}


	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}


	public String getModified() {
		return modified;
	}
	public void setModified(String modified) {
		this.modified = modified;
	}


	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}


	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}


	public String getTicketID() {
		return ticketID;
	}
	public void setTicketID(String ticketID) {
		this.ticketID = ticketID;
	}
	
	
	public Ticket getAssociatedTicket() {
		return associatedTicket;
	}

	public void setAssociatedTicket(Ticket associatedTicket) {
		this.associatedTicket = associatedTicket;
	}
	
	
	
	public String getSubmitedBy()
	{
		if(associatedTicket == null)
			return "Unknow";
		
		return String.format("%s <%s>", associatedTicket.getUserName(), associatedTicket.getUserEmail());
	}
	
	public String getSolver()
	{
		if(associatedTicket == null)
			return "Unknow";
		
		return String.format("%s <%s>", associatedTicket.getSolverName(), associatedTicket.getSolverEmail());
	}

	@Override
	public boolean equals(Object obj) {
		
		if( obj == null )
			return false;
		
		SecondaryTicket sec = (SecondaryTicket)obj;
		
		return sec.ID != null && this.ID != null && this.ID.equals(sec.ID);
	}

}
