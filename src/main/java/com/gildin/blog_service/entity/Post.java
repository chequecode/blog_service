package com.gildin.blog_service.entity;

import com.gildin.blog_service.enumTypes.PostType;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "user_likes",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> likedUsers = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User authorUser;

    @Column(nullable = false)
    private String contentText;

    @OneToMany(mappedBy = "commentedPost")
    private List<Comment> comments = new ArrayList<>();

    private String title;

    private PostType postType;

    public Post() {}

    public Post(PostType postType, Long id, List<User> likedUsers, User authorUser, String contentText, List<Comment> comments, String title) {
        this.id = id;
        this.likedUsers = likedUsers;
        this.authorUser = authorUser;
        this.contentText = contentText;
        this.comments = comments;
        this.title = title;
        this.postType = postType;
    }

    public PostType getPostType() {
        return postType;
    }

    public void setPostType(PostType postType) {
        this.postType = postType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<User> getLikedUsers() {
        return likedUsers;
    }

    public void setLikedUsers(List<User> likedUsers) {
        this.likedUsers = likedUsers;
    }

    public User getAuthorUser() {
        return authorUser;
    }

    public void setAuthorUser(User authorUser) {
        this.authorUser = authorUser;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}

