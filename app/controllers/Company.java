package controllers;

import static play.data.Form.form;

import java.util.List;

import models.JobOffer;
import play.data.Form;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.mainmenu;
import views.html.comp.company;
import views.html.comp.companymenu;
import views.html.comp.newJob;

@Security.Authenticated(Secured.class)
public class Company extends Controller {

	
    public static Result index(String emailUser) {
    	String titleMsg = Messages.get("home.title");
    	List<JobOffer> listJobs = JobOffer.findAll();
    	return ok(company.render(titleMsg, listJobs, companymenu.render(emailUser)));
    }
    
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

		return redirect(routes.Company.index("inventedMail"));
    }
    /*
    public static Result add() {
        //if(Secured.isMemberOf(project)) {
            Form<JobOffer> taskForm = form(JobOffer.class).bindFromRequest();
            if(taskForm.hasErrors()) {
                return badRequest();
            } else {
                return ok(item.render(JobOffer.create(taskForm.get(), "userCompanyMail"))
                );
            }
       // } else {
       //     return forbidden();
       // }
    } 
    */
}