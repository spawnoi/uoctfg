package controllers;

import static play.data.Form.form;

import java.util.ArrayList;
import java.util.List;

import models.JobOffer;
import models.SearchJob;
import models.UserApp;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.user.candidate;
import views.html.user.candidatemenu;
import views.html.user.searchJobs;
import views.html.user.viewjob;

import com.avaje.ebean.Expr;
import com.avaje.ebean.Expression;

@Security.Authenticated(Secured.class)
public class Candidate extends Controller {

	/*
	 * Retornar inscribed Jobs
	 */
	public static Result index() {
		// String titleMsg = Messages.get("home.title");
		String title = "Candidate Home";
		List<JobOffer> listJobs = new ArrayList<JobOffer>();
		// Load just the ones that the candidate is inscribed on.
		listJobs.addAll(JobOffer.find().fetch("publisher").fetch("inscribed")
				.where().eq("inscribed.email", session().get("email"))
				.findList());

		return ok(candidate.render(title, listJobs, candidatemenu.render()));
	}

	public static Result prepareSearchJob() {
		Form<SearchJob> jobOfferForm = form(SearchJob.class);
		List<JobOffer> list = new ArrayList<JobOffer>();
		list.addAll(JobOffer.find().fetch("publisher").fetch("inscribed")
				.where().ne("inscribed.email", session().get("email"))
				.findList());

		return ok(searchJobs.render(jobOfferForm, candidatemenu.render(), list));
	}

	public static Result searchJob() {
		Form<SearchJob> jobStoreForm = form(SearchJob.class).bindFromRequest();
		List<JobOffer> list = new ArrayList<JobOffer>();
		SearchJob job = jobStoreForm.get();

		Expression duration = Expr.eq("1", "1");
		if (job.duration != null && job.duration.id != null)
			duration = Expr.eq("duration.id", job.duration.id);

		Expression sector = Expr.eq("1", "1");
		if (job.sector != null && job.sector.id != null)
			sector = Expr.eq("sector.id", job.sector.id);
		
		Expression province = Expr.eq("1", "1");
		if (job.province != null && job.province.id != null)
			province = Expr.eq("province.id", job.province.id);
		
		list.addAll(JobOffer.find().fetch("publisher").fetch("inscribed")
				.fetch("duration").fetch("province").fetch("sector").where()
				.ilike("title", "%" + job.title + "%")
				.add(duration)
				.add(sector)
				.add(province)
				// .eq("inscribed.email", session().get("email"))
				.findList());

		// for (String nameWord : job.title.split(" ")) {
		// list.addAll(JobOffer.find().where().contains("title",
		// nameWord).findList());
		// }

		return ok(searchJobs.render(jobStoreForm, candidatemenu.render(), list));
	}

	/*
	 * Simple Query example
	 * 
	 * List<Order> list = Ebean.find(Order.class) .join("customer") .where()
	 * .gt("id", 0) .eq("status", Order.Status.NEW) .ilike("customer.name",
	 * "Ro%") .findList();
	 */

	public static Result viewJob(Long id) {
		Form<JobOffer> jobForm = form(JobOffer.class).fill(
				JobOffer.findById(id));
		return ok(viewjob.render("View Job details", candidatemenu.render(),
				id, jobForm));
	}
	/*
	 * public static Result addJob(){ Form<JobOffer> jobOfferForm =
	 * form(JobOffer.class); return ok(newJob.render(jobOfferForm,
	 * mainmenu.render())); }
	 * 
	 * public static Result storeJob(){ Form<JobOffer> jobStoreForm =
	 * form(JobOffer.class).bindFromRequest(); if (jobStoreForm.hasErrors()) {
	 * return badRequest(newJob.render(jobStoreForm, mainmenu.render())); }
	 * jobStoreForm.get().save(); System.out.println("userAddForm.get().name: "
	 * + jobStoreForm.get().title); flash("success", "User " +
	 * jobStoreForm.get().title + " has been created");
	 * 
	 * return redirect(routes.Company.index(); }
	 */
	
	public static Result subscribe(Long id){
		
		if(id !=null){
			JobOffer job = JobOffer.findById(id);
			job.inscribed.add(UserApp.findByEmail(session().get("email")));
			job.save();
			flash("success", "Subscription to offer ["+job.title +"] done!");
		}
		
		return index();
	}
	
	public static Result revoke(Long id){
		
		if(id !=null){
			JobOffer job = JobOffer.findById(id);
			UserApp user = UserApp.findByEmail(session().get("email"));
			if(job.inscribed.contains(user)){
				job.inscribed.remove(user);
			}
			job.save();
			flash("success", "Revoked the subscription to offer ["+job.title +"]");
		}
		
		return index();
	}
}