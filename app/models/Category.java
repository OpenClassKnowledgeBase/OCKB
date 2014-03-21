package models;

import java.util.*;

import javax.persistence.*;

import play.data.format.*;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Category extends Model {

	@Required
	public String id;

	@Required
	public String description;

	//help initiate queries
	public static Finder<String,Category> find = new Finder<String,Category>(String.class, Category.class);

}