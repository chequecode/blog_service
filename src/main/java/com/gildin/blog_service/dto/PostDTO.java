package com.gildin.blog_service.dto;

import java.util.List;

public class PostDTO {

    private Long id;
    private List<Long> likedUsersIds;
    private List<Long> commentsIds;
    private Long authorUser;
    private String contentText;
    private String title;

    public PostDTO() {
    }

    public PostDTO(Long id, List<Long> likedUsersIds, List<Long> commentsIds, Long authorUser, String contentText, String title) {
        this.id = id;
        this.likedUsersIds = likedUsersIds;
        this.commentsIds = commentsIds;
        this.authorUser = authorUser;
        this.contentText = contentText;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getLikedUsersIds() {
        return likedUsersIds;
    }

    public void setLikedUsersIds(List<Long> likedUsersIds) {
        this.likedUsersIds = likedUsersIds;
    }

    public List<Long> getcommentsIds() {
        return commentsIds;
    }

    public void setcommentsIds(List<Long> commentsIds) {
        this.commentsIds = commentsIds;
    }

    public Long getAuthorUser() {
        return authorUser;
    }

    public void setAuthorUser(Long authorUser) {
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
}
