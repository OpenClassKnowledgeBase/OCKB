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
	public String content;
	
	@ManyToOne
	@JoinColumn(name="user_name")
	@Required 
	public String author;
	
	@Required
	@Formats.DateTime(pattern="yyyy-MM-dd")
	public Date submission_date;
	
	//help initiate queries
	public static Finder<Long,Comment> find = new Finder<Long,Comment>(Long.class, Comment.class);
	
	/*Implement the CRUD operations*/
	public static List<Comment> all(){
		return find.all();
	}
	
	public static void create(Comment Post){
		Post.save();
	}
	
	public static void delete(Long id){
		find.ref(id).delete();
	}

}