package com.gildin.blog_service.dto.response;

import com.gildin.blog_service.enumTypes.RoleType;
import com.gildin.blog_service.enumTypes.TeamRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @Builder.Default
    private List<ShortPostDTO> likedPostsShort = new ArrayList<>();
    @Builder.Default
    private List<ShortPostDTO> userPostsShort = new ArrayList<>();
    @Builder.Default
    private List<ShortCommentDTO> userCommentsShort = new ArrayList<>();

    private Long id;
    private String email;
    private String username;
    private String password;
    private RoleType role;
    private TeamRole teamRole;
}
