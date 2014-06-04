package model;

import org.json.JSONObject;

import resources.JSONHelper;

public class Ticket {
	
	private String Name, Email, Title, Date, ID, Description;

	public Ticket(String name, String email, String title, String date, String ID, String description)
	{
		this.Name = name;
		this.Email = email;
		this.Title = title;
		this.Date = date;
		this.ID = ID;
		this.Description = description;
	}

	public Ticket() {
		this("", "", "", "", "", "");
	}
	
	
	public Ticket(JSONObject obj)
	{
		this.ID = JSONHelper.getString(obj, "id", "");
		this.Name = JSONHelper.getString(obj, "name", "");
		this.Email = JSONHelper.getString(obj, "email", "");
		this.Title = JSONHelper.getString(obj, "title", "");
		this.Date = JSONHelper.getString(obj, "date", "");
		this.Description = JSONHelper.getString(obj, "description", "");
	}
	
	
	
	

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		
		if( obj == null )
			return false;
		
		Ticket tick = (Ticket)obj;
		
		return tick.ID != null && this.ID != null && this.ID.equals(tick.ID);
	}

}
