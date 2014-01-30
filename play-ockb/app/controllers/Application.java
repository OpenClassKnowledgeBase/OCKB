package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Welcome to the home page."));
    }
    
    public static Result submit() {
        return ok(submit.render("Welcome to the submit content page"));
    }
    
    public static Result explore() {
        return ok(explore.render("Welcome to the explore content page"));
    }

}
