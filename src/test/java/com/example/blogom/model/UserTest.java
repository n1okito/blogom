package com.example.blogom.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testUsernameGetterSetter() {
        User user = new User();
        user.setUsername("tesztuser");
        assertEquals("tesztuser", user.getUsername());
    }

    @Test
    void testPostListGetterSetter() {
        User user = new User("Author");
        Post post = new Post();
        List<Post> posts = new ArrayList<>();
        posts.add(post);

        user.setPosts(posts);

        assertEquals(1, user.getPosts().size());
        assertEquals(post, user.getPosts().get(0));
    }

    @Test
    void testAddPost() {
        User user = new User("Author");
        Post post = new Post();

        user.addPost(post);

        assertEquals(1, user.getPosts().size());
        assertEquals(user, post.getAuthor());
    }

    @Test
    void testRemovePost() {
        User user = new User("Author");
        Post post = new Post();
        user.addPost(post);

        user.removePost(post);

        assertEquals(0, user.getPosts().size());
        assertNull(post.getAuthor());
    }
}
