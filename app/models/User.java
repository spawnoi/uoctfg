package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

/**
 * Computer entity managed by Ebean
 */
@Entity
public class User extends Model {

	private static final long serialVersionUID = 1L;

	@Id
	public Long id;

	@Constraints.Required
	public String email;

	@Constraints.Required
	public String password;

	public String name;
	public String secName;

	@Formats.DateTime(pattern = "yyyy-MM-dd")
	public Date birthDate;

	
 // -- Queries
    
	/**
	 * Generic query helper for entity User with id Long
	 */
    public static Model.Finder<String,User> find = new Model.Finder<String,User>(String.class, User.class);
    
    /**
     * Retrieve all users.
     */
    public static List<User> findAll() {
        return find.all();
    }

    /**
     * Retrieve a User from email.
     */
    public static User findByEmail(String email) {
        return find.where().eq("email", email).findUnique();
    }
    
    /**
     * Authenticate a User.
     */
    public static User authenticate(String email, String password) {
        return find.where()
            .eq("email", email)
            .eq("password", password)
            .findUnique();
    }
    
    // --
    
    public String toString() {
        return "User(" + email + ")";
    }

}
