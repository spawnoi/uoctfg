package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Page;

/**
 * Computer entity managed by Ebean
 */
@Entity
public class JobOffer extends Model {

	private static final long serialVersionUID = 1L;

	@Id
	public Long id;

	@Constraints.Required
	public String title;

	@Constraints.Required
	public String description;

	public Integer numVacants;

	@ManyToOne
	public Duration duration;

	@ManyToOne
	public Worktype worktype;

	public String salary;

	@ManyToOne
	public Province province;

	public String emplacement;

	public String benefits;

	@ManyToOne
	public Sector sector;

	@Formats.DateTime(pattern = "dd/MM/yyyy")
	public Date publicationDate;

	@Formats.DateTime(pattern = "dd/MM/yyyy")
	public Date expirationDate;

	@OneToOne(fetch = FetchType.EAGER)
	public UserApp publisher;

	@ManyToMany(cascade = CascadeType.REMOVE)
	public List<UserApp> inscribed = new ArrayList<UserApp>();

	// -- Queries

	/**
	 * Generic query helper for entity JobOffer with id Long
	 */
	// public static Finder<Long, JobOffer> find() {
	// return new Finder<Long,JobOffer>(Long.class, JobOffer.class);
	// }

	public static Model.Finder<String, JobOffer> find() {
		return new Model.Finder<String, JobOffer>(String.class, JobOffer.class);
	}

	/**
	 * Retrieve all JobOffers.
	 */
	public static List<JobOffer> findAll() {
		/*
		 * return find.fetch("project") .where() .eq("done", false)
		 * .eq("project.members.email", user) .findList();
		 */
		return Ebean.find(JobOffer.class).fetch("publisher").fetch("inscribed").fetch("worktype").fetch("duration").fetch("province").fetch("sector")
				.findList();
		// return find.all();
	}

	 public static Page<JobOffer> page(int page, int pageSize, String sortBy, String order, String filter) {
	        return 
	            find().where()
	                .ilike("title", "%" + filter + "%")
	                .orderBy(sortBy + " " + order)
	                .findPagingList(pageSize)
	                .setFetchAhead(false)
	                .getPage(page);
	    }
	
	/**
	 * Retrieve a JobOffer from Id.
	 */
	public static JobOffer findById(Long id) {
		return find().where().eq("id", id).findUnique();
	}

	public static JobOffer createByMail(JobOffer jobOffer, String companyName) {
		jobOffer.publisher = UserApp.findByEmail(companyName);
		jobOffer.save();
		return jobOffer;
	}

	public static JobOffer create(JobOffer jobOffer, Long companyId) {
		jobOffer.publisher = UserApp.findById(companyId);
		jobOffer.save();
		return jobOffer;
	}
}
