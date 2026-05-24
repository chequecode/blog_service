package com.gildin.blog_service.dto.create;

import com.gildin.blog_service.enumTypes.RoleType;
import com.gildin.blog_service.enumTypes.TeamRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {
    @NotBlank
    @Email(message = "email should be valid")
    private String email;
    @NotBlank(message = "username required")
    @Size(min = 5, max = 10)
    private String username;
    @NotBlank
    @Min(4)
    private String password;
    @Builder.Default
    private RoleType role = RoleType.USER;
    @Builder.Default
    private TeamRole teamRole = TeamRole.UNKNOWN;
}
