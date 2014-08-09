package models;

import java.util.*;

import javax.persistence.*;

import play.data.format.*;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@SuppressWarnings("serial")
@Entity /*THIS IS IMPORTANT*/
public class CodeReviewFeedback extends Model {

    @Required
    public Long id;
    
    @ManyToOne
    @JoinColumn(name= "user_id")
    @Required 
    public User user;
    
    @ManyToOne
    @JoinColumn(name = "codeReview_id")
    public CodeReview codeReview;
    
    @Required
    public String userFeedback;
    
    @Formats.DateTime(pattern="yyyy-MM-dd'T'hh:mm:ss-22:00")
    public Date datePosted = new Date();
    
    public CodeReviewFeedback(User user, CodeReview codeReview, String userFeedback) {
        super();
        this.user = user;
        this.codeReview = codeReview;
        this.userFeedback = userFeedback;
    }

    public static void create(User user, CodeReview codeReview, String userFeedback) {
        CodeReviewFeedback crf = new CodeReviewFeedback(user, codeReview, userFeedback);
        crf.save();
    }

    public static Finder<Long, CodeReviewFeedback> find = new Finder<Long, CodeReviewFeedback>(Long.class, CodeReviewFeedback.class);
    
    public static List<CodeReviewFeedback> findAll(){
        return find.all();
    }

    public static void delete(Long id){
        find.ref(id).delete();
    }
    
    public static CodeReviewFeedback getCodeReviewFeedback(Long id) {
        return CodeReviewFeedback.find.byId(id);
    }

    /**
     * @return the codeReview
     */
    public CodeReview getCodeReview() {
        return codeReview;
    }

    /**
     * @return the userFeedback
     */
    public String getUserFeedback() {
        return userFeedback;
    }

    /**
     * @return the datePosted
     */
    public Date getDatePosted() {
        return datePosted;
    }
    
}