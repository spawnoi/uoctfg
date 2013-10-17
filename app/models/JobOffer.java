package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

/**
 * Computer entity managed by Ebean
 */
@Entity
public class JobOffer extends Model {

	private static final long serialVersionUID = 1L;

	@Id
	public Long id;

	@Constraints.Required
	public String description;

	@Formats.DateTime(pattern = "yyyy-MM-dd")
	public Date expirationDate;

	@ManyToOne
	public UserApp publisher;
	
 // -- Queries
    
	/**
	 * Generic query helper for entity JobOffer with id Long
	 */
    public static Model.Finder<String,JobOffer> find = new Model.Finder<String,JobOffer>(String.class, JobOffer.class);
    
    /**
     * Retrieve all JobOffers.
     */
    public static List<JobOffer> findAll() {
        return find.all();
    }

    /**
     * Retrieve a JobOffer from Id.
     */
    public static JobOffer findById(Long id) {
        return find.where().eq("id", id).findUnique();
    }
    
    public static JobOffer create(JobOffer jobOffer, String companyName) {
    	jobOffer.publisher = UserApp.findByEmail(companyName);
    	jobOffer.save();
        return jobOffer;
    }
    
}
