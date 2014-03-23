package models;

import java.util.*;
import play.db.ebean.*;
import play.data.format.*;
import javax.persistence.*; /*For the database*/
import play.data.validation.Constraints.*;

@SuppressWarnings("serial")
@Entity /*THIS IS IMPORTANT*/
public class Post extends Model{

	public String userName;

	@Required
	public String category;

	@Required
	public String title;

	@Required
	public String content;

	@Formats.DateTime(pattern="MM/dd/yyyy")
	public Date datePosted = new Date();

	@Id 
	public Long id;

	//help initiate queries
	public static Finder<Long,Post> find = new Finder<Long,Post>(Long.class, Post.class);
	
	public Post (String category, String title, String content) {
		this.category = category;
		this.title = title;
		this.content = content;
	}
	/*Implement the CRUD operations*/
	public static List<Post> all(){
		return find.all();
	}

	public static void create(Post post, String category, String title, String content){
		post.category = category;
		post.title = title;
		post.content = content;
		post.save();
	}
	
	public static List<Post> findAll(){
		return find.all();
	}
	public static void delete(Long id){
		find.ref(id).delete();
	}
}