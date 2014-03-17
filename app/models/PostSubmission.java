package models;

import java.util.*;
import play.db.ebean.*;
import play.data.format.*;
import javax.persistence.*; /*For the database*/
import play.data.validation.Constraints.*;

@SuppressWarnings("serial")
@Entity /*THIS IS IMPORTANT*/
public class PostSubmission extends Model{

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
	public static Finder<Long,PostSubmission> find = new Finder<Long,PostSubmission>(Long.class, PostSubmission.class);

	/*Implement the CRUD operations*/
	public static List<PostSubmission> all(){
		return find.all();
	}

	public static void create(PostSubmission Post){
		Post.save();
	}

	public static void delete(Long id){
		find.ref(id).delete();
	}
}