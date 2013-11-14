package models;

import play.data.validation.Constraints;
import play.data.validation.Constraints.Email;

public class CandidateUser {
	
	@Constraints.Required
	public String name;
	
	@Constraints.Required
	public String surname;
	
	@Constraints.Required
	@Email
	public String email;

	@Constraints.Required
	public String password;
	
	public String docId;
	
	public String education;
	
	public String experience;
	
	public String languages;
	
	public String projects;

	//GETTERS
	
	
	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
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

	public String getEducation() {
		return education;
	}

	public String getExperience() {
		return experience;
	}

	public String getLanguages() {
		return languages;
	}

	public String getProjects() {
		return projects;
	}
	
	
}
