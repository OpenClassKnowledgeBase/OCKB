package models;

import javax.persistence.*;
import play.db.ebean.*;
import com.avaje.ebean.*;

@Entity
public class User extends Model {

    
    public Long id;
    
    public String email;
   
    public String name;
   
    public String status;
    //@Required
    //public String type;
    public int posts;
    
    public User() {}
    public User(String email, String name, String status, int posts) {
      this.email = email + "@hawaii.edu";
      this.name = name;
      this.status = status;
      this.posts = posts;
    }
    
    public void addPost() { posts++; }
    
    public static Finder<Long,User> find = new Finder<Long,User>(
    		   Long.class, User.class
    );
    
//    public static List<User> all(){
//		return find.all();
//	  }
    
    
  /*  public static User authenticate(String email, String password) {
        return find.where().eq("email", email)
            .eq("password", password).findUnique();
    }*/
}