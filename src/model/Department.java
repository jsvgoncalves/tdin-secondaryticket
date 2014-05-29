package model;

import org.json.JSONObject;

public class Department {
	
	private String id, name, solverName, created, modified, description;
	
	
	public Department(String id, String name, String solverName, String created, String modified, String description)
	{
		this.id = id;
		this.name = name;
		this.solverName = solverName;
		this.created = created;
		this.modified = modified;
		this.description = description;
	}
	
	public Department(JSONObject obj)
	{
		this.id = obj.getString("id");
		this.name = obj.getString("name");
		this.solverName = obj.getString("solver_name");
		this.created = obj.getString("created");
		this.modified = obj.getString("modified");
		this.description = obj.getString("description");
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSolverName() {
		return solverName;
	}

	public void setSolverName(String solverName) {
		this.solverName = solverName;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "" + this.name;
	}
	
	

}
