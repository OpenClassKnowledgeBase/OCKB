package controllers;

import com.avaje.ebean.Ebean;


//EXCEL IMPORTS
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import play.mvc.Http.MultipartFormData;

import com.avaje.ebean.Page;
import com.avaje.ebean.Query;

import models.*;
import play.Logger;
import play.data.*;
import play.mvc.*;
import views.html.*;
import play.Routes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

//CAS imports
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            User user1 = User.getUser(user);
            List<CodeChallengeScore> codeChallengeList = CodeChallengeScore.find.where().eq("user_id", user1.id).findList();
                
            return ok(views.html.dashboard.render(userRole, codeChallengeList, user1));
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

        return ok(views.html.categories.render(getCategoriesView()));
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

        List<CodeChallenge> challengeList= CodeChallenge.find.where().eq("categoryId", cid).findList();

        return ok(views.html.category.render(stickyList, currentPage, sortBy, order, filter, currentCategory, user, challengeList));
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

        return ok(views.html.editCategories.render(getCurrentSortOrderList()));
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

    /**
     * 
     * 
     * @return
     */
    public static Result sortByCourseOrder() {

        return ok(views.html.sortByCourseOrder.render(getCurrentSortOrderList()));
    }

    /**
     * 
     * 
     * @return
     */
    public static Result saveSortByCourseOrder() {

        final Map<String, String[]> values = request().body().asFormUrlEncoded();   
        String sortCourseOrder = values.get("sortCourseOrder")[0];

        Course course = Course.getCourse(1L);     
        course.currentSortOrder = sortCourseOrder;  
        course.categoryOrder = "Sort by Course Order";
        course.save();

        return ok(views.html.manageCategories.render(getCurrentSortOrderString()));
    }	

    /**
     * 
     * 
     * @return
     */
    public static Result manageCategories() {
        String currentSortOrder = getCurrentSortOrderString();

        return ok(views.html.manageCategories.render(currentSortOrder));	    
    }

    /**
     * 
     * 
     * @return
     */
    public static Result sortAlphabetically() {
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

        Course course = Course.getCourse(1L);

        String concatIdOrder = "";
        for(Category c : categoryList) {
            concatIdOrder += c.id + ",";
        }

        course.currentSortOrder = concatIdOrder;
        course.categoryOrder = "Alphabetically";
        course.save();

        return ok(views.html.manageCategories.render(getCurrentSortOrderString()));	    
    }

    /**
     * 
     * 
     * @return
     */
    public static Result hideOrShowCategories() {

        return ok(views.html.hideCategories.render(getCurrentSortOrderList()));
    }

    /**
     * 
     * 
     * @return
     */
    public static Result updateHideCategories() {
        final Map<String, String[]> values = request().body().asFormUrlEncoded();   

        List<Category> categoryList = getCurrentSortOrderList();

        for(Category c : categoryList) {
            if(values.get(c.id + "")[0].equals("1")) {
                c.hidden = true;
            } else {
                c.hidden = false;
            }
            c.save();
        }

        return ok(views.html.hideCategories.render(getCurrentSortOrderList()));
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
        Post.create(currentCategory, postTitle, postContent, user, isSticky, "");

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

    /******************************
     *                            *
     *   CODE CHALLENGE METHODS   *
     *                            *
     ******************************/ 

    /**
     * 
     * 
     * @return
     */
    public static Result createCodeChallenge() {
        List<Category> categoryList = Category.findAll();

        return ok(views.html.createCodeChallenge.render(categoryList));
    }

    /**
     * 
     * 
     * @return
     */
    public static Result createCodeChallengePost() {
        List<Category> categoryList = Category.findAll();
        final Map<String, String[]> values = request().body().asFormUrlEncoded(); 

        String codeChallengeTitle = values.get("codeChallengeTitle")[0];
        String categorySelect = values.get("categorySelect")[0];
        String description = values.get("question")[0];
        String output = values.get("output")[0]; 
        String hours = values.get("hours")[0];
        String minutes = values.get("minutes")[0];
        String seconds = values.get("seconds")[0];

        try {           
            int challengeHours;
            int challengeMinutes;
            int challengeSeconds;

            if(hours.equals("")) {
                challengeHours = 0;
            } else {
                challengeHours = Integer.parseInt(hours);
            }

            if(minutes.equals("")) {
                challengeMinutes = 0;
            } else {
                challengeMinutes = Integer.parseInt(minutes);
            }

            if(seconds.equals("")) {
                challengeSeconds = 0;
            } else {
                challengeSeconds = Integer.parseInt(seconds);
            }

            Integer timeLimit = (challengeHours * 3600) + (challengeMinutes * 60) + (challengeSeconds * 1);            
            Long categoryId = Long.parseLong(categorySelect);

            CodeChallenge.create(codeChallengeTitle, description, output, timeLimit, categoryId);

        } catch(NumberFormatException nfe) {
            //nothing for now
        }

        return ok(views.html.createCodeChallenge.render(categoryList));
    }

    public static Result codeChallenge(Long chid) {  
        User currentUser = User.getUser(session("username")); 
        CodeChallenge challenge = CodeChallenge.getChallenge(chid);
        
        CodeChallengeScore challengeScore = CodeChallengeScore.getScore(chid, currentUser.id);
        
        return ok(views.html.codeChallenge.render(challenge, challengeScore));        

    }	

    /**********************
     *                    *
     *   UTILITY METHODS  *
     *                    *
     **********************/    

    /**
     * 
     * 
     * @return
     */
    public static Result createCourse() {
        final Map<String, String[]> values = request().body().asFormUrlEncoded();   
        String inputSemester = values.get("inputSemester")[0]; //e.g. Fall 2014
        String courseSelect = values.get("courseSelect")[0]; //ICS 111
        Integer courseSection = Integer.parseInt(values.get("courseSection")[0]); //001
        String courseTitle = values.get("courseTitle")[0]; //Intro to Computer Science I

        //For Edit Course Information - needs list of all Courses for selection.
        List<Course> courses = Course.findAll(); 

        Course.create(courseTitle, "", "", courseSection, inputSemester, courseSelect);

        return ok(views.html.courseSettings.render(getStudentRoster(0L), courses));
    }

    /**
     * 
     * 
     * @return
     */
    public static Result courseSettings() {
        List<Course> courses = Course.findAll();

        return ok(views.html.courseSettings.render(getStudentRoster(0L), courses));
    }

    /**
     * 
     * 
     * @return
     */
    public static Result courseRoster() {

        return ok(views.html.courseRoster.render(getStudentRoster(0L)));
    }

    /**
     * 
     * 
     * @return
     */
    @BodyParser.Of(BodyParser.MultipartFormData.class)
    public static Result uploadExcelRoster() {

        Http.MultipartFormData body = request().body().asMultipartFormData();        
        Http.MultipartFormData.FilePart temp = body.getFile("courseRosterExcel");
        final Map<String, String[]> values = body.asFormUrlEncoded();    

        Long courseID = Long.parseLong(values.get("courseId")[0]);

        File uploadedFile = temp.getFile();
        System.out.println(temp.getFilename());
        System.out.println(temp.getContentType());

        Course course = Course.getCourse(courseID);
        course.studentRoster = "";

        try {

            FileInputStream file = new FileInputStream(uploadedFile);

            //Get the workbook instance for XLS file
            XSSFWorkbook workbook = new XSSFWorkbook (file);

            //Get first sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);

            //Iterate through each rows from first sheet
            Iterator<Row> rowIterator = sheet.iterator();
            while(rowIterator.hasNext()) {
                Row row = rowIterator.next();

                //For each row, iterate through each columns
                Iterator<Cell> cellIterator = row.cellIterator();
                while(cellIterator.hasNext()) {

                    Cell cell = cellIterator.next();

                    switch(cell.getCellType()) {
                    case Cell.CELL_TYPE_BOOLEAN:
                        //System.out.print(cell.getBooleanCellValue() + "\t");
                        course.studentRoster += cell.getBooleanCellValue() + "";
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        //System.out.print(cell.getNumericCellValue() + "\t");
                        course.studentRoster += cell.getNumericCellValue() + "";
                        break;
                    case Cell.CELL_TYPE_STRING:
                        //System.out.print(cell.getStringCellValue() + "\t");
                        course.studentRoster += cell.getStringCellValue();
                        break;
                    }

                    if(cellIterator.hasNext()) {
                        course.studentRoster += ",";
                    }
                }

                if(rowIterator.hasNext()) {
                    course.studentRoster += "|";
                }

            }
            course.save();

            file.close();
            FileOutputStream out =          //RENZEE Might need to change to your Desktop path when testing.
                    new FileOutputStream(new File("C:\\Users\\Alaan\\Desktop\\test1.xlsx"));
            //Uncomment below to write a file to your Desktop
            //workbook.write(out);
            out.close();           

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } 
        List<Course> courses = Course.findAll();

        return ok(views.html.courseSettings.render(getStudentRoster(0L), courses));    
    }

    /**
     * 
     * 
     * @return
     */
    public static List<Category> getCategoriesView() {
        Course course = Course.getCourse(1L);

        List<Category> sortCourseOrder = new ArrayList<Category>();
        String[] formSplit = course.currentSortOrder.split(",");      
        List<Category> categoryList = Category.findAll();

        for(int i = 0; i < formSplit.length; i++) {
            Category c = categoryList.get(Integer.parseInt(formSplit[i]) - 1);
            if(c.hidden) {
                //hidden variable is true, so don't add to list.
            } else {
                sortCourseOrder.add(c);
            }
        }

        return sortCourseOrder;
    }

    /**
     * 
     * 
     * @return
     */
    public static List<Category> getCurrentSortOrderList() {   
        Course course = Course.getCourse(1L);

        List<Category> sortCourseOrder = new ArrayList<Category>();
        String[] formSplit = course.currentSortOrder.split(",");

        List<Category> categoryList = Category.findAll();

        for(int i = 0; i < formSplit.length; i++) {
            sortCourseOrder.add(categoryList.get(Integer.parseInt(formSplit[i])-1));
        }

        return sortCourseOrder;
    }

    /**
     * 
     * 
     * @return
     */
    public static String getCurrentSortOrderString() {
        Course course = Course.getCourse(1L);
        String sortOrder = course.categoryOrder;

        return sortOrder;
    }
    
    public static Result calendar() {
        return ok(views.html.calendar.render());
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


    /***********************
     *                     *
     *  EDITOR METHODS     *
     *                     *
     ***********************/


    public static Result editor(Long chid, String input, String output, String gradingResults, Long points) {
        CodeChallenge challenge = CodeChallenge.getChallenge(chid);
        return ok(views.html.editor.render(challenge, input, output, gradingResults, points));
    }

    public static Result submitCode(Long chid) throws Exception {
        final Map<String, String[]> values = request().body().asFormUrlEncoded();
        String javaCode = values.get("javaSource")[0];
        String[] tokens = javaCode.split("\\s+");
        String className = "";
        for (int i = 0; i < tokens.length; i++) {
            //Logger.debug(tokens[i]);
            if (tokens[i].equals("class")) {
                className = tokens[i+1];
                break;
            }
        }
        String path = System.getProperty("java.io.tempDir");
        if (path == null) {
            path = System.getProperty("user.home") + "/Desktop";
        }
        //Logger.debug("Result=" + javaCode + "\nPath=" + path);

        Files.write(Paths.get(path + "/" + className + ".java"), javaCode.getBytes());

        String output1 = runProcess("javac " + path + "/" + className + ".java");
        String output2 = runProcess("java -cp " + path + " " + className);
        String finalOutput = "";
        String gradingResults = "";
        long points = 0;
        Pattern pattern = Pattern.compile("");
        Matcher matcher = pattern.matcher(javaCode);

        if (output1.equals("")) {
            finalOutput = output2;
            CodeChallenge currentChallenge = CodeChallenge.getChallenge(chid);

            String[] requiredSource = currentChallenge.requiredSource.split(",");
            for (int i = 0; i < requiredSource.length; i=i+3) {
                pattern = Pattern.compile(requiredSource[i]);
                matcher = pattern.matcher(javaCode);

                gradingResults += requiredSource[i+1];
                if (matcher.find()) {
                    gradingResults += " - PASSED\n    +" + requiredSource[i+2] + " Point(s)\n";
                    points += Integer.parseInt(requiredSource[i+2]);
                } else {
                    gradingResults += " - FAILED\n    +0 Point(s)\n";
                }
            }

            pattern = Pattern.compile(currentChallenge.requiredOutput);
            matcher = pattern.matcher(finalOutput);
            gradingResults += "Matched Output";
            if (matcher.find()) {
                gradingResults += " - PASSED\n    +1 Point(s)\n";
                points += 1;
            } else {
                gradingResults += " - FAILED\n    +0 Point(s)\n";
            }

        } else {
            finalOutput = output1 + "\n" + output2;
            gradingResults += "Failed to compile\n";
        }


        gradingResults += "===============\nTotal Points: " + points;

        return redirect(routes.Application.editor(chid, javaCode, finalOutput, gradingResults, points));

    }

    private static String printLines(String name, InputStream ins) throws Exception {
        String line = null;
        StringBuilder sb = new StringBuilder();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(ins));
        while ((line = in.readLine()) != null) {
            sb.append(line + "\n");
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

    public static Result submitChallengeScore(Long chid, Long points) {
        CodeChallenge currentChallenge = CodeChallenge.getChallenge(chid);
        User currentUser = User.getUser(session("username"));
        Logger.debug("" + currentUser.id);
        List<CodeChallengeScore> userScoreList = CodeChallengeScore.getScoresForUser(currentUser.id);
        CodeChallengeScore existingScore = null;

        for (CodeChallengeScore score : userScoreList) {
            if (score.challenge.id == chid && points >= score.score) {
                existingScore = score;
                break;
            }
        }
        if (existingScore == null) {
            CodeChallengeScore.create(currentChallenge, currentUser, points);
        } else if (points > existingScore.score){
            existingScore.setScore(points);
        }

        return redirect(routes.Application.codeChallenge(chid));

    }
    
    /**
     * 
     * 
     * @return
     */
    public static Result javascriptRoutes() {
        response().setContentType("text/javascript");
        return ok(
                Routes.javascriptRouter("myJsRoutes",
                        routes.javascript.Application.vote()
                        )
                );
    }
    
    /**
     * Allows a user to vote on a post.
     * 
     * A User can only vote on a post once. Downvoting is disabled in order to promote
     * a positive learning environment. Since this method is only called through JavascriptRouting,
     * a simple ok is returned.
     * 
     * @param pid Post id
     * @return ok
     */
    public static Result vote(Long pid) {      
        String user = session("username");       
        Post post = Post.getPost(pid);
        post.votes += 1;
        
        if(!post.usersVoted.contains(user)) {
            post.usersVoted += user + " ";
        }         
        post.save();

        return ok();
    }

    /**
     * Allows a professor to view different course rosters.
     * 
     * A professor can select a new course and click 'Change Course' to view
     * it's Course Roster.
     * 
     * @return A view of the selected courses' roster.
     */
    public static Result changeCourse() {
        final Map<String, String[]> values = request().body().asFormUrlEncoded();   
        Long courseID = Long.parseLong(values.get("courseID")[0]); 
        List<Course> courseList = Course.findAll();

        return ok(views.html.courseSettings.render(getStudentRoster(courseID), courseList));
    }


    /**
     * Creates a List of a List of student information.
     * 
     * The list is created from information gathered from the uploaded
     * Excel file. Needs updating to reflect future courses.
     * 
     * @return A list of a list containing student information.
     */
    public static List<List<String>> getStudentRoster(Long cid) {
        Course course = null;

        if(cid == 0) {
            course = Course.getCourse(1L);
        } else {
            course = Course.getCourse(cid);
        }

        List<List<String>> table = new ArrayList<>();
        String[] rows = course.studentRoster.split("\\|");

        for(String s : rows) {
            table.add(Arrays.asList(s.split(",")));
        }

        return table;
    }
    
    /**
     * View a user's code challenge results.
     * 
     * Allows a professor to view a user's code challenge results.
     * Might need to change method name later if it's only for code challenge.
     * e.g., userCodeChallengeResults
     * 
     * @param uid User id
     * @return A view of the user's code challenge results.
     */
    public static Result viewUser(String uid) {
        User user = User.getUser(uid);
        List<CodeChallengeScore> codeChallengeList = CodeChallengeScore.find.where().eq("user_id", user.id).findList();
        
        return ok(views.html.user.render(user, codeChallengeList));
    }
    
    /**
     * View a code challenges' results.
     * 
     * Allows a professor to view all of the students who have participated in
     * a selected code challenge.
     * 
     * @return A view of a code challenges' results.
     */
    public static Result codeChallengeResults() {
        String title = "";
        List<CodeChallenge> codeChallengeList = CodeChallenge.findAll();
        List<CodeChallengeScore> codeChallengeScoresList = new ArrayList<CodeChallengeScore>(); 
        final Map<String, String[]> values = request().body().asFormUrlEncoded();   
        
        try {
            Long challengeID = Long.parseLong(values.get("challengeId")[0]); 
            codeChallengeScoresList = CodeChallengeScore.find.where().eq("challenge_id", challengeID).findList();
            title = CodeChallenge.getChallenge(challengeID).getTitle();
        } catch(NullPointerException npe) {
            //Do nothing.
        }
        
        return ok(views.html.codeChallengeResults.render(codeChallengeList, codeChallengeScoresList, title));
    }

    /****************************
     *                          *
     *  CODE REVIEW METHODS     *
     *                          *
     ****************************/
    
    /**
     * 
     * 
     * @return
     */
    public static Result codeReview() {
        
        return ok(views.html.codeReview.render());
    }
    
    /**
     * 
     * 
     * 
     * @return
     */
    @BodyParser.Of(BodyParser.MultipartFormData.class)
    public static Result uploadJavaFile() {
             
        Http.MultipartFormData body = request().body().asMultipartFormData();  
        Http.MultipartFormData.FilePart temp = body.getFile("reviewFile");

        String userCode = "";
        String readLine = "";

        File uploadedFile = temp.getFile();
        try {
            InputStream is = new FileInputStream(uploadedFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            
            while((readLine = br.readLine()) != null) {
                userCode += readLine + "\n";
            }
            
            is.close();
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //System.out.println(temp.getFilename());
        //System.out.println(temp.getContentType());
   
        final Map<String, String[]> values = body.asFormUrlEncoded();    
        String title = values.get("title")[0];
        String userComment = values.get("userComment")[0];
        User user = User.getUser(session("username"));

        CodeReview.create(title, userCode, userComment, user);

        return redirect(routes.Application.codeReview());
    }
}