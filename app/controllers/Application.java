package controllers;

import static play.data.Form.form;

import java.util.ArrayList;
import java.util.List;

import models.CandidateUser;
import models.CompanyUser;
import models.JobOffer;
import models.UserApp;
import play.api.templates.Html;
import play.data.Form;
import play.data.validation.ValidationError;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.editadmin;
import views.html.editcompany;
import views.html.edituser;
import views.html.mainmenu;
import views.html.admin.adminmenu;
import views.html.admin.dashboard;
import views.html.comp.companymenu;
import views.html.user.candidatemenu;

public class Application extends Controller {

	public static Result index() {
		String titleMsg = Messages.get("home.title");
		return ok(views.html.index.render(titleMsg, mainmenu.render()));
	}

	public static Result setLang(String code) {
		List<String> langCodes = new ArrayList<String>();
		langCodes.add("es");
		langCodes.add("en");
		langCodes.add("ca");
		if(langCodes.contains(code)){
			changeLang(code);
		}else{
			changeLang("en");
		}
	    return redirect(routes.Application.index());
	}
	
	public static Result newCompUser() {
		Form<CompanyUser> userForm = form(CompanyUser.class);
		return ok(views.html.newCompanyUser.render(userForm, mainmenu.render()));
	}

	public static Result saveCompUser() {
		Form<CompanyUser> userAddForm = form(CompanyUser.class)
				.bindFromRequest();
		if (userAddForm.hasErrors()) {
			flash("error", Messages.get("error.msg"));
			return badRequest(views.html.newCompanyUser.render(userAddForm,
					mainmenu.render()));
		} else if (UserApp.findByEmail(userAddForm.get().getEmail()) != null) {
			flash("error", "This e-mail already exists!");
			return badRequest(views.html.newCompanyUser.render(userAddForm,
					mainmenu.render()));

		} else {
			UserApp user = UserApp.makeInstance(userAddForm.get());
			user.save();
			flash("success", "Company account instance created/edited: " + user.name);
			System.out.println("userAddForm.get().name: "
					+ userAddForm.get().name);
			flash("success", "Company " + userAddForm.get().name
					+ " has been created");

			return redirect(routes.Company.index());
		}
	}

	public static Result newUser() {
		Form<CandidateUser> userForm = form(CandidateUser.class);
		return ok(views.html.newUser.render(userForm, mainmenu.render()));
	}

	public static Result saveUser() {
		Form<CandidateUser> userAddForm = form(CandidateUser.class)
				.bindFromRequest();
		if (userAddForm.hasErrors()) {
			flash("error", Messages.get("error.msg"));
			return badRequest(views.html.newUser.render(userAddForm,
					mainmenu.render()));
		} else if (UserApp.findByEmail(userAddForm.get().getEmail()) != null) {
			flash("error", "This e-mail already exists!");
			return badRequest(views.html.newUser.render(userAddForm,
					mainmenu.render()));

		} else {
			UserApp user = UserApp.makeInstance(userAddForm.get());
			user.save();
			flash("success", "Candidate instance created: " + user.name);

			return redirect(routes.Application.login());
		}
	}

	public static Result prepareUser() {
		Form<UserApp> userUpdForm = form(UserApp.class).fill(
				UserApp.findByEmail(session().get("email")));
		
		
		Html view = null;
		if (userUpdForm.get().isCandidate()) {
			view = edituser.render(userUpdForm, candidatemenu.render());
		} else if (userUpdForm.get().isCompany()) {
			view = editcompany.render(userUpdForm, companymenu.render());
		}else if(userUpdForm.get().isAdmin()){
			view = editadmin.render(userUpdForm, adminmenu.render());
		} else {
			view = views.html.index.render(Messages.get("account.update"), mainmenu.render());
		}

		return ok(view);
	}

	public static Result updateUser() {
		Result res;
		Form<UserApp> userUpdForm = form(UserApp.class).bindFromRequest();
		if (userUpdForm.hasErrors()) {
			flash("error", Messages.get("error.msg"));
			Html view;
			if (userUpdForm.get().isCandidate()) {
				view = edituser.render(userUpdForm, candidatemenu.render());
			} else if (userUpdForm.get().isCompany()) {
				view = editcompany.render(userUpdForm, companymenu.render());
			}else if(userUpdForm.get().isAdmin()){
				view = editadmin.render(userUpdForm, adminmenu.render());
			} else {
				view = views.html.index.render(Messages.get("account.update"),
						mainmenu.render());
			}
			res = badRequest(view);
		} else {
			
			UserApp old = UserApp.findByEmail(session().get("email"));
			UserApp userUpdated = userUpdForm.get();
			userUpdated.email = old.email;
			userUpdated.password= old.password;
			userUpdated.update(old.id);
			
			res = redirect(routes.Application.index());
			if (old.isCandidate()) {
				res = redirect(routes.Candidate.index());
			} else if (old.isCompany()) {
				res = redirect(routes.Company.index());
			} else {
				Integer numJobs = JobOffer.find().findRowCount();
				Integer numCand = UserApp.find.where().eq("type", 0).findRowCount();
				Integer numComp = UserApp.find.where().eq("type", 1).findRowCount();
				res =  ok(views.html.admin.dashboard.render("Admin dashboard",
						adminmenu.render(), numJobs, numCand, numComp));
			}
			flash("updated", "Account updated!");

		}

		return res;
	}

	/**
	 * Authentication methods
	 * 
	 */
	public static class Login {
		public String email;
		public String password;

		public List<ValidationError> validate() {
			List<ValidationError> errors = new ArrayList<ValidationError>();
			if (UserApp.authenticate(email, password) == null) {
				errors.add(new ValidationError("email",
						Messages.get("login.error")));
			}
			return errors.isEmpty() ? null : errors;
		}
	}

	/**
	 * Login page.
	 */
	public static Result login() {
		flash("success", Messages.get("login.please"));
		// ready to login
		return ok(views.html.login.render(form(Login.class), mainmenu.render()));
	}

	/**
	 * Handle login form submission.
	 */
	public static Result authenticate() {
		Form<Login> loginForm = form(Login.class).bindFromRequest();
		if (loginForm.hasErrors()) {
			flash("error", Messages.get("login.error"));
			return badRequest(views.html.login.render(loginForm,
					mainmenu.render()));
		} else {

			UserApp user = UserApp.authenticate(loginForm.get().email,
					loginForm.get().password);
			if (user != null) {
				session("email", user.getEmail());
				session("connected", "" + user.id);
				session("type", "" + user.getType());

				if (user.isCandidate()) {
					return redirect(routes.Candidate.index());
				} else if (user.isCompany()) {
					return redirect(routes.Company.index());

				} else if (user.isAdmin()) {
					return redirect(routes.Admin.list(0, "name", "asc", ""));
				} else {
					return redirect(routes.Application.index());
				}
			} else {
				return badRequest(views.html.login.render(loginForm,
						mainmenu.render()));
			}
		}
	}

	/**
	 * Logout and clean the session.
	 */
	public static Result logout() {
		session().clear();
		flash("success", Messages.get("logout.msg"));
		return redirect(routes.Application.index());
	}
}
