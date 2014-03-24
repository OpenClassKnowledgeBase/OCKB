
import java.util.List;

import models.Category;
import models.Post;
import play.Application;
import play.GlobalSettings;
import play.libs.Yaml;

import com.avaje.ebean.Ebean;


public class Global extends GlobalSettings {
	@SuppressWarnings("unchecked")
	@Override
	public void onStart(Application app) {
		System.out.println(Post.find.findRowCount());
		//check if the database is empty
		if (Post.find.findRowCount() == 0) {
			Ebean.save((List<Post>) Yaml.load("initial-data.yml"));
		}
		if (Category.find.findRowCount() == 0) {
			Ebean.save((List<Post>) Yaml.load("categories.yml"));
		}
	}
}
