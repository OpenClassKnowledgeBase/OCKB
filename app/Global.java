
import java.util.List;

import models.*;
import play.Application;
import play.GlobalSettings;
import play.libs.Yaml;

import com.avaje.ebean.Ebean;


public class Global extends GlobalSettings {
	@SuppressWarnings("unchecked")
	@Override
	public void onStart(Application app) {

		
		//check if the database is empty
		if (Post.find.findRowCount() == 0) {
			Ebean.save((List<Post>) Yaml.load("post-data.yml"));
		}
		if (Category.find.findRowCount() == 0) {
			Ebean.save((List<Category>) Yaml.load("category-data.yml"));
		}
		
		if (Comment.find.findRowCount() == 0) {
			Ebean.save((List<Comment>) Yaml.load("comment-data.yml"));
		}

		if (User.find.findRowCount() == 0) {
            Ebean.save((List<User>) Yaml.load("user-data.yml"));
        }
	}
}
