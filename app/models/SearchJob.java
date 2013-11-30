package models;

import javax.persistence.ManyToOne;

import play.data.validation.Constraints;


public class SearchJob {

	public String title;

	public String description;

	public Integer numVacants;

	@ManyToOne
	public Duration duration;

	@ManyToOne
	public WorkType workType;

	public String salary;

	@ManyToOne
	public Province province;

	public String emplacement;

	public String benefits;

	@ManyToOne
	public Sector sector;

}
