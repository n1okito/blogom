package com.example.blogom.controller;

import com.example.blogom.model.Post;
import com.example.blogom.model.User;
import com.example.blogom.repository.UserRepository;
import com.example.blogom.service.PostService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(PostController.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void testListPosts() throws Exception {
        when(postService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(view().name("posts"));
    }

    @Test
    void testNewPostForm() throws Exception {
        mockMvc.perform(get("/posts/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("post_form"));
    }

    @Test
    void testSavePost() throws Exception {
        when(userRepository.findByUsername("Zsolti"))
                .thenReturn(Optional.of(new User("Zsolti")));

        mockMvc.perform(post("/posts")
                        .param("title", "Test Title")
                        .param("content", "Test Content")
                        .param("authorName", "Zsolti"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts"));
    }

    @Test
    void testEditPostFormWhenPostExists() throws Exception {
        Post post = new Post("Test Title", "Test Content", new User("Zsolti"));
        post.setId(1L);
        when(postService.findById(1L)).thenReturn(Optional.of(post));

        mockMvc.perform(get("/posts/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("post_form"))
                .andExpect(model().attributeExists("post"));
    }

    @Test
    void testEditPostFormWhenPostNotFound() throws Exception {
        when(postService.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/posts/edit/99"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts"));
    }

    @Test
    void testDeletePost() throws Exception {
        mockMvc.perform(get("/posts/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts"));
    }
}
