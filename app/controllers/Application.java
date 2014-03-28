package controllers;

import java.util.List;
import com.avaje.ebean.Ebean;
import models.*;
import play.*;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {
	static Form<PostSubmission> postForm = Form.form(PostSubmission.class);

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
    	return ok(views.html.post.render(cmntList));
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
    
    public static Result categories() {
    	//figure out how to put this in global
/*    	Ebean.save((List<Category>) Yaml.load("categories.yml"));*/
    	List<Category> categoryList = Category.findAll();
    	return ok(views.html.categories.render(categoryList));
    }
}
