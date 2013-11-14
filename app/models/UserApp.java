package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

/**
 * Computer entity managed by Ebean
 */
@Entity
public class UserApp extends Model {

	private static final long serialVersionUID = 1L;
	
	enum UserType {CANDIDATE, ENTERPRISE, ADMINISTRATOR }
	
	@Id
	public Long id;

	@Constraints.Required
	public String email;

	@Constraints.Required
	public String password;
	
	public String name;
	
	public String secName;
	
	public String docId;

	@Formats.DateTime(pattern = "yyyy-MM-dd")
	public Date birthDate;
	
	public UserType type;
	
	//Candidate Specifics:
	public String education;
	
	public String experience;
	
	public String languages;
	
	public String projects;
	
	//Company Specifics:
	public String sector;
	
	public String web;
	
	public String description;

	public boolean isCandidate(){
		return type == UserType.CANDIDATE;
	}
	
	public boolean isCompany(){
		return type == UserType.ENTERPRISE;
	}
	
	

	
// -- Parsing
	 public static UserApp makeInstance(CandidateUser candidateFormUser) {
		 UserApp user = new UserApp();
		 user.setName(candidateFormUser.getName());
		 user.setEmail(candidateFormUser.getEmail());
		 user.setPassword(candidateFormUser.getPassword());
		 user.setSecName(candidateFormUser.getSurname());
		 user.setDocId(candidateFormUser.getDocId());
		 user.setEducation(candidateFormUser.getEducation());
		 user.setExperience(candidateFormUser.getExperience());
		 user.setLanguages(candidateFormUser.getLanguages());
		 user.setProjects(candidateFormUser.getProjects());
		 user.setType(UserType.CANDIDATE);

		 //student.level = GradeLevel.findLevel(formData.level);
		 //student.gpa = GradePointAverage.findGPA(formData.gpa);
		 //for (String major : formData.majors) {
	     //  student.majors.add(Major.findMajor(major));
 	     //}
		    return user;
		  }
	 
	 public static UserApp makeInstance(CompanyUser companyFormUser) {
		 UserApp user = new UserApp();
		 user.setName(companyFormUser.getName());
		 user.setEmail(companyFormUser.getEmail());
		 user.setPassword(companyFormUser.getPassword());
		 user.setDescription(companyFormUser.getDescription());
		 user.setDocId(companyFormUser.getDocId());
		 user.setSector(companyFormUser.getSector());
		 user.setWeb(companyFormUser.getWeb());
		 user.setType(UserType.ENTERPRISE);

		 //student.level = GradeLevel.findLevel(formData.level);
		 //student.gpa = GradePointAverage.findGPA(formData.gpa);
		 //for (String major : formData.majors) {
	     //  student.majors.add(Major.findMajor(major));
 	     //}
		    return user;
		  }
	
 // -- Queries
    
	/**
	 * Generic query helper for entity User with id Long
	 */
    public static Model.Finder<String,UserApp> find = new Model.Finder<String,UserApp>(String.class, UserApp.class);
    
    /**
     * Retrieve all users.
     */
    public static List<UserApp> findAll() {
        return find.all();
    }

    /**
     * Retrieve a User from email.
     */
    public static UserApp findByEmail(String email) {
        return find.where().eq("email", email).findUnique();
    }
    
    /**
     * Authenticate a UserApp.
     */
    public static UserApp authenticate(String email, String password) {
        return find.where()
            .eq("email", email)
            .eq("password", password)
            .findUnique();
    }
    
    // --
    
    public String toString() {
        return "UserApp(" + email + ")";
    }


    //GETTERS AND SETTERS
  
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSecName() {
		return secName;
	}

	public void setSecName(String secName) {
		this.secName = secName;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getLanguages() {
		return languages;
	}

	public void setLanguages(String languages) {
		this.languages = languages;
	}

	public String getProjects() {
		return projects;
	}

	public void setProjects(String projects) {
		this.projects = projects;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
    

}
