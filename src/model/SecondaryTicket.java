package model;

import org.json.JSONObject;

import resources.JSONHelper;

public class SecondaryTicket {
	
	private String ID, ticketID, departmentID, status, created, modified, title, description, reply;
	private Ticket associatedTicket = null;

	
	public SecondaryTicket(JSONObject obj)
	{
		this.ID = JSONHelper.getString(obj, "id", "");
		this.ticketID = JSONHelper.getString(obj, "ticket_id", "");
		this.departmentID = JSONHelper.getString(obj, "department_id", "");
		this.status = JSONHelper.getString(obj, "status", "");
		this.created = JSONHelper.getString(obj, "created", "");
		this.modified = JSONHelper.getString(obj, "modified", "");
		this.title = JSONHelper.getString(obj, "title", "");
		this.description = JSONHelper.getString(obj, "description", "");
		this.reply = JSONHelper.getString(obj, "reply", "");
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


	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
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

	@Override
	public boolean equals(Object obj) {
		
		if( obj == null )
			return false;
		
		SecondaryTicket sec = (SecondaryTicket)obj;
		
		return sec.ID != null && this.ID != null && this.ID.equals(sec.ID);
	}

}
