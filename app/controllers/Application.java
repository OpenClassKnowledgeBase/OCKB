package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Page;
import com.avaje.ebean.Query;

import models.*;
import play.data.*;
import play.mvc.*;
import views.html.*;

//CAS imports
import java.net.*;
import java.util.*;

import javax.xml.parsers.*;

import org.w3c.dom.Document;

/**
 * This class is a controller that helps users navigate through our web application.
 * 
 * @author Renzee Reyes
 * @author Erika Nana
 * @author Tyler Pascua
 * @author Alan Ho
 */
public class Application extends Controller {	 
	// CAS Variables 
	/*
	private static final String CAS_LOGIN = "https://authn.hawaii.edu/cas/login";
	private static final String CAS_VALIDATE = "https://authn.hawaii.edu/cas/serviceValidate";
	private static final String CAS_LOGOUT = "https://authn.hawaii.edu/cas/logout";
	 */


	// TEST CAS Variables for local testing
	private static final String CAS_LOGIN = "https://cas-test.its.hawaii.edu/cas/login";
	private static final String CAS_VALIDATE = "https://cas-test.its.hawaii.edu/cas/serviceValidate";
	private static final String CAS_LOGOUT = "https://cas-test.its.hawaii.edu/cas/logout";

	/**********************
	 *                    *
	 * NAVIGATION METHODS *
	 *                    *
	 **********************/

	/**
	 * Renders the front page of the web application on initial load and when the OCKB button 
	 * is clicked on the navigational bar.
	 * 
	 * @return A rendered view of our index page.
	 */
	public static Result index() {
		return ok(views.html.index.render("Welcome to the home page."));
	}

	/**
	 * Renders a dashboard view of the users account based on their role within the web application.
	 * Different roles such as admin will provide more site functionality than a student role.
	 * 
	 * @return A rendered view of a user's dashboard.  
	 */
	public static Result dashboard() {
		String user = session("username");
		
		// User tables not setup yet, will default userRole to 'admin' for now
		// String userRole = User.getUser(user).role;
		
		String userRole = "admin";

		if (user == null) {
			return redirect(routes.Application.index());
		}
		else {
			List<Post> recentUserReplyList = Post.find.where().eq("userName", user).orderBy("latestActivity").findList();
			List<Post> recentUserPostList = Post.find.where().eq("userName", user).orderBy("latestActivity").findList();
			return ok(views.html.dashboard.render(recentUserReplyList, recentUserPostList, userRole));
		}
	}   

	/**********************
	 *                    *
	 *  CATEGORY METHODS  *
	 *                    *
	 **********************/

	/**
	 * Renders a list of categories currently in the web application.  Categories with the 'requested'
	 * boolean are not rendered.  Categories will then be sorted based on their Title.
	 * 
	 * @return A rendered view of all the categories in the web application.
	 */
	public static Result categories() {
		//figure out how to put this in global
		List<Category> categoryList = Category.findAll();

		//Removes categories with 'requested' boolean set to true
		for(int i = categoryList.size() - 1; i >= 0; i--) {
			if(categoryList.get(i).requested == true) {
				categoryList.remove(i);
			}
		}

		//Sorts the categories based on their Title
		Collections.sort(categoryList, new Comparator<Category>() {
			@Override
			public int compare(final Category object1, final Category object2) {
				return object1.getTitle().compareTo(object2.getTitle());
			}
		} );

		return ok(views.html.categories.render(categoryList));
	}    

	/**
	 * Renders a specific category page view.
	 * 
	 * @param cid 
	 * @param page 
	 * @param sortBy
	 * @param order
	 * @param filter
	 * @return
	 */
	public static Result category(Long cid, int page, String sortBy, String order, String filter) {
		String user = session("username");
		//List<Post> postList = Post.find.where().eq("category_id", cid).eq("isSticky", false).findList();
		List<Post> stickyList = Post.find.where().eq("category_id", cid).eq("isSticky", true).findList();

		Category currentCategory = Category.getCategory(cid);
		Page<Post> currentPage = Post.getPosts(cid, page, 10, sortBy, order, filter);

		return ok(views.html.category.render(stickyList, currentPage, sortBy, order, filter, currentCategory, user));
	}
	/**
	 * Renders the requested category page view.
	 * 
	 * @return Renders the requested category page view.
	 */
	public static Result requestCategory() {
		return ok(views.html.requestCategory.render());
	}

	/**
	 * Users click this button to submit their request to the professor.
	 * 
	 * @return Redirects users to a view of the categories.
	 */
	public static Result requestCategorySubmit() {
		final Map<String, String[]> values = request().body().asFormUrlEncoded();  	
		String requestedCategory = values.get("requestedCategory")[0];
		String requestedCategoryReason = values.get("requestCategoryReason")[0];
		String user = session("username");

		Category.create(requestedCategory, requestedCategoryReason, "", true, user);

		return redirect(routes.Application.categories());
	}

	/**
	 * Allows a Professor/TA/admin to view the currently requested categories submitted from students.
	 * They may also Approve or Deny a students request form this view.
	 * 
	 * @return A rendered page to view the requested categories submitted by students.
	 */
	public static Result viewRequestedCategories() {
		List<Category> allCategories = Category.findAll();
		String user = session("username");

		//If a category is already created, it will be removed from the list retrieved
		//since we only need new requested categories.
		for(int i = allCategories.size() - 1; i >= 0; i--) {
			if(allCategories.get(i).requested == false) {
				allCategories.remove(i);
			}
		}

		return ok(views.html.viewRequestedCategories.render(allCategories, user));    	
	}

	/**
	 * A Professor/TA/admin user approves the requested category and is taken to a rendered page to
	 * make necessary adjustments to the new category title and to create a description.
	 * 
	 * @param cid Category identifier
	 * @return Rendered view of a page to approve the category.
	 */
	public static Result approveRequestedCategory(Long cid) {   	
		Category requestedCategory = Category.getCategory(cid);

		return ok(views.html.approveRequestedCategory.render(requestedCategory));
	}

	/**
	 * A Professor/TA/admin user may click this approve button to make changes to the web application
	 * after making adjustments to the category title and creating a description.
	 * 
	 * @param cid Category identifier
	 * @return Redirects users to a view of the categories.
	 */
	public static Result approveRequestedCategorySubmit(Long cid) {   	
		final Map<String, String[]> values = request().body().asFormUrlEncoded();  	
		String categoryName = values.get("categoryName")[0];
		String categoryDescription = values.get("categoryDescription")[0];
		String user = session("username");

		//The old requested category is removed from the database.
		Category.delete(cid);
		//A new category with the updated information is created.
		Category.create(categoryName, categoryDescription, "", false, user);

		return redirect(routes.Application.categories());
	}

	/**
	 * A Professor/TA/admin user may deny a requested category.
	 * 
	 * @param cid Category identifier
	 * @return Redirects user to a view of the requested categories.
	 */
	public static Result denyRequestedCategory(Long cid) {
		Category.delete(cid);

		return redirect(routes.Application.viewRequestedCategories());
	}

	/**********************
	 *                    *
	 *   POSTING METHODS  *
	 *                    *
	 **********************/

	public static Result post(Long pid) {
		String user = session("username");
		List<Comment> cmntList = Comment.find.where().eq("parent_post_id", pid).findList();
		return ok(views.html.post.render(cmntList, Post.find.byId(pid), user));
	}

	public static Result createPost(Long cid) {
		String user = session("username");
		Category currentCategory = Category.getCategory(cid);

		//To pull information from the two forms (temporary).
		final Map<String, String[]> values = request().body().asFormUrlEncoded();  	
		String postTitle = values.get("postTitle")[0];
		String postContent = values.get("postContent")[0];
		String stickyPost = "";

		try {
			stickyPost = values.get("stickyPost")[0];
		} catch (NullPointerException e) {
			//If null that means the Radio button was not selected, therefore user does not have Sticky privileges.
		}

		Boolean isSticky = stickyPost.equals("on");

		//Create post with gathered information.
		Post.create(currentCategory, postTitle, postContent, user, isSticky);

		return redirect(routes.Application.category(currentCategory.id, 0, "datePosted", "desc", ""));
	}
	public static Result deletePost(Long pid, Long cid) {
		Post.delete(pid);
		return redirect(routes.Application.category(cid, 0, "datePosted", "desc", ""));
	}
	public static Result submitPost(Long cid) {
		Category currentCategory = Category.getCategory(cid);
		String user = session("username");
		String userRole = User.getUser(user).role;


		return ok(views.html.submitPost.render(currentCategory, userRole));
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

	/**********************
	 *                    *
	 *   UTILITY METHODS  *
	 *                    *
	 **********************/    


	public static Result random() {
		String user = session("username");
		if (user == null) {
			return redirect(routes.Application.index());
		}
		else {
			List<Category> categoryList = Category.findAll();
			int random = randInt(1, categoryList.size());
			Long randomLong = new Long(random);
			return redirect(routes.Application.category(randomLong, 0, "datePosted", "desc", ""));
		}
	}

	public static int randInt(int min, int max) {
		Random rand = new Random();

		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}    

	public static Result userPriv() {
		List<User> userList = User.findAll();
		return ok(views.html.userPriv.render(userList));
	}

	public static Result notifications() {
		return ok(views.html.notifications.render());
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

	/***********************
	 *                     *
	 *  CAS LOGIN METHODS  *
	 *                     *
	 ***********************/    

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


}
