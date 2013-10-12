package controllers;

import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {

    public static Result index() {
    	String titleMsg = Messages.get("home.title");
        return ok(index.render(titleMsg + " --> Your new application is ready."));
    }

}
