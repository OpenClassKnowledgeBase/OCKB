package controllers;
import java.util.List;

import play.libs.*;
import models.Post;
import play.Application;
import play.GlobalSettings;

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
	}
}
