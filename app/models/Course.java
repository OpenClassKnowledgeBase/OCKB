package models;

import java.util.*;

import play.db.ebean.*;

import javax.persistence.*; /*For the database*/

import play.data.validation.Constraints.*;

@SuppressWarnings("serial")
@Entity /*THIS IS IMPORTANT*/
public class Course extends Model{

    @Id 
    public Long id;
    
	@Required
	public String title; //e.g. Introduction to Comp Sci I

	@Required
	public String description; //Nothing yet
	
	@Required
	public String categoryOrder; //Alphabetically or CurrentSortOrder
	
	public String currentSortOrder; //Contains the string that will form the categories list
			    
    public String studentRoster; //Contains the string that will form the student roster
    
    public Integer courseSection; //Course section 001, 002, etc.
    
    public String semester; //Fall 2014, etc.
    
    public String icsCourse; //ICS111, ICS211, etc.
	
	public Course (String title, String description, String categoryOrder, Integer courseSection, String semester, String icsCourse) {
        this.title = title;
        this.description = description;
        this.categoryOrder = categoryOrder;
        this.courseSection = courseSection;
        this.semester = semester;
        this.icsCourse = icsCourse;
    }
	
	//help initiate queries
	public static Finder<Long,Course> find = new Finder<Long,Course>(Long.class, Course.class);
	
	/*Implement the CRUD operations*/

	public static void create(String title, String description, String categoryOrder, Integer courseSection, String semester, String icsCourse) {
		Course course = new Course(title, description, categoryOrder, courseSection, semester, icsCourse);
		course.save();
	}
	
	public static List<Course> findAll(){
		return find.all();
	}
	
	public static void delete(Long id){
		find.ref(id).delete();
	}
	
	public static Course getCourse (Long id) {
		return Course.find.byId(id);
	}
	
	public String getTitle() {
		return title;
	}
	
}