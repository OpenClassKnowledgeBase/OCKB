import java.util.*;

import play.libs.*;
import models.*;
import play.Application;
import play.GlobalSettings;

import com.avaje.ebean.Ebean;


public class Global extends GlobalSettings {
	@SuppressWarnings("unchecked")
	@Override
	public void onStart(Application app) {
		
		Map<String,List<Object>> all = (Map<String,List<Object>>)Yaml.load("initial-data.yml");
		Ebean.save(all.get("comments"));
		
	}
}