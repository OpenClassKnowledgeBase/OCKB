package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Page;
import com.avaje.ebean.Query;

import models.*;
import play.Logger;
import play.data.*;
import play.mvc.*;
import views.html.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
//CAS imports
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import javax.xml.parsers.*;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

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
	/*private static final String CAS_LOGIN = "https://authn.hawaii.edu/cas/login";
	private static final String CAS_VALIDATE = "https://authn.hawaii.edu/cas/serviceValidate";
	private static final String CAS_LOGOUT = "https://authn.hawaii.edu/cas/logout";*/
	
	// TEST CAS Variables for local testing
	private static final String CAS_LOGIN = "https://cas-test.its.hawaii.edu/cas/login";
	private static final String CAS_VALIDATE = "https://cas-test.its.hawaii.edu/cas/serviceValidate";
	private static final String CAS_LOGOUT = "https://cas-test.its.hawaii.edu/cas/logout";
	

	/**********************
	 *                    *
	 * MAIN PAGE METHODS *
	 *                    *
	 **********************/

	/**
	 * Renders the front page of the web application on initial load and when the OCKB button 
	 * is clicked on the navigational bar.
	 * 
	 * @return A rendered view of our index page.
	 */
	public static Result index() {
		return ok(views.html.index.render());
	}

	/**
	 * Renders a dashboard view of the users account based on their role within the web application.
	 * Different roles such as admin will provide more site functionality than a student role.
	 * 
	 * @return A rendered view of a user's dashboard.  
	 */
	public static Result dashboard() {
		String user = session("username");

		if (user == null) {
			return redirect(routes.Application.index());
		}
		else {
		    String userRole = User.getUser(user).role;
			List<Post> recentUserReplyList = Post.find.where().eq("userName", user).orderBy("latestActivity").findList();
			List<Post> recentUserPostList = Post.find.where().eq("userName", user).orderBy("latestActivity").findList();
			List<Post> topPosts = Post.getSortedByComments().subList(0, 3);
			
			return ok(views.html.dashboard.render(recentUserReplyList, recentUserPostList, userRole, topPosts));
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
	 * @param cid Category id
	 * @param page Page number
	 * @param sortBy String representing column to be sorted by
	 * @param order Descending (desc) or ascending (asc) order
	 * @param filter String used for keyword filter 
	 * @return Renders the category page view.
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
	
	/**
	 * Allows an admin user to edit Categories.  They can edit the Category name/description
	 * or delete the Category.
	 * 
	 * @return Redirects user to a view of the categories to edit.
	 */
	public static Result editCategories() {
	    
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
	    
	    return ok(views.html.editCategories.render(categoryList));
	}

	/**
	 * Allows an admin user to edit the Category name/description.
	 * 
	 * @param cid Category id
	 * @return Redirects user to a view of that particular Category to edit.
	 */
	public static Result editCategory(Long cid) {
	    
        Category currentCategory = Category.getCategory(cid);
        
        return ok(views.html.editCategory.render(currentCategory));
	}
	
	/**
	 * Updates a Category name/description if anything was changed.
	 * 
	 * @param cid Category id
     * @return Redirects user to a view of the categories to edit.
	 */
	public static Result updateCategory(Long cid) {
	    
        Category currentCategory = Category.getCategory(cid);

        final Map<String, String[]> values = request().body().asFormUrlEncoded();   
        String categoryName = values.get("categoryName")[0];
        String categoryDescription = values.get("categoryDescription")[0];

        currentCategory.title = categoryName;
        currentCategory.description = categoryDescription;
        currentCategory.save();
        
        return redirect(routes.Application.editCategories());	    	    
	}

    /**
     * Allows an admin user to add a new category from the edit category page. 
     * 
     * @return Redirects user to a view of the edit categories page.
     */
    public static Result addCategory() {       

        return ok(views.html.addCategory.render());
    }
    
	/**
	 * Allows an admin user to delete a category from the edit category page.
	 * 
	 * @param cid Category id
     * @return Redirects user to a view of the categories to edit.
	 */
	public static Result deleteCategory(Long cid) {
	    Category currentCategory = Category.getCategory(cid);
	    
	    List<Post> temp = Post.findAll();
	    for(Post p : temp) {
	        if(p.category.equals(currentCategory)) {
	            p.delete(p.id);
	        }
	    }
	    
        Category.delete(cid);

        return redirect(routes.Application.editCategories());	        
	}
	
	/**
     * An admin user can click this button to add the new Category to the list of categories.
	 * 
	 * @return Redirects the user to a view of the edit categories page with the newly made Category.
	 */
	public static Result addCategorySubmit() {
        final Map<String, String[]> values = request().body().asFormUrlEncoded();   
        String categoryName = values.get("categoryName")[0];
        String categoryDescription = values.get("categoryDescription")[0];
        String user = session("username");

        //A new category information is created.
        Category.create(categoryName, categoryDescription, "", false, user);	
        
        return redirect(routes.Application.editCategories());
	}
	
	
	public static Result sortByCourseOrder() {
        List<Category> categoryList = Category.findAll();

        //Removes categories with 'requested' boolean set to true
        for(int i = categoryList.size() - 1; i >= 0; i--) {
            if(categoryList.get(i).requested == true) {
                categoryList.remove(i);
            }
        }	    
	    return ok(views.html.sortByCourseOrder.render(categoryList));
	}
	
	
	/**********************
	 *                    *
	 *   POSTING METHODS  *
	 *                    *
	 **********************/
	
	/**
	 * View containing a single post and its comments
	 * 
	 * @param pid Id of shown post
	 * @return Renders the post page view
	 */
	public static Result post(Long pid) {	    
        String user = session("username");
        List<Comment> cmntList = Comment.find.where().eq("parent_post_id", pid).findList();
        if (user==null) { 
            user = "";
            String userRole = "";
            return ok(views.html.post.render(cmntList, Post.find.byId(pid), user, userRole));
        } else { 
            String userRole = User.getUser(user).role;
            return ok(views.html.post.render(cmntList, Post.find.byId(pid), user, userRole));
        }	
	}
	
	/**
	 * View containing submission form for a new post
	 * 
	 * @param cid Id of corresponding category
	 * @return Renders the submit post page view
	 */
	public static Result submitPost(Long cid) {
        Category currentCategory = Category.getCategory(cid);
        String user = session("username");
        String userRole = User.getUser(user).role;


        return ok(views.html.submitPost.render(currentCategory, userRole));
    } 

	/**
	 * Creates and saves newly submitted post from submit post page view
	 * 
	 * @param cid Id of corresponding category
	 * @return Redirect to category page view of current category
	 */
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
	
	/**
	 * Deletes a post from the application database
	 * 
	 * @param pid Id of post to be deleted
	 * @param cid Id of the category of the post to be deleted
	 * @return Redirect to category page view of deleted post
	 */
	public static Result deletePost(Long pid, Long cid) {
		Post.delete(pid);
		return redirect(routes.Application.category(cid, 0, "datePosted", "desc", ""));
	}

	/**
	 * Creates a comment for a specified post
	 * 
	 * @param cid Id of the category of the current post
	 * @param pid Id of the current post to be replied to
	 * @return Redirect to the post page view of the current post
	 */
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
	
	/**
	 * Makes a specified post sticky. This action is only available to privileged users.
	 * 
	 * @param cid Id of the category for the specified post
	 * @param pid Id of the specified post to be stickied
	 * @return Redirect to the category page view for specified post
	 */
	public static Result makePostSticky(Long cid, Long pid) {
	    Post currentPost = Post.getPost(pid);
	    currentPost.isSticky = true;
	    currentPost.save();
	    return redirect(routes.Application.category(cid, 0, "datePosted", "desc", ""));
	}
	
	/**
	 * Makes a specified post unsticky. This action is only available to privileged users.
	 * 
	 * @param cid Id of the category for the specified post
	 * @param pid Id of the specified post to be unstickied
	 * @return Redirect to the category page view for specified post
	 */
	public static Result unStickyPost(Long cid, Long pid) {
        Post currentPost = Post.getPost(pid);
        currentPost.isSticky = false;
        currentPost.save();
        return redirect(routes.Application.category(cid, 0, "datePosted", "desc", ""));	    
	}

	/**********************
	 *                    *
	 *   UTILITY METHODS  *
	 *                    *
	 **********************/    

	/**
	 * Redirects to a random category page view. Used within the dashboard
	 * 
	 * @return Redirect to a random category page view.
	 */
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
	
	/**
	 * Generates a random integer for the random() controller method.
	 * 
	 * @param min Minimum range for generated integer
	 * @param max Maximum range for generated integer
	 * @return Randomly generated integer with the range of min and max
	 */
	public static int randInt(int min, int max) {
		Random rand = new Random();

		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}    
	
	/**
	 * View containing table of Users and their privileges. Table is only viewable for privileged users
	 * 
	 * @return Renders user privileges page view
	 */
	public static Result userPriv() {
		List<User> userList = User.findAll();
		return ok(views.html.userPriv.render(userList));
	}
	
	/**
	 * View contained notification of user
	 * 
	 * @return Renders notifications page view
	 */
	public static Result notifications() {
		return ok(views.html.notifications.render());
	}

	/***********************
	 *                     *
	 *  CAS LOGIN METHODS  *
	 *                     *
	 ***********************/    

	/**
	 * Controller method that handles request and validation of CAS authentication
	 * 
	 * @return Redirect to appropriate page depending on authentication success or failure
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws MalformedURLException 
	 */
	public static Result login() throws ParserConfigurationException, MalformedURLException, SAXException, IOException {
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
	
	/**
	 * Logs the current user out of their CAS session
	 * 
	 * @return Redirect to the CAS logout service URL
	 * @throws UnsupportedEncodingException 
	 */
	public static Result logout() throws UnsupportedEncodingException {
		// clear your session
		session().clear();
		String serviceURL = routes.Application.index().absoluteURL(request());
		serviceURL = URLEncoder.encode(serviceURL, "UTF-8");
		// redirect to CAS logout
		return redirect(CAS_LOGOUT + "?service=" + serviceURL);
	}

	public static Result editor(String input, String output) {
	    return ok(views.html.editor.render(input, output));
	}
	
	public static Result submitCode() throws Exception {
	    final Map<String, String[]> values = request().body().asFormUrlEncoded();
        String javaCode = values.get("javaSource")[0];
        String[] tokens = javaCode.split("\\s+");
        String className = "";
        for (int i = 0; i < tokens.length; i++) {
            Logger.debug(tokens[i]);
            if (tokens[i].equals("class")) {
                className = tokens[i+1];
                break;
            }
        }
        String path = System.getProperty("java.io.tempDir");
        if (path == null) {
            path = System.getProperty("user.home") + "/Desktop";
        }
        Logger.debug("Result=" + javaCode + "\nPath=" + path);
        
        Files.write(Paths.get(path + "/" + className + ".java"), javaCode.getBytes());
        
        String output1 = runProcess("javac " + path + "/" + className + ".java");
        String output2 = runProcess("java -cp " + path + " " + className);
        
        
	    return redirect(routes.Application.editor(javaCode, output1 + "\n" + output2));
	    
	}
	
	private static String printLines(String name, InputStream ins) throws Exception {
	    String line = null;
	    StringBuilder sb = new StringBuilder();
	    BufferedReader in = new BufferedReader(
	        new InputStreamReader(ins));
	    while ((line = in.readLine()) != null) {
	        sb.append(line + "<br>");
	    }
	    return sb.toString();
	  }

	  private static String runProcess(String command) throws Exception {
	    Process pro = Runtime.getRuntime().exec(command);
	    String output = "";
	    output+= printLines(command + " stdout:", pro.getInputStream());
	    output+= printLines(command + " stderr:", pro.getErrorStream());
	    pro.waitFor();
	   // System.out.println(command + " exitValue() " + pro.exitValue());
	    return output;
	  }


}
