import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.avaje.ebean.Page;
import com.fasterxml.jackson.databind.JsonNode;

import models.Category;
import models.Comment;
import models.Post;
import models.User;

import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.api.templates.Html;
import play.data.DynamicForm;
import play.data.validation.ValidationError;
import play.data.validation.Constraints.RequiredValidator;
import play.i18n.Lang;
import play.libs.F;
import play.libs.F.*;
import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;


/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class ApplicationTest {

    public static FakeApplication app;
    private final Http.Request request = mock(Http.Request.class);

    @BeforeClass
    public static void startApp() {
        app = Helpers.fakeApplication();
        Helpers.start(app);
    }
    
    @Before
    public void setUp() throws Exception {
        Map<String, String> flashData = Collections.emptyMap();
        Map<String, Object> argData = Collections.emptyMap();
        Long id = 2L;
        play.api.mvc.RequestHeader header = mock(play.api.mvc.RequestHeader.class);
        Http.Context context = new Http.Context(id, header, request, flashData, flashData, argData);
        Http.Context.current.set(context);
    }
    
    @Test
    public void simpleCheck() {
        int a = 1 + 1;
        assertThat(a).isEqualTo(2);
    }
    
    @Test
    public void renderIndexTemplate() {
        Content html = views.html.index.render();
        assertThat(contentType(html)).isEqualTo("text/html");
        assertThat(contentAsString(html)).contains("Open Class Knowledge Base");
    }
    
    @Test
    public void renderDashboardTemplates() {
        Content dash = views.html.dashboard.render(new ArrayList(), new ArrayList(), "admin");
        assertThat(contentType(dash)).isEqualTo("text/html");
        assertThat(contentAsString(dash)).contains("My Recent Activity");
        assertThat(contentAsString(dash)).contains("Account Settings");
        
        Content req = views.html.requestCategory.render();
        assertThat(contentType(req)).isEqualTo("text/html");
        assertThat(contentAsString(req)).contains("Requested Category");
        
        Content view = views.html.viewRequestedCategories.render(new ArrayList(), "admin");
        assertThat(contentType(view)).isEqualTo("text/html");
        assertThat(contentAsString(view)).contains("Requested Categories");
        
        Content priv = views.html.userPriv.render(new ArrayList());
        assertThat(contentType(priv)).isEqualTo("text/html");
        assertThat(contentAsString(priv)).contains("Role");
        assertThat(contentAsString(priv)).contains("Name");
    }
    
    @Test
    public void renderCategoriesTemplate() {
        Content html = views.html.categories.render(new ArrayList());
        assertThat(contentType(html)).isEqualTo("text/html");
        assertThat(contentAsString(html)).contains("Pick a Category to Explore");
    }
    
    @Test
    public void renderCategoryTemplate() {
        Page<Post> currentPage = Post.getPosts((long) 1, 0, 10, "datePosted", "desc", "");
        Category currentCategory = Category.getCategory((long) 1);
        
        Content html = views.html.category.render(new ArrayList(), currentPage, "datePosted", "desc", "", currentCategory, "admin");
        assertThat(contentType(html)).isEqualTo("text/html");
        assertThat(contentAsString(html)).contains("Submit Question for " + currentCategory.title);
        assertThat(contentAsString(html)).contains(currentCategory.description);
    }
    
    @Test
    public void renderPostTemplates() {
        Category testcat = new Category("cat-test-title", "test-desc", "test-url", false);
        testcat.user = "admin";
        testcat.id = (long) -1;
        Post testpost = new Post(testcat, "test-title", "test-content");
        testpost.userName = "admin";
        testpost.comments = (long) 0;
        testpost.votes = (long) 0;
        testpost.isSticky = false;
        testpost.latestActivity = new Date();
        testpost.datePosted = new Date();
        testpost.id = (long) -1;
        
        Content posthtml = views.html.post.render(new ArrayList(), testpost, "admin", "admin");
        assertThat(contentType(posthtml)).isEqualTo("text/html");
        assertThat(contentAsString(posthtml)).contains(testpost.content);
        assertThat(contentAsString(posthtml)).contains("Post a Reply");
        assertThat(contentAsString(posthtml)).contains("Submit Comment");
        
        Content sub = views.html.submitPost.render(testcat, "admin");
        assertThat(contentType(sub)).isEqualTo("text/html");
        assertThat(contentAsString(sub)).contains("Sumbit Question for " + testcat.title);
    }
    
    @Test
    public void sessionTest() {
       running(fakeApplication(), new Runnable() {
           public void run() {
               String username = "admin";
               Result res = route(fakeRequest("GET", "/index")
                                .withSession("username", username));
               assert(contentAsString(res).contains(username));           
           }
       });
    }

    @AfterClass
    public static void stopApp() {
        Helpers.stop(app);
    }

}
