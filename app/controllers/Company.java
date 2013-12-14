package controllers;

import static play.data.Form.form;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.JobOffer;
import play.data.Form;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.comp.company;
import views.html.comp.companymenu;
import views.html.comp.jobdetail;
import views.html.comp.newJob;

@Security.Authenticated(Secured.class)
public class Company extends Controller {

	
    public static Result index() {
    	String titleMsg = Messages.get("home.title");
    	List<JobOffer> list = new ArrayList<JobOffer>();
		list.addAll(JobOffer.find().fetch("publisher").fetch("inscribed")
				.fetch("duration").fetch("worktype").fetch("province").fetch("sector").where()
				.eq("publisher.email", session().get("email"))
				.findList());
    	return ok(company.render(titleMsg, list, companymenu.render()));
    }
    
    public static Result addJob(){
    	Form<JobOffer> jobOfferForm = form(JobOffer.class);
		return ok(newJob.render(jobOfferForm, companymenu.render()));
    }
    
    public static Result storeJob(){
    	Form<JobOffer> jobStoreForm = form(JobOffer.class).bindFromRequest();
		if (jobStoreForm.hasErrors()) {
			flash("error", Messages.get("error.msg"));
			return badRequest(newJob.render(jobStoreForm, companymenu.render()));
		}
		JobOffer job = jobStoreForm.get();
		job.publicationDate = new Date();
		//job.save();
		String companyId = session().get("connected");
		JobOffer.create(job, new Long(companyId));
		
		flash("success", Messages.get("job.created.ok"));

		return redirect(routes.Company.index());
    }
    
    public static Result details(Long id){
    	JobOffer job = JobOffer.findById(id);
    	return ok(jobdetail.render(Messages.get("job.details"), job, companymenu.render()));
    }
    
}