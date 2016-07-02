import java.util.List;

import models.Comment;
import models.Post;
import models.User;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;


public class PostTest extends UnitTest {
	 @Before
    public void setup() {
        Fixtures.deleteDatabase();
    }
	
	 @Test
    public void createPost() {
        // Create a new user and save it
        User bob = new User("bob@gmail.com", "secret", "Bob").save();

        // Create a new post
        new Post(bob, "My first post", "Hello world").save();

        // Test that the post has been created
        assertEquals(1, Post.count());

        // Retrieve all posts created by Bob
        List<Post> bobPosts = Post.find("byAuthor", bob).fetch();

        // Tests
        assertEquals(1, bobPosts.size());
        Post firstPost = bobPosts.get(0);
        assertNotNull(firstPost);
        assertEquals(bob, firstPost.author);
        assertEquals("My first post", firstPost.title);
        assertEquals("Hello world", firstPost.content);
        assertNotNull(firstPost.postedAt);
    }
	 
	 @Test
	 public void useTheCommentsRelation() {
	     // Create a new user and save it
	     User bob = new User("bob@gmail.com", "secret", "Bob").save();

	     // Create a new post
	     Post bobPost = new Post(bob, "My first post", "Hello world").save();

	     // Post a first comment
	     bobPost.addComment("Jeff", "Nice post");
	     bobPost.addComment("Tom", "I knew that !");

	     // Count things
	     assertEquals(1, User.count());
	     assertEquals(1, Post.count());
	     assertEquals(2, Comment.count());

	     // Retrieve Bob's post
	     bobPost = Post.find("byAuthor", bob).first();
	     assertNotNull(bobPost);

	     // Navigate to comments
	     assertEquals(2, bobPost.comments.size());
	     assertEquals("Jeff", bobPost.comments.get(0).author);

	     // Delete the post
	     bobPost.delete();

	     // Check that all comments have been deleted
	     assertEquals(1, User.count());
	     assertEquals(0, Post.count());
	     assertEquals(0, Comment.count());
	 }

}
