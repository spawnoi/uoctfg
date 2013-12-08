package controllers;

import static play.data.Form.form;
import models.JobOffer;
import models.UserApp;
import play.data.Form;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.admin.adminmenu;
import views.html.admin.complist;
import views.html.admin.jobslist;
import views.html.admin.managecand;
import views.html.admin.managecomp;
import views.html.admin.userlist;

public class Admin extends Controller {

	/**
	 * This result directly redirect to application home.
	 */

	public static Result GO_ADMIN_HOME = redirect(routes.Admin.list(0, "name",
			"asc", ""));
	
	public static Result GO_ENTER_HOME = redirect(routes.Admin.listComp(0, "name",
			"asc", ""));
	
	public static Result GO_JOBS_HOME = redirect(routes.Admin.listJobs(0, "title",
			"asc", ""));

	public static Result list(int page, String sortBy, String order,
			String filter) {
		return ok(userlist.render(
				UserApp.page(page, 10, sortBy, order, filter), sortBy, order,
				filter, Messages.get("admin.cand"), adminmenu.render()));

	}

	public static Result listJobs(int page, String sortBy, String order,
			String filter) {
		return ok(jobslist.render(
				JobOffer.page(page, 10, sortBy, order, filter), sortBy, order,
				filter, Messages.get("admin.job"), adminmenu.render()));

	}

	public static Result listComp(int page, String sortBy, String order,
			String filter) {
		return ok(complist.render(
				UserApp.pageComp(page, 10, sortBy, order, filter), sortBy,
				order, filter, Messages.get("admin.enter"), adminmenu.render()));

	}

	public static Result delete(Long id) {
		UserApp user = UserApp.findById(id);
		user.delete();
		flash("success", "UserApp [ " + user.name + " ]has been deleted");
		return GO_ADMIN_HOME;
	}

	public static Result prepareCandidate(Long id) {

		Form<UserApp> userUpdForm = form(UserApp.class).fill(
				UserApp.findById(id));

		return ok(managecand.render(userUpdForm, adminmenu.render()));
	}

	public static Result updateCandidate() {
		Result res;
		Form<UserApp> userUpdForm = form(UserApp.class).bindFromRequest();
		if (userUpdForm.hasErrors()) {
			flash("error", Messages.get("error.msg"));

			res = badRequest(managecand.render(userUpdForm, adminmenu.render()));
		} else {

			UserApp old = UserApp
					.findByEmail(userUpdForm.get().email);
			UserApp userUpdated = userUpdForm.get();
			userUpdated.email = old.email;
			userUpdated.password = old.password;
			userUpdated.update(old.id);

			res = GO_ADMIN_HOME;

			flash("success", "Account updated!");
		}
		return res;
	}

	public static Result prepareComp(Long id) {
		Form<UserApp> userUpdForm = form(UserApp.class).fill(
				UserApp.findById(id));
		return ok(managecomp.render(userUpdForm, adminmenu.render()));
	}

	public static Result updateComp() {
		
		Result res;
		Form<UserApp> userUpdForm = form(UserApp.class).bindFromRequest();
		if (userUpdForm.hasErrors()) {
			flash("error", Messages.get("error.msg"));

			res = badRequest(managecomp.render(userUpdForm, adminmenu.render()));
		} else {

			UserApp old = UserApp
					.findByEmail(userUpdForm.get().email);
			UserApp userUpdated = userUpdForm.get();
			userUpdated.email = old.email;
			userUpdated.password = old.password;
			userUpdated.update(old.id);

			res = GO_ENTER_HOME;

			flash("success", "Account updated!");
		}
		return res;
		
	}
	
	public static Result deleteJob(Long id) {
		JobOffer job = JobOffer.findById(id);
		job.delete();
		flash("success", "UserApp [ " + job.title + " ]has been deleted");
		return GO_JOBS_HOME;
	}

	
	public static Result dashboard() {
		Integer numJobs = JobOffer.find().findRowCount();
		Integer numCand = UserApp.find.where().eq("type", 0).findRowCount();
		Integer numComp = UserApp.find.where().eq("type", 1).findRowCount();
		return ok(views.html.admin.dashboard.render("Admin dashboard",
				adminmenu.render(), numJobs, numCand, numComp));
	}

}