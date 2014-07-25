package models;

import java.util.*;

import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

import com.avaje.ebean.*;
import com.avaje.ebean.Query;

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
    
    @Required
    public String role;
    
    @Required
    public int posts;
    
    @ManyToOne
    @JoinColumn(name="course_id")
    public Course course;
    
    public User(String email, String name, String status, String role, int posts) {
      this.email = email + "@hawaii.edu";
      this.name = name;
      this.status = status;
      this.role = role;
      this.posts = posts;
    }
    
    public void addPost() { posts++; }
    
    public static Finder<Long,User> find = new Finder<Long,User>(Long.class, User.class);
    
    public static List<User> findAll(){
		return find.all();
	}
    
    public static User authenticate(String email, String password) {
        return find.where().eq("email", email)
            .eq("password", password).findUnique();
    }
    
    public static List<User> getAdminList() {
    	return find.where().like("role", "admin").findList();
    }
    
    public static User getUser(String username) {
        return find.where().eq("name", username).findUnique();
    }
    
    public static User findUser(Long uid) {
        return find.where().eq("id", uid).findUnique(); 
    }
    
}