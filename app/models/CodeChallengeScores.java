package models;

import java.util.*;

import javax.persistence.*;

import com.avaje.ebean.Page;

import play.data.format.*;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity 
public class CodeChallengeScores extends Model {

	@Required
	public Long id;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	@Required 
	public User user;
	
	@Formats.DateTime(pattern="yyyy-MM-dd hh:mm:ss")
	public Date submission_date = new Date();
	
	@ManyToOne
	@JoinColumn(name = "challenge_id")
	public CodeChallenge challenge;
	
	@Required
	public Long score;
	
	public CodeChallengeScores(CodeChallenge challenge, User user, Long score) {
        this.challenge = challenge;
        this.user = user;
        this.score = score;
    }
	
	//help initiate queries
	public static Finder<Long,CodeChallengeScores> find = new Finder<Long,CodeChallengeScores>(Long.class, CodeChallengeScores.class);
	
	/*Implement the CRUD operations*/
	public static List<CodeChallengeScores> all(){
		return find.all();
	}
	
	public static List<CodeChallengeScores> getScoresForUser (Long id) {
        return find.where().eq("user_id", id).findList();
    }
	
	public static void create(CodeChallenge challenge, User user, Long score){
		CodeChallengeScores scores = new CodeChallengeScores(challenge, user, score);
		scores.save();
	}
	
	public static void delete(Long id){
		find.ref(id).delete();
	}
	
	public void setScore(Long newScore) {
	    score = newScore;
	}

}