package com.gildin.blog_service.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "authorUser_id")
    private User authorUser;

    @ManyToOne
    @JoinColumn(name = "commentedPost_id")
    private Post commentedPost;

    @Column(nullable = false)
    private String contentText;


    public Comment() {}

    public Comment(Long id, User authorUser, String contentText, Post commentedPost) {
        this.id = id;
        this.authorUser = authorUser;
        this.contentText = contentText;
        this.commentedPost = commentedPost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Post getCommentedPost() {
        return commentedPost;
    }

    public void setCommentedPost(Post commentedPost) {
        this.commentedPost = commentedPost;
    }
}
