package com.gildin.blog_service.dto;

public class CommentDTO {
    private Long id;
    private Long authorUserId;
    private Long commentedPostId;
    private String contentText;

    public CommentDTO() {
    }

    public CommentDTO(Long id, Long authorUserId, Long commentedPostId, String contentText) {
        this.id = id;
        this.authorUserId = authorUserId;
        this.commentedPostId = commentedPostId;
        this.contentText = contentText;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAuthorUserId() {
        return authorUserId;
    }

    public void setAuthorUserId(Long authorUserId) {
        this.authorUserId = authorUserId;
    }

    public Long getCommentedPostId() {
        return commentedPostId;
    }

    public void setCommentedPostId(Long commentedPostId) {
        this.commentedPostId = commentedPostId;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }
}
