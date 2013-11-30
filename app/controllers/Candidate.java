package controllers;


import static play.data.Form.form;

import java.util.ArrayList;
import java.util.List;

import models.JobOffer;
import models.SearchJob;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.user.candidate;
import views.html.user.candidatemenu;
import views.html.user.searchJobs;
import views.html.user.viewjob;

@Security.Authenticated(Secured.class)
public class Candidate extends Controller {

	/*
	 * Retornar inscribed Jobs
	 */
    public static Result index() {
    	//String titleMsg = Messages.get("home.title");
    	String title = "Candidate Home";
    	List<JobOffer> listJobs = JobOffer.findAll();
    	return ok(candidate.render(title, listJobs, candidatemenu.render()));
    }
   
    public static Result prepareSearchJob(){
    	Form<SearchJob> jobOfferForm = form(SearchJob.class);
    	List<JobOffer> filtered = JobOffer.findAll();
    	return ok(searchJobs.render(jobOfferForm, candidatemenu.render(), filtered));
    }

    public static Result searchJob(){
      	Form<SearchJob> jobStoreForm = form(SearchJob.class).bindFromRequest();
      	List<JobOffer> list = new ArrayList<JobOffer>();
      	SearchJob job = jobStoreForm.get();
      	for (String nameWord : job.title.split(" ")) {
      		list.addAll(JobOffer.find().where().contains("title", nameWord).findList());
          }
      
        return ok(searchJobs.render(jobStoreForm, candidatemenu.render(), list));
    }
    
    public static Result viewJob(Long id){
    	 Form<JobOffer> jobForm = form(JobOffer.class).fill(
    			 JobOffer.findById(id)
    	        );
    	return ok(viewjob.render("View Job details", candidatemenu.render(), id, jobForm));
    }
    /*
    public static Result addJob(){
    	Form<JobOffer> jobOfferForm = form(JobOffer.class);
		return ok(newJob.render(jobOfferForm, mainmenu.render()));
    }
    
    public static Result storeJob(){
    	Form<JobOffer> jobStoreForm = form(JobOffer.class).bindFromRequest();
		if (jobStoreForm.hasErrors()) {
			return badRequest(newJob.render(jobStoreForm,
					mainmenu.render()));
		}
		jobStoreForm.get().save();
		System.out.println("userAddForm.get().name: " + jobStoreForm.get().title);
		flash("success", "User " + jobStoreForm.get().title + " has been created");

		return redirect(routes.Company.index();
    }
    */
}