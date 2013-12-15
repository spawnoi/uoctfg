package controllers;

import static play.data.Form.form;

import java.util.ArrayList;
import java.util.List;

import models.JobOffer;
import models.SearchJob;
import models.UserApp;
import play.data.Form;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.mainmenu;
import views.html.user.candidate;
import views.html.user.candidatemenu;
import views.html.user.searchJobs;
import views.html.user.viewjob;

import com.avaje.ebean.Expr;
import com.avaje.ebean.Expression;

import controllers.Application.Login;

@Security.Authenticated(Secured.class)
public class Candidate extends Controller {

	/*
	 * Retornar inscribed Jobs
	 */
	public static Result index() {
		if (session().get("type") == null) {
			flash("error", Messages.get("restricted.area"));
			return ok(views.html.login.render(form(Login.class),
					mainmenu.render()));
		} else if (!session().get("type").equals("CANDIDATE")) {
			if (session().get("type").equals("ENTERPRISE")) {
				return redirect(routes.Company.index());
			} else {
				return redirect(routes.Admin.dashboard());
			}
		} else {
			List<JobOffer> listJobs = new ArrayList<JobOffer>();
			listJobs.addAll(JobOffer.find().fetch("publisher")
					.fetch("inscribed").where()
					.eq("inscribed.email", session().get("email")).findList());

			return ok(candidate.render(Messages.get("candidate.area"),
					listJobs, candidatemenu.render()));
		}
	}

	public static Result prepareSearchJob() {

		if (session().get("type") == null) {
			flash("error", Messages.get("restricted.area"));
			return ok(views.html.login.render(form(Login.class),
					mainmenu.render()));
		} else if (!session().get("type").equals("CANDIDATE")) {
			if (session().get("type").equals("ENTERPRISE")) {
				return redirect(routes.Company.index());
			} else {
				return redirect(routes.Admin.dashboard());
			}
		} else {

			Form<SearchJob> jobOfferForm = form(SearchJob.class);
			List<JobOffer> list = new ArrayList<JobOffer>();

			return ok(searchJobs.render(jobOfferForm, candidatemenu.render(),
					list));

		}
	}

	public static Result searchJob() {
		Form<SearchJob> jobStoreForm = form(SearchJob.class).bindFromRequest();
		List<JobOffer> list = new ArrayList<JobOffer>();
		SearchJob job = jobStoreForm.get();

		Expression duration = Expr.eq("1", 1);
		if (job.duration != null && job.duration.id != null)
			duration = Expr.eq("duration.id", job.duration.id);

		Expression sector = Expr.eq("1", 1);
		if (job.sector != null && job.sector.id != null)
			sector = Expr.eq("sector.id", job.sector.id);

		Expression province = Expr.eq("1", 1);
		if (job.province != null && job.province.id != null)
			province = Expr.eq("province.id", job.province.id);

		Expression worktype = Expr.eq("1", 1);
		if (job.worktype != null && job.worktype.id != null)
			worktype = Expr.eq("worktype.id", job.worktype.id);

		Expression empl = Expr.eq("1", 1);
		if (job.emplacement != null && !"".equals(job.emplacement)) {
			empl = Expr.ilike("emplacement", "%" + job.emplacement + "%");
		}

		list.addAll(JobOffer.find().fetch("publisher").fetch("inscribed")
				.fetch("duration").fetch("worktype").fetch("province")
				.fetch("sector").where().ilike("title", "%" + job.title + "%")
				.add(empl).add(duration).add(worktype).add(sector)
				.add(province).findList());

		return ok(searchJobs.render(jobStoreForm, candidatemenu.render(), list));
	}

	public static Result viewJob(Long id) {
		Form<JobOffer> jobForm = form(JobOffer.class).fill(
				JobOffer.findById(id));
		return ok(viewjob.render(Messages.get("job.details"), candidatemenu.render(),
				id, jobForm));
	}

	public static Result subscribe(Long id) {

		if (id != null) {
			JobOffer job = JobOffer.findById(id);
			job.inscribed.add(UserApp.findByEmail(session().get("email")));
			job.save();
			flash("success", Messages.get("job.inscribed.ok"));
		}

		return index();
	}

	public static Result revoke(Long id) {

		if (id != null) {
			JobOffer job = JobOffer.findById(id);
			UserApp user = UserApp.findByEmail(session().get("email"));
			if (job.inscribed.contains(user)) {
				job.inscribed.remove(user);
			}
			job.save();
			flash("success", Messages.get("job.inscribed.rev"));
		}

		return index();
	}
}