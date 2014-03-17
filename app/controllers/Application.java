package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import models.*;
import views.html.*;

public class Application extends Controller {
	static Form<PostSubmission> postForm = Form.form(PostSubmission.class);

    public static Result index() {
        return ok(index.render("Welcome to the home page."));
    }
    
    public static Result submit() {
        return ok(submit.render("Welcome to the submit content page"));
    }
    
    public static Result explore() {
        return ok(explore.render("Welcome to the explore content page"));
    }

    public static Result submitPost(){
    	Form<PostSubmission> filledForm = postForm.bindFromRequest();
    	if (filledForm.hasErrors()){
    		System.out.println(filledForm.errors());
    		return badRequest(views.html.submitPost.render(PostSubmission.all(), filledForm));
    	}
    	else{
    		PostSubmission.create(filledForm.get());
    		return redirect(routes.Application.posts());
    	}
    }

    public static Result posts(){
    	return ok(views.html.submitPost.render(PostSubmission.all(), postForm));
    }

    public static Result deletePost(Long id){
    	return TODO;
    }
}
