package models;

import java.util.*;

import javax.persistence.*;

import play.data.format.*;
import play.data.validation.*;
import play.db.jpa.*;

/**
 * Category entity managed by JPA
 */
@Entity
public class Category {

	@Id
	public Long id;

	@Constraints.Required
	@ManyToOne(cascade = CascadeType.MERGE)
	public Long post_id;

	/**
	 * Find a Category by id.
	 */
	public static Category findById(Long id) {
		return JPA.em().find(Category.class, id);
	}

}