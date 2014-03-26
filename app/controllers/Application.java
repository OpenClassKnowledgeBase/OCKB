package controllers;

import java.util.List;

import models.Comment;
import models.User;
import play.*;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return ok(views.html.index.render("Welcome to the home page."));
    }
    
    public static Result submit() {
        return ok(views.html.submit.render("Welcome to the submit content page"));
    }
    
    public static Result explore() {
        return ok(views.html.explore.render("Welcome to the explore content page"));
    }
    
    public static Result comments() {
    	List<Comment> cmntList = Comment.all();
    	return ok(views.html.comments.render(cmntList));
    }
    
    public static Result users() {
    	List<User> userList = User.all();
    	return ok(views.html.users.render(userList));
    }

}
