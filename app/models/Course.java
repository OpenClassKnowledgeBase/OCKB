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
	public String title;

	@Required
	public String description;
	
	@Required
	public String categoryOrder;
	
	public String currentSortOrder;
		
	public Integer codeChallengeTime;
	
    public ArrayList<ArrayList<String>> rowRoster;
	
	public Course (String title, String description, String categoryOrder, ArrayList<ArrayList<String>> roster) {
        this.title = title;
        this.description = description;
        this.categoryOrder = categoryOrder;
        this.rowRoster = roster;
    }
	
	//help initiate queries
	public static Finder<Long,Course> find = new Finder<Long,Course>(Long.class, Course.class);
	
	/*Implement the CRUD operations*/

	public static void create(String title, String description, String categoryOrder){
		Course course = new Course(title, description, categoryOrder, null);
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