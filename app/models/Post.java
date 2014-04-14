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
	
	public Boolean isSticky;

	@ManyToOne
	@JoinColumn(name="category_id")
	public Category category;

	@Required
	public String title;

	@Required
	@Column(columnDefinition = "TEXT")
	public String content;

	@Formats.DateTime(pattern="yyyy-MM-dd hh:mm:ss")
	public Date datePosted = new Date();
	
	@Formats.DateTime(pattern="yyyy-MM-dd hh:mm:ss")
	public Date latestActivity;
	
	public Long comments;
	
	public Long votes;

	@Id 
	public Long id;


	//help initiate queries
	public static Finder<Long,Post> find = new Finder<Long,Post>(Long.class, Post.class);
	
	public Post (Category category, String title, String content) {
		this.category = category;
		this.title = title;
		this.content = content;
	}
	/*Implement the CRUD operations*/
	public static List<Post> all(){
		return find.all();
	}

	public static void create(Post post, Category category, String title, String content){
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