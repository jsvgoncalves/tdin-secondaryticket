package model;

public class Ticket {
	
	private String Name, Email, Title, Date, ID, Description;

	public Ticket(String name, String email, String title, String date,
			String iD, String description) {
		super();
		Name = name;
		Email = email;
		Title = title;
		Date = date;
		ID = iD;
		Description = description;
	}

	public Ticket() {
		super();
		Name = "";
		Email = "";
		Title = "";
		Date = "";
		ID = "";
		Description = "";
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

}
