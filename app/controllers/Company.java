package controllers;

import models.JobOffer;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.comp.company;
import views.html.comp.companymenu;

@Security.Authenticated(Secured.class)
public class Company extends Controller {

	
    public static Result index(String emailUser) {
    	String titleMsg = Messages.get("home.title");
    	return ok(company.render(titleMsg, JobOffer.findAll(), companymenu.render(emailUser)));
    }
    
    public static Result addJob(){
    	return ok("addJob");
    }
    
    public static Result storeJob(){
    	return ok("storeJob");
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