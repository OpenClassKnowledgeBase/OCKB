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
	
	@Id 
	public Long id;

	//help initiate queries
	public static Finder<Long,Category> find = new Finder<Long,Category>(Long.class, Category.class);
	
	public Category (String title, String description, String url) {
		this.title = title;
		this.url = url;
		this.description = description;
	}
	/*Implement the CRUD operations*/

	public static void create(Category category, String title, String description, String url){
		category.title = title;
		category.description = description;
		category.url = url;
		category.save();
	}
	
	public static List<Category> findAll(){
		return find.all();
	}
	public static void delete(Long id){
		find.ref(id).delete();
	}
}