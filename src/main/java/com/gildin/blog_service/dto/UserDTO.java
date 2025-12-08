package com.gildin.blog_service.dto;

import com.gildin.blog_service.entity.RoleType;

import java.util.List;

public class UserDTO {

    private Long id;
    private List<Long> likedPostsIds;
    private List<Long> userPostsIds;
    private List<Long> userCommentsIds;
    private String email;
    private String username;
    private String password;
    private RoleType role;

    public UserDTO() {
    }

    public UserDTO(Long id, List<Long> likedPostsIds, List<Long> userPostsIds, List<Long> userCommentsIds, String email, String username, String password, RoleType role) {
        this.id = id;
        this.likedPostsIds = likedPostsIds;
        this.userPostsIds = userPostsIds;
        this.userCommentsIds = userCommentsIds;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getLikedPostsIds() {
        return likedPostsIds;
    }

    public void setLikedPostsIds(List<Long> likedPostsIds) {
        this.likedPostsIds = likedPostsIds;
    }

    public List<Long> getUserPostsIds() {
        return userPostsIds;
    }

    public void setUserPostsIds(List<Long> userPostsIds) {
        this.userPostsIds = userPostsIds;
    }

    public List<Long> getUserCommentsIds() {
        return userCommentsIds;
    }

    public void setUserCommentsIds(List<Long> userCommentsIds) {
        this.userCommentsIds = userCommentsIds;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }
}
