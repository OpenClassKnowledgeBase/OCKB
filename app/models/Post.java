package models;

import java.util.*;
import play.db.ebean.*;
import play.data.format.*;
import javax.persistence.*; /*For the database*/
import play.data.validation.Constraints.*;

@Entity /*THIS IS IMPORTANT*/
public class Post extends Model{
	@Required
	public String UserName;

	@Required
	public String Category;

	@Required
	public String title;

	@Required
	public String content;

/*	@Formats.DateTime(pattern="MM/dd/yyyy")
	public Date datePosted = new Date();
*/
	@Id 
	public Long id;

	//help initiate queries
	public static Finder<Long,Post> find = new Finder(Long.class, Post.class);

	/*Implement the CRUD operations*/
	public static List<Post> all(){
		return find.all();
	}

	public static void create(Post Post){
		Post.save();
	}

	public static void delete(Long id){
		find.ref(id).delete();
	}
}