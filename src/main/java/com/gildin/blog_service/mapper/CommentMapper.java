package com.gildin.blog_service.mapper;

import com.gildin.blog_service.dto.create.CreateCommentDTO;
import com.gildin.blog_service.dto.response.CommentDTO;
import com.gildin.blog_service.dto.response.ShortPostDTO;
import com.gildin.blog_service.dto.response.ShortUserDTO;
import com.gildin.blog_service.dto.update.UpdateCommentDTO;
import com.gildin.blog_service.entity.Comment;
import com.gildin.blog_service.entity.Post;
import com.gildin.blog_service.entity.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CommentMapper {
    @Mapping(target = "authorUserShort", source = "authorUser", qualifiedByName = "authorUserToShort")
    @Mapping(target = "commentedPostShort", source = "commentedPost", qualifiedByName = "commentedPostToShort")
    CommentDTO toDTO(Comment comment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authorUser", ignore = true)
    @Mapping(target = "commentedPost", ignore = true)
    Comment toEntity(CreateCommentDTO createCommentDTO);

    void updateFromUpdateDto(UpdateCommentDTO updateCommentDTO, @MappingTarget Comment comment);

    List<CommentDTO> toDtoList(List<Comment> comments);

    @Named("authorUserToShort")
    default ShortUserDTO authorUserToShort(User authorUser) {
        return ShortUserDTO.builder().id(authorUser.getId()).username(authorUser.getUsername()).build();
    }

    @Named("commentedPostToShort")
    default ShortPostDTO commentedPostToShort(Post commentedPost) {
        return ShortPostDTO.builder().id(commentedPost.getId()).title(commentedPost.getTitle()).build();
    }
}
