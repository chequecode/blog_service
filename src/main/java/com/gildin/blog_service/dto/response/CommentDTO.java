package com.gildin.blog_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Long id;
    private ShortUserDTO authorUserShort;
    private ShortPostDTO commentedPostShort;
    private String contentText;
}
