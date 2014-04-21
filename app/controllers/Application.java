package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Page;

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
	// CAS Variables 
	
	private static final String CAS_LOGIN = "https://authn.hawaii.edu/cas/login";
	private static final String CAS_VALIDATE = "https://authn.hawaii.edu/cas/serviceValidate";
	private static final String CAS_LOGOUT = "https://authn.hawaii.edu/cas/logout";
	
	
	/*
	// TEST CAS Variables for local testing
	private static final String CAS_LOGIN = "https://cas-test.its.hawaii.edu/cas/login";
	private static final String CAS_VALIDATE = "https://cas-test.its.hawaii.edu/cas/serviceValidate";
	private static final String CAS_LOGOUT = "https://cas-test.its.hawaii.edu/cas/logout";
	*/

	private static String user = "";
	private static final int NEWEST = 1;
	private static final int VOTES = 2;
	private static final int COMMENTS = 3;

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
    		List<Post> recentUserReplyList = Post.find.where().eq("userName", user).orderBy("latestActivity").findList();
    		List<Post> recentUserPostList = Post.find.where().eq("userName", user).orderBy("latestActivity").findList();
    		return ok(views.html.dashboard.render(recentUserReplyList, recentUserPostList));
    	}
    }
    
    public static Result random() {
    	String user = session("username");
    	if (user == null) {
    		return redirect(routes.Application.index());
    	}
    	else {
    		List<Category> categoryList = Category.findAll();
    		int random = randInt(1, categoryList.size());
    		Long randomLong = new Long(random);
    		return redirect(routes.Application.category(randomLong, 0, "datePosted", "desc"));
    	}
    }
    
    public static Result category(Long cid, int page, String sortBy, String order) {
    	String user = session("username");
    	
    	//List<Post> postList = Post.find.where().eq("category_id", cid).eq("isSticky", false).findList();
    	List<Post> stickyList = Post.find.where().eq("category_id", cid).eq("isSticky", true).findList();
    	
    	Category currentCategory = Category.getCategory(cid);
    	Page currentPage = Post.getPosts(cid, page, 10, sortBy, order);
    	
    	return ok(views.html.category.render(stickyList, currentPage, sortBy, order, currentCategory, user));
    }
    
    /**
    public static Result sort(Long cid, int sort) {
    	String user = session("username");
    	List<Post> postList = new ArrayList<Post>();
    	Page<Post> postPage = Post.getPosts(cid, 1, 10, "datePosted", "desc");;
    	String currentSort = "datePosted";
    	String currentOrder = "desc";
     	switch (sort) {
    		case NEWEST:
    			 postList = Post.find.where().eq("category_id", cid).eq("isSticky", false).orderBy("datePosted desc").findList();
    			 postPage = Post.getPosts(cid, 1, 10, "datePosted", "desc");
    			 currentSort = "datePosted";
    			 currentOrder = "desc";
    			break;
    		case VOTES:
    			postList = Post.find.where().eq("category_id", cid).eq("isSticky", false).orderBy("votes desc").findList();
    			postPage = Post.getPosts(cid, 1, 10, "votes", "desc");
    			currentSort = "votes";
    			currentOrder = "desc";
    			break;
    		case COMMENTS:
    			postList = Post.find.where().eq("category_id", cid).eq("isSticky", false).orderBy("comments desc").findList();
    			postPage = Post.getPosts(cid, 1, 10, "comments", "desc");
    			currentSort = "comments";
    			currentOrder = "desc";
    			break;
    	}
    	List<Post> stickyList = Post.find.where().eq("category_id", cid).eq("isSticky", true).findList();
    	Category currentCategory = Category.getCategory(cid);
    	
    	return ok(views.html.category.render(stickyList, postPage, currentSort, currentOrder, currentCategory, user));
    }**/
    
    public static Result categories() {
    	//figure out how to put this in global
    	List<Category> categoryList = Category.findAll();
        return ok(views.html.categories.render(categoryList));
    }
    
    public static Result submitPost(Long cid) {
    	Category currentCategory = Category.getCategory(cid);

    	return ok(views.html.submitPost.render(currentCategory));
    }
    
    public static Result createPost(Long cid) {
    	String user = session("username");
    	Category currentCategory = Category.getCategory(cid);
    	List<Post> postList = Post.find.where().eq("category_id", cid).eq("isSticky", false).findList();
    	List<Post> stickyList = Post.find.where().eq("category_id", cid).eq("isSticky", true).findList();
    	
    	//To pull information from the two forms (temporary).
    	final Map<String, String[]> values = request().body().asFormUrlEncoded();  	
    	String postTitle = values.get("postTitle")[0];
    	String postContent = values.get("postContent")[0];
    	
    	//Create post with gathered information.
    	Post.create(currentCategory, postTitle, postContent, user);
    	
		return redirect(routes.Application.category(currentCategory.id, 0, "datePosted", "desc"));
    }
    
    public static Result createComment(Long cid, Long pid) {
    	//To pull information from the two forms (temporary).
    	final Map<String, String[]> values = request().body().asFormUrlEncoded();  	
    	String commentData = values.get("editor1")[0];
    	String user = session("username");
    	Post parentPost = Post.getPost(pid);
    	
    	//Create comment
    	Comment.create(parentPost, user, commentData);
    	
    	return redirect(routes.Application.post(pid));
    }
    public static Result userPriv() {
    	List<User> userList = User.findAll();
    	return ok(views.html.userPriv.render(userList));
    }
    
    public static Result notifications() {
    	return ok(views.html.notifications.render());
    }
    
    public static Result requestCategory() {
    	return ok(views.html.requestCategory.render());
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
		        return redirect(routes.Application.dashboard());
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
	
	public static int randInt(int min, int max) {
	    Random rand = new Random();

	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
}
