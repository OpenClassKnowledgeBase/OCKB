package models;

import java.util.*;

import javax.persistence.*;

import play.data.format.*;
import play.data.validation.*;
import play.db.jpa.*;

/**
 * Computer entity managed by JPA
 */
@Entity 
@SequenceGenerator(name = "comment_seq", sequenceName = "comment_seq")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_seq")
    public Long id;
    
    @Constraints.Required
    @ManyToOne(cascade = CascadeType.MERGE)
    public Long post_id;
    
    @Constraints.Required
    public String content;
    
    /**
     * Find a category by id.
     */
    public static Comment findById(Long id) {
        return JPA.em().find(Comment.class, id);
    }
    
    
    public static List<Comment> findAll() {
    	return JPA.em().createQuery("Select * from Comment").getResultList();
    }
}