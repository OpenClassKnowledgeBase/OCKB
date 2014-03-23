package controllers;

import java.util.List;

import com.avaje.ebean.Ebean;

import models.Post;
import models.PostSubmission;
import play.data.Form;
import play.libs.Yaml;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.explore;
import views.html.index;
import views.html.submit;

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
    		//PostSubmission.create(filledForm.get());
    		return redirect(routes.Application.posts());
    	}
    }

    public static Result posts(){
    	//Ebean.save((List<Post>) Yaml.load("initial-data.yml"));
    	return ok(views.html.seedPost.render(Post.all()));
    }

    public static Result deletePost(Long id){
    	Post.find.ref(id).delete();
    	return ok(views.html.seedPost.render(Post.all()));
    }
}
