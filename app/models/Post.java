package models;

import java.util.*;

import play.db.ebean.*;
import play.data.format.*;

import javax.persistence.*; /*For the database*/

import com.avaje.ebean.Page;

import play.data.validation.Constraints.*;

@SuppressWarnings("serial")
@Entity /*THIS IS IMPORTANT*/
public class Post extends Model implements Comparable<Post>{

	public String userName;
	
	public Boolean isSticky;

	@ManyToOne
	@JoinColumn(name="category_id")
	public Category category;

	@OneToMany(cascade=CascadeType.ALL, mappedBy="parent_post")
    public List<Comment> commentList;
	
	@Required
	public String title;

	@Required
	@Column(columnDefinition = "TEXT")
	public String content;

	@Formats.DateTime(pattern="yyyy-MM-dd hh:mm:ss")
	public Date datePosted = new Date();
	
	@Formats.DateTime(pattern="yyyy-MM-dd hh:mm:ss")
	public Date latestActivity = new Date();
	
	public Long comments;
	
	public Long votes;
	
	public String usersVoted;

	@Id 
	public Long id;

	public Post (Category category, String title, String content) {
        this.category = category;
        this.title = title;
        this.content = content;
        this.usersVoted = "";
    }

	//help initiate queries
	public static Finder<Long,Post> find = new Finder<Long,Post>(Long.class, Post.class);
	
	/*Implement the CRUD operations*/
	public static List<Post> all(){
		return find.all();
	}
	
	public static void create(Category category, String title, String content, String username, Boolean isSticky, String usersVoted){
		Post post = new Post(category, title, content);
		post.userName = username;
		post.comments = (long) 0;
		post.votes = (long) 0;
		post.isSticky = isSticky;
		post.usersVoted = usersVoted;
		post.save();
	}
	
	public static List<Post> findAll(){
		return find.all();
	}
	public static void delete(Long id){
		find.ref(id).delete();
	}
	
	public static Post getPost (Long id) {
		return Post.find.byId(id);
	}
    
    //use to compare commentLists
    @Override
    public int compareTo(Post post) {
        //needs to be reversed to be in descending order
        if (this.commentList.size() < post.commentList.size()) {
            return 1;
        }
        if (this.commentList.size() > post.commentList.size()) {
            return -1;
        }
        else {
            return 0; 
        }
    }
    
    public static List<Post> getSortedByComments(){
        List<Post> allComments = Post.all();
        Collections.sort(allComments);
        return allComments;
    }
    
    /**
    * Return a page of posts
    *
    * @param page Page to display
    * @param pageSize Number of posts per page
    * @param sortBy Posts property used for sorting
    * @param order Sort order (either or asc or desc)
    * @param filter Filter applied on the name column
    */
    public static Page<Post> getPosts(Long cid, int page, int pageSize, String sortBy, String order, String filter) {
        return
            find.where()
                .eq("category_id", cid)
                .eq("isSticky", false)
                .ilike("title", "%" + filter + "%")
                .ilike("content", "%" + filter + "%")
                .orderBy(sortBy + " " + order)
                .findPagingList(pageSize)
                .getPage(page);
    }
    
    /**
    <script>
        function vote(id, voteAmount) {
            $("#"+id).text( parseInt($("#"+id).text().trim()) + voteAmount);
            $("#upvote"+id).addClass("btn-disabled");
            $("#downvote"+id).addClass("btn-disabled");
            if (voteAmount > 0) {
                $("#glyphup"+id).addClass("voted-color");
            } else {
                $("#glyphdown"+id).addClass("voted-color");
            }
            $("#"+id).addClass("voted-color");
        }
    </script>
     */
    // var listAction = #{jsAction @Post.upvote(currentPost.id, -1) /}

    
    
}