package com.gildin.blog_service.dto.update;

import com.gildin.blog_service.enumTypes.RoleType;
import com.gildin.blog_service.enumTypes.TeamRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDTO {
    private String email;
    private String username;
    private String password;
    private RoleType role;
    private TeamRole teamRole;
}
