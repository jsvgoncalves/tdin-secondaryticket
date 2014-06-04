package model;

import java.io.Serializable;

import org.json.JSONObject;

import resources.JSONHelper;

public class Ticket implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7650793087693386094L;
	
	
	private String ID,userID,solverID,status,created,modified,title,
					description,reply,userName,userEmail,SolverName,SolverEmail;


	public Ticket(JSONObject obj)
	{
		this.ID = JSONHelper.getString(obj, "id", "").trim();
		this.userID = JSONHelper.getString(obj, "user_id", "").trim();
		this.solverID = JSONHelper.getString(obj, "solver_id", "").trim();
		this.status = JSONHelper.getString(obj, "status", "0").trim();
		this.created = JSONHelper.getString(obj, "created", "").trim();
		this.modified = JSONHelper.getString(obj, "modified", "").trim();
		this.title = JSONHelper.getString(obj, "title", "").trim();
		this.description = JSONHelper.getString(obj, "description", "").trim();
		this.reply = JSONHelper.getString(obj, "reply", "").trim();
		
		if( obj.has("User") )
		{
			JSONObject objUser = obj.getJSONObject("User");
			this.userName = JSONHelper.getString(objUser, "name", "").trim();
			this.userEmail = JSONHelper.getString(objUser, "email", "").trim();
		}
		
		if( obj.has("Solver") )
		{
			JSONObject objUser = obj.getJSONObject("Solver");
			this.SolverName = JSONHelper.getString(objUser, "name", "").trim();
			this.SolverEmail = JSONHelper.getString(objUser, "email", "").trim();
		}
	}
	
	

	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}





	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}



	public String getSolverID() {
		return solverID;
	}
	public void setSolverID(String solverID) {
		this.solverID = solverID;
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



	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}



	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}



	public String getSolverName() {
		return SolverName;
	}
	public void setSolverName(String solverName) {
		SolverName = solverName;
	}



	public String getSolverEmail() {
		return SolverEmail;
	}
	public void setSolverEmail(String solverEmail) {
		SolverEmail = solverEmail;
	}

	

	
	@Override
	public boolean equals(Object obj) {
		
		if( obj == null )
			return false;
		
		Ticket tick = (Ticket)obj;
		
		return tick.ID != null && this.ID != null && this.ID.equals(tick.ID);
	}
}
