package models;

import java.util.*;

import javax.persistence.*;

import com.avaje.ebean.Page;

import play.data.format.*;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity 
public class Comment extends Model {

	@Required
	public Long id;

	@Required
	@Column(columnDefinition = "TEXT")
	public String content;
	
	@ManyToOne
	@JoinColumn(name="user_name")
	@Required 
	public String author;
	
	@Formats.DateTime(pattern="yyyy-MM-dd hh:mm:ss")
	public Date submission_date = new Date();
	
	@ManyToOne
	@JoinColumn(name = "parent_post_id")
	public Post parent_post;
	
	public Comment(Post parentPost, String content, String author) {
        this.parent_post = parentPost;
        this.content = content;
        this.author = author;
        parentPost.comments++;
    }
	
	//help initiate queries
	public static Finder<Long,Comment> find = new Finder<Long,Comment>(Long.class, Comment.class);
	
	/*Implement the CRUD operations*/
	public static List<Comment> all(){
		return find.all();
	}
	
	public static void create(Post parentPost, String author, String content){
		Comment comment = new Comment(parentPost, content, author);
		parentPost.comments++;
		comment.save();
	}
	
	public static void delete(Long id){
		find.ref(id).delete();
	}

}