package controllers;

import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import models.Comment;
import play.core.Router.RouteParams;
import play.mvc.*;

public class Application extends Controller {
	
	private static final String CAS_LOGIN = "https://cas-test.its.hawaii.edu/cas/login";
	private static final String CAS_VALIDATE = "https://cas-test.its.hawaii.edu/cas/serviceValidate";
	private static final String CAS_LOGOUT = "https://cas-test.its.hawaii.edu/cas/logout";
	private static String user = "";

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
		        user = username;
		        return redirect(routes.Application.submit());
		      } else {
		        // you could redirect to the CAS login here if you want to
		      }
		    }
		    return redirect(routes.Application.index());
		  }
		}
	
	// logging out is easy
	public static Result logout() throws Exception {
	  // clear your session
	  session().clear();
	  String serviceURL = routes.Application.index().absoluteURL(request());
	  serviceURL = URLEncoder.encode(serviceURL, "UTF-8");
	  // redirect to CAS logout
	  return redirect(CAS_LOGOUT + "?service=" + serviceURL);
	}







}
