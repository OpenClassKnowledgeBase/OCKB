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
    public String email;
    public String name;
    public String password;
    public String userType;
    public int posts;
    
    public User() {}
    public User(String email, String name, String password, String userType, int posts) {
      this.email = email;
      this.name = name;
      this.password = password;
      this.userType = userType;
      this.posts = posts;
    }
    
    public void addPost() { posts++; }
    
    public static Finder<String,User> find = new Finder<String,User>(
    		   String.class, User.class
    );
    
    public static List<User> all(){
		return User.find.all();
	}
    
    
    public static User authenticate(String email, String password) {
        return find.where().eq("email", email)
            .eq("password", password).findUnique();
    }
}