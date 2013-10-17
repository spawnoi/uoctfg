package controllers;

import static play.data.Form.form;
import models.UserApp;
import play.data.Form;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.mainmenu;

public class Application extends Controller {

	public static Result index() {
		String titleMsg = Messages.get("home.title");
		return ok(views.html.index.render(titleMsg, mainmenu.render()));
	}

	/**
	 * Handle the 'new computer form' submission
	 */
	public static Result saveUser() {
		Form<UserApp> userAddForm = form(UserApp.class).bindFromRequest();
		if (userAddForm.hasErrors()) {
			return badRequest(views.html.newUser.render(userAddForm,
					mainmenu.render()));
		}
		userAddForm.get().save();
		System.out.println("userAddForm.get().name: " + userAddForm.get().name);
		flash("success", "User " + userAddForm.get().name + " has been created");

		return redirect(routes.Application.index());
	}

	public static Result newUser() {
		Form<UserApp> userForm = form(UserApp.class);
		return ok(views.html.newUser.render(userForm, mainmenu.render()));
	}

	/**
	 * Authentication methods
	 * 
	 */
	public static class Login {
		public String email;
		public String password;

		public String validate() {
			if (UserApp.authenticate(email, password) == null) {
				return "Invalid user or password";
			}
			return null;
		}
	}

	/**
	 * Login page.
	 */
	public static Result login() {
		return ok(views.html.login.render(form(Login.class), mainmenu.render()));
	}

	/**
	 * Handle login form submission.
	 */
	public static Result authenticate() {
		Form<Login> loginForm = form(Login.class).bindFromRequest();
		if (loginForm.hasErrors()) {
			return badRequest(views.html.login.render(loginForm,
					mainmenu.render()));
		} else {
			session("email", loginForm.get().email);
			return redirect(routes.Company.index(loginForm.get().email));
		}
	}

	/**
	 * Logout and clean the session.
	 */
	public static Result logout() {
		session().clear();
		flash("success", "You've been logged out");
		return redirect(routes.Application.login());
	}
}
