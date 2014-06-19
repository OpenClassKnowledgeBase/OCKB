package models;

import java.util.*;

import play.db.ebean.*;

import javax.persistence.*; /*For the database*/

import play.data.validation.Constraints.*;

@SuppressWarnings("serial")
@Entity /*THIS IS IMPORTANT*/
public class CodeChallenge extends Model{

    @Id 
    public Long id;
    
	@Required
	public String title;

	@Required
	public String description;
	
	@Required
	public String requiredOutput;

	public CodeChallenge (String title, String description, String requiredOutput) {
        this.title = title;
        this.description = description;
        this.requiredOutput = requiredOutput;
    }
	
	//help initiate queries
	public static Finder<Long,CodeChallenge> find = new Finder<Long,CodeChallenge>(Long.class, CodeChallenge.class);
	
	/*Implement the CRUD operations*/

	public static void create(String title, String description, String requiredOutput){
		CodeChallenge challenge = new CodeChallenge(title, description, requiredOutput);
		challenge.save();
	}
	
	public static List<CodeChallenge> findAll(){
		return find.all();
	}
	
	public static void delete(Long id){
		find.ref(id).delete();
	}
	
	public static CodeChallenge getChallenge (Long id) {
		return CodeChallenge.find.byId(id);
	}
	
	public String getTitle() {
		return title;
	}
	
}