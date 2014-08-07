package models;

import java.util.*;
import play.db.ebean.*;
import javax.persistence.*;
import play.data.format.Formats;
import play.data.validation.Constraints.*;

@SuppressWarnings("serial")
@Entity /*THIS IS IMPORTANT*/
public class CodeReview extends Model{
    
    @Id 
    public Long id;
 
    @ManyToOne
    @JoinColumn(name="user_id")
    @Required 
    public User user;
    
    @Required
    public String title;

    @Required
    public String userCode;
    
    public String userComment;
    
    @Formats.DateTime(pattern="yyyy-MM-dd'T'hh:mm:ss-22:00")
    public Date datePosted = new Date();

    public CodeReview(String title, String userCode, String userComment, User user) {
        super();
        this.title = title;
        this.userCode = userCode;
        this.userComment = userComment;
        this.user = user;
    }

    public static void create(String title, String userCode, String userComment, User user) {
        CodeReview cr = new CodeReview(title, userCode, userComment, user);
        cr.save();
    }
    
    public static Finder<Long, CodeReview> find = new Finder<Long, CodeReview>(Long.class, CodeReview.class);
    
    public static List<CodeReview> findAll(){
        return find.all();
    }
    
    public static void delete(Long id){
        find.ref(id).delete();
    }
    
    public static CodeReview getCodeReview(Long id) {
        return CodeReview.find.byId(id);
    }

    /**
     * @return the userCode
     */
    public String getUserCode() {
        return userCode;
    }

    /**
     * @return the userComment
     */
    public String getUserComment() {
        return userComment;
    }

    /**
     * @return the datePosted
     */
    public Date getDatePosted() {
        return datePosted;
    }
    
}