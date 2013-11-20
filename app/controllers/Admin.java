package controllers;

import models.UserApp;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.admin.userlist;
import views.html.user.candidatemenu;

public class Admin extends Controller {
    
    /**
     * This result directly redirect to application home.
     */
	
    public static Result GO_ADMIN_HOME = 
    redirect(
        routes.Admin.list(0, "name", "asc", "")
    );
    
    
    public static Result list(int page, String sortBy, String order, String filter) {
        return ok(
            userlist.render(
                UserApp.page(page, 10, sortBy, order, filter),
                sortBy, order, filter, "UsersList", candidatemenu.render("admin mail")
            )
        );
        
    }
    
    public static Result delete(Long id) {
        //UserApp.find.ref(id).delete();
        flash("success", "UserApp has been deleted");
        return GO_ADMIN_HOME;
    }
    
}