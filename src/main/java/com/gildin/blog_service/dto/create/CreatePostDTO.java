package com.gildin.blog_service.dto.create;

import com.gildin.blog_service.enumTypes.PostType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostDTO {
    @NotBlank(message = "inner text required")
    @Size(min = 5, max = 20)
    private String contentText;
    @NotBlank(message = "title required")
    @Size(min = 10, max = 5000)
    private String title;

    private PostType postType;
}
