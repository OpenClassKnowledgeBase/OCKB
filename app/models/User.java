package models;

import java.util.*;

import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

import com.avaje.ebean.*;

@Entity
public class User extends Model {

    @Id
    public Long id;
    @Required
    public String email;
    @Required
    public String name;
    @Required
    public String status;
    //@Required
    //public String type;
    @Required
    public int posts;
    
    public User() {}
    public User(String email, String name, String status, int posts) {
      this.email = email + "@hawaii.edu";
      this.name = name;
      this.status = status;
      //this.type = type;
      this.posts = posts;
    }
    
    public void addPost() { posts++; }
    
    public static Finder<Long,User> find = new Finder<Long,User>(
    		   Long.class, User.class
    );
    
    public static List<User> all(){
		return find.all();
	}
    
    
    public static User authenticate(String email, String password) {
        return find.where().eq("email", email)
            .eq("password", password).findUnique();
    }
}