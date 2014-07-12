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
	
	public String requiredSource;
	
	public Integer codeChallengeTime;
		
	public Long categoryId;

	public CodeChallenge (String title, String description, String requiredOutput, Integer codeChallengeTime, Long categoryId) {
        this.title = title;
        this.description = description;
        this.requiredOutput = requiredOutput;
        this.codeChallengeTime = codeChallengeTime;
        this.categoryId = categoryId;
    }
	
	//help initiate queries
	public static Finder<Long,CodeChallenge> find = new Finder<Long,CodeChallenge>(Long.class, CodeChallenge.class);
	
	/*Implement the CRUD operations*/

	public static void create(String title, String description, String requiredOutput, Integer codeChallengeTime, Long categoryId) {
		CodeChallenge challenge = new CodeChallenge(title, description, requiredOutput, codeChallengeTime, categoryId);
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
		return this.title;
	}
	
	public String getCategoryTitle() {
	    return Category.getCategory(this.categoryId).getTitle();
	}
	
}