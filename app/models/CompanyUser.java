package models;

import play.data.validation.Constraints;
import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.Required;

public class CompanyUser {

	@Constraints.Required
	public String name;
	
	@Required
	public String description;
	
	@Constraints.Required
	@Email
	public String email;

	@Constraints.Required
	public String password;
	
	public String docId;
	
	public String sector;
	
	public String web;
	

	
	//GETTERS
	
	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getDocId() {
		return docId;
	}

	public String getSector() {
		return sector;
	}

	public String getWeb() {
		return web;
	}

	public String getDescription() {
		return description;
	}

	
	
	
}
