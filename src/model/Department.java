package model;

import org.json.JSONObject;

import resources.JSONHelper;

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
		this.id = JSONHelper.getString(obj, "id", "");
		this.name = JSONHelper.getString(obj, "name", "");
		this.solverName = JSONHelper.getString(obj, "solver_name", "");
		this.created = JSONHelper.getString(obj, "created", "");
		this.modified = JSONHelper.getString(obj, "modified", "");
		this.description = JSONHelper.getString(obj, "description", "");
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
