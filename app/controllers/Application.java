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
	/* CAS Variables */
	private static final String CAS_LOGIN = "https://cas-test.its.hawaii.edu/cas/login";
	private static final String CAS_VALIDATE = "https://cas-test.its.hawaii.edu/cas/serviceValidate";
	private static final String CAS_LOGOUT = "https://cas-test.its.hawaii.edu/cas/logout";
	private static String user = "";
	

    public static Result index() {
        return ok(views.html.index.render("Welcome to the home page."));
    }
    
    public static Result post(Long pid) {
    	String user = session("username");
    	List<Comment> cmntList = Comment.all();
    	return ok(views.html.post.render(cmntList, Post.find.byId(pid), user));
    }
    
    public static Result dashboard() {
    	String user = session("username");
    	if (user == null) {
    		return redirect(routes.Application.index());
    	}
    	else {
    		return ok(views.html.dashboard.render());
    	}
    }
    
    public static Result category(Long cid) {
    	String user = session("username");
    	List<Post> postList = Post.find.where().eq("category_id", cid).eq("isSticky", false).findList();
    	List<Post> stickyList = Post.find.where().eq("category_id", cid).eq("isSticky", true).findList();
    	Category currentCategory = Category.getCategory(cid);
    	return ok(views.html.category.render(stickyList, postList, currentCategory, user));
    }
    
    public static Result categories() {
    	//figure out how to put this in global
    		List<Category> categoryList = Category.findAll();
        	return ok(views.html.categories.render(categoryList));
    }
    
    public static Result userPriv() {
    	List<User> userList = User.findAll();
    	return ok(views.html.userPriv.render(userList));
    }
    
    public static Result submissionView() {
    	
    	return ok(views.html.submissionView.render());
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
		        return redirect(routes.Application.categories());
		      } else {
		        return redirect(routes.Application.login());
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
