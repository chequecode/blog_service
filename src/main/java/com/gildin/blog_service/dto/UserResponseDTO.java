package com.gildin.blog_service.dto;

import com.gildin.blog_service.entity.RoleType;
import java.util.List;

// для вывода безопасных ответов
public class UserResponseDTO {
    private Long id;
    private String username;
    private String email;
    private RoleType role;
    private List<Long> likedPostsIds;
    private List<Long> userPostsIds;
    private List<Long> userCommentsIds;

    public UserResponseDTO(Long id, String username, String email, RoleType role,
                           List<Long> likedPostsIds, List<Long> userPostsIds, List<Long> userCommentsIds) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.likedPostsIds = likedPostsIds;
        this.userPostsIds = userPostsIds;
        this.userCommentsIds = userCommentsIds;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public RoleType getRole() { return role; }
    public List<Long> getLikedPostsIds() { return likedPostsIds; }
    public List<Long> getUserPostsIds() { return userPostsIds; }
    public List<Long> getUserCommentsIds() { return userCommentsIds; }
}
