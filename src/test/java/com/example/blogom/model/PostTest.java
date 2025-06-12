package com.example.blogom.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PostTest {

    @Test
    void testTitleContentSetterGetter() {
        Post post = new Post();
        post.setTitle("Teszt cím");
        post.setContent("Ez egy tartalom");

        assertEquals("Teszt cím", post.getTitle());
        assertEquals("Ez egy tartalom", post.getContent());
    }

    @Test
    void testAuthorSetterGetter() {
        User user = new User("Author");
        Post post = new Post();
        post.setAuthor(user);

        assertEquals(user, post.getAuthor());
    }

    @Test
    void testCreatedAtSetterGetter() {
        Post post = new Post();
        LocalDateTime now = LocalDateTime.now();
        post.setCreatedAt(now);

        assertEquals(now, post.getCreatedAt());
    }

    @Test
    void testAuthorNameTransientSetterGetter() {
        Post post = new Post();
        post.setAuthorName("Valaki");

        assertEquals("Valaki", post.getAuthorName());
    }
}
