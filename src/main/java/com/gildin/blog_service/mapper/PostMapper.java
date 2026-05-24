package com.gildin.blog_service.mapper;

import com.gildin.blog_service.dto.create.CreatePostDTO;
import com.gildin.blog_service.dto.response.PostDTO;
import com.gildin.blog_service.dto.response.ShortCommentDTO;
import com.gildin.blog_service.dto.response.ShortUserDTO;
import com.gildin.blog_service.dto.update.UpdatePostDTO;
import com.gildin.blog_service.entity.Comment;
import com.gildin.blog_service.entity.Post;
import com.gildin.blog_service.entity.User;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PostMapper {
    @Mapping(target = "postCommentsShort", source = "comments", qualifiedByName = "commentsToShort")
    @Mapping(target = "authorUserShort", source = "authorUser", qualifiedByName = "authorUserToShort")
    @Mapping(target = "likedUsersShort", source = "likedUsers", qualifiedByName = "likedUsersToShort")
    PostDTO toDTO(Post post);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "likedUsers", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "authorUser", ignore = true)
    @Mapping(target = "postType", source = "postType")
    Post toEntity(CreatePostDTO createPostDTO);

    void updateFromUpdateDto(UpdatePostDTO updatePostDTO, @MappingTarget Post post);

    List<PostDTO> toDtoList(List<Post> list);

    @Named("commentsToShort")
    default List<ShortCommentDTO> commentsToShort(List<Comment> comments) {
        return comments.stream()
                .map(comment -> ShortCommentDTO.builder().id(comment.getId()).contentText(comment.getContentText()).build())
                .collect(Collectors.toList());
    }

    @Named("authorUserToShort")
    default ShortUserDTO authorUserToShort(User user) {
        return ShortUserDTO.builder()
                .id(user.getId()).username(user.getUsername()).build();
    }

    @Named("likedUsersToShort")
    default List<ShortUserDTO> likedUsersToShort(List<User> users) {
        if (users == null) return null;
        return users.stream()
                .map(user -> ShortUserDTO.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .build())
                .collect(Collectors.toList());
    }

}
