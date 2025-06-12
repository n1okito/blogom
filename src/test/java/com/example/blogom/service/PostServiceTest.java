package com.example.blogom.service;

import com.example.blogom.model.Post;
import com.example.blogom.model.User;
import com.example.blogom.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PostServiceTest {

    private PostRepository postRepository;
    private PostService postService;

    @BeforeEach
    void setUp() {
        postRepository = mock(PostRepository.class);
        postService = new PostService(postRepository);
    }

    @Test
    void testFindAll() {
        when(postRepository.findAll()).thenReturn(Arrays.asList(new Post(), new Post()));

        List<Post> result = postService.findAll();

        assertEquals(2, result.size());
        verify(postRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        Post post = new Post("Cím", "Tartalom", new User("Teszt"));
        post.setId(1L);
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        Optional<Post> result = postService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Cím", result.get().getTitle());
    }

    @Test
    void testSaveSetsCreatedAtAndSaves() {
        Post post = new Post("Teszt", "Tartalom", new User("Szerző"));

        when(postRepository.save(any(Post.class))).thenAnswer(i -> i.getArgument(0));

        Post savedPost = postService.save(post);

        assertNotNull(savedPost.getCreatedAt());
        verify(postRepository).save(post);
    }

    @Test
    void testDeleteById() {
        postService.deleteById(42L);

        verify(postRepository, times(1)).deleteById(42L);
    }
}
