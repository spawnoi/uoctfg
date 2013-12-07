package controllers;

import models.JobOffer;
import models.UserApp;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.admin.adminmenu;
import views.html.admin.complist;
import views.html.admin.jobslist;
import views.html.admin.userlist;

public class Admin extends Controller {

	/**
	 * This result directly redirect to application home.
	 */

	public static Result GO_ADMIN_HOME = redirect(routes.Admin.list(0, "name",
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
		// UserApp.find.ref(id).delete();
		flash("success", "UserApp has been deleted");
		return GO_ADMIN_HOME;
	}

	public static Result dashboard() {
		Integer numJobs = JobOffer.find().findRowCount();
		Integer numCand = UserApp.find.where().eq("type", 0).findRowCount();
		Integer numComp = UserApp.find.where().eq("type", 1).findRowCount();
		return ok(views.html.admin.dashboard.render("Admin dashboard",
				adminmenu.render(), numJobs, numCand, numComp));
	}

}