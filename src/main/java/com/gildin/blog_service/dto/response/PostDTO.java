package com.gildin.blog_service.dto.response;

import com.gildin.blog_service.enumTypes.PostType;
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
public class PostDTO {
    @Builder.Default
    private List<ShortUserDTO> likedUsersShort = new ArrayList<>();
    @Builder.Default
    private List<ShortCommentDTO> postCommentsShort = new ArrayList<>();

    private Long id;
    private ShortUserDTO authorUserShort;
    private String contentText;
    private String title;
    private PostType postType;
}
