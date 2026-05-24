package com.gildin.blog_service.dto.create;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CreateCommentDTO {
    @NotBlank
    @Size(min = 5, max = 100)
    private String contentText;
}
