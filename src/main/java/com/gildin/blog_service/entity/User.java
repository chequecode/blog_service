package com.gildin.blog_service.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "user_likes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    private List<Post> likedPosts;

    @OneToMany(mappedBy = "authorUser")
    private List<Post> userPosts;

    @OneToMany(mappedBy = "authorUser")
    private List<Comment> userComments;

    @Column(nullable = false)
//    @NotBlank(message = "email is not valid")
    private String email;

    @Column(nullable = false)
//    @NotBlank(message = "username is not valid")
    private String username;

    @Column(nullable = false)
//    @NotBlank(message = "password is not valid")
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING) // шобы в БД были значения типа стринг, а не цифры 0-1
    private RoleType role;



    public User() {
    }

    public User(Long id, String username, String email, String password, List<Post> userPosts, List<Comment> userComments, List<Post> likedPosts, RoleType role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.userPosts = userPosts;
        this.userComments = userComments;
        this.likedPosts = likedPosts;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Post> getUserPosts() {
        return userPosts;
    }

    public void setUserPosts(List<Post> userPosts) {
        this.userPosts = userPosts;
    }

    public List<Comment> getUserComments() {
        return userComments;
    }

    public void setUserComments(List<Comment> userComments) {
        this.userComments = userComments;
    }

    public List<Post> getLikedPosts() {
        return likedPosts;
    }

    public void setLikedPosts(List<Post> likedPosts) {
        this.likedPosts = likedPosts;
    }
}
