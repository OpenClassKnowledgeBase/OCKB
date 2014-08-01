package models;

import java.util.*;

import javax.persistence.*;

import com.avaje.ebean.Page;

import play.data.format.*;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity 
public class CodeChallengeScore extends Model {

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
	
	public CodeChallengeScore(CodeChallenge challenge, User user, Long score) {
        this.challenge = challenge;
        this.user = user;
        this.score = score;
    }
	
	//help initiate queries
	public static Finder<Long,CodeChallengeScore> find = new Finder<Long,CodeChallengeScore>(Long.class, CodeChallengeScore.class);
	
	/*Implement the CRUD operations*/
	public static List<CodeChallengeScore> all(){
		return find.all();
	}
	
	public static List<CodeChallengeScore> getScoresForUser (Long id) {
        return find.where().eq("user_id", id).findList();
    }
	
	public static CodeChallengeScore getScore(Long chid, Long uid) {
	    return find.where().eq("user_id", uid).eq("challenge_id", chid).findUnique();
	}
	
	public static void create(CodeChallenge challenge, User user, Long score){
		CodeChallengeScore scores = new CodeChallengeScore(challenge, user, score);
		scores.save();
	}
	
	public static void delete(Long id){
		find.ref(id).delete();
	}
	
	public void setScore(Long newScore) {
	    score = newScore;
	}

}