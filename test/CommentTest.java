import java.util.List;

import models.Comment;
import models.Post;
import models.User;

import org.junit.Test;

import play.test.UnitTest;


public class CommentTest extends UnitTest {

	@Test
	public void postComments() {
	    // Create a new user and save it
	    User bob = new User("bob@gmail.com", "secret", "Bob").save();

	    // Create a new post
	    Post bobPost = new Post(bob, "My first post", "Hello world").save();

	    // Post a first comment
	    new Comment(bobPost, "Jeff", "Nice post").save();
	    new Comment(bobPost, "Tom", "I knew that !").save();

	    // Retrieve all comments
	    List<Comment> bobPostComments = Comment.find("byPost", bobPost).fetch();

	    // Tests
	    assertEquals(2, bobPostComments.size());

	    Comment firstComment = bobPostComments.get(0);
	    assertNotNull(firstComment);
	    assertEquals("Jeff", firstComment.author);
	    assertEquals("Nice post", firstComment.content);
	    assertNotNull(firstComment.postedAt);

	    Comment secondComment = bobPostComments.get(1);
	    assertNotNull(secondComment);
	    assertEquals("Tom", secondComment.author);
	    assertEquals("I knew that !", secondComment.content);
	    assertNotNull(secondComment.postedAt);
	}

}
