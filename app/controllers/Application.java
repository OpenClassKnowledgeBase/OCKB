package controllers;

import com.avaje.ebean.Ebean;
import models.*;
import play.data.*;
import play.mvc.*;
import views.html.*;
//CAS imports
import java.net.*;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.Document;


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
    
	public static Result login() throws Exception {
		  Map<String, String[]> query = request().queryString();
		  // service url is where you will handle validation after login
		  // or getting the user attributes after validation
		  String serviceURL = routes.Application.login().absoluteURL(request());
		  serviceURL = URLEncoder.encode(serviceURL, "UTF-8");

		  // no query means the user needs to login
		  if(query.size() == 0) {
		    // url to initiate CAS login
		    String loginURL = CAS_LOGIN + "?service=" + serviceURL;
		    return redirect(loginURL);
		  } else {
		    // after successful login from CAS, you get the ticket parameter
		    String[] tickets = query.get("ticket");
		    if(tickets.length > 0) {
		      String ticket = tickets[0];
		      // url to validate the ticket
		      String validateURL = CAS_VALIDATE + "?service=" + serviceURL + "&ticket=" + ticket;
		      // this junk is needed to parse the xml response from the CAS server
		      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		      DocumentBuilder db = dbf.newDocumentBuilder();
		      // this is where we make the actual request to validate and parse the response at the same time
		      Document doc = db.parse(new URL(validateURL).openStream());
		      // check for successful validation
		      doc.getElementsByTagName("cas:serviceResponse");
		      boolean success = doc.getElementsByTagName("cas:authenticationSuccess").getLength() > 0;
		      if(success) {
		        // if successful, get the username and save it into your session
		        String username = doc.getElementsByTagName("cas:user").item(0).getTextContent();
		        session().clear();
		        session("username", username);
		        //User.add(-1, username, "");
		        return redirect(routes.Application.submit());
		      } else {
		        // you could redirect to the CAS login here if you want to
		      }
		    }
		    return redirect(routes.Application.index());
		  }
		}
	
	public static Result logout() throws Exception {
	  // clear your session
	  session().clear();
	  String serviceURL = routes.Application.index().absoluteURL(request());
	  serviceURL = URLEncoder.encode(serviceURL, "UTF-8");
	  // redirect to CAS logout
	  return redirect(CAS_LOGOUT + "?service=" + serviceURL);
	}
	
}
