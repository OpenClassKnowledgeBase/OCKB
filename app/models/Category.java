package models;

import java.util.*;

import play.db.ebean.*;

import javax.persistence.*; /*For the database*/

import play.data.validation.Constraints.*;

@SuppressWarnings("serial")
@Entity /*THIS IS IMPORTANT*/
public class Category extends Model{

	@Required
	public String title;

	@Required
	public String description;
	
	@Required
	public String url;
	
	@Required
	public Boolean requested;
	
	@Id 
	public Long id;
	
	public String user;
	
	public boolean hidden;

	public Category (String title, String description, String url, Boolean requested) {
        this.title = title;
        this.url = url;
        this.description = description;
        this.requested = requested;
    }
	
	//help initiate queries
	public static Finder<Long,Category> find = new Finder<Long,Category>(Long.class, Category.class);
	
	/*Implement the CRUD operations*/

	public static void create(String title, String description, String url, Boolean requested, String user){
		Category category = new Category(title, description, url, requested);
		category.user = user;
		category.save();
	}
	
	public static List<Category> findAll(){
		return find.all();
	}
	
	public static void delete(Long id){
		find.ref(id).delete();
	}
	
	public static Category getCategory (Long id) {
		return Category.find.byId(id);
	}
	
	public String getTitle() {
		return title;
	}
	
}