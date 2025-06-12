package com.example.blogom.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    // Alapértelmezett konstruktor
    public User() {}

    // Paraméteres konstruktor
    public User(String username) {
        this.username = username;
    }

    // GETTEREK

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public List<Post> getPosts() {
        return posts;
    }

    // SETTEREK

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    // ➕ Kiegészítés: Post hozzáadása
    public void addPost(Post post) {
        posts.add(post);
        post.setAuthor(this);
    }

    // ➖ Kiegészítés: Post eltávolítása
    public void removePost(Post post) {
        posts.remove(post);
        post.setAuthor(null);
    }
}
