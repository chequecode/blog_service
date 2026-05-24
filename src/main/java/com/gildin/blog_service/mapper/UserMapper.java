package com.gildin.blog_service.mapper;

import com.gildin.blog_service.dto.response.ShortCommentDTO;
import com.gildin.blog_service.dto.response.ShortPostDTO;
import com.gildin.blog_service.dto.response.UserDTO;
import com.gildin.blog_service.dto.create.CreateUserDTO;
import com.gildin.blog_service.dto.update.UpdateUserDTO;
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
public interface UserMapper {
    @Mapping(target = "userCommentsShort", source = "userComments", qualifiedByName = "commentsToShort")
    @Mapping(target = "userPostsShort", source = "userPosts", qualifiedByName = "postsToShort")
    @Mapping(target = "likedPostsShort", source = "likedPosts", qualifiedByName = "likedPostsToShort")
    UserDTO toDTO(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userPosts", ignore = true)
    @Mapping(target = "userComments", ignore = true)
    @Mapping(target = "likedPosts", ignore = true)
    User toEntity(CreateUserDTO createUserDTO);

    void updateFromUpdateDto(UpdateUserDTO updateUserDTO, @MappingTarget User user);

    List<UserDTO> toDTOList(List<User> users);

    @Named("postsToShort")
    default List<ShortPostDTO> postToShortDto(List<Post> posts) {
        return posts.stream()
                .map(post -> ShortPostDTO.builder().id(post.getId()).title(post.getTitle()).build())
                .collect(Collectors.toList());
    }

    @Named("commentsToShort")
    default List<ShortCommentDTO> commentToShortDto(List<Comment> comments) {
        return comments.stream()
                .map(comment -> ShortCommentDTO.builder().id(comment.getId()).contentText(comment.getContentText()).build())
                .collect(Collectors.toList());
    }

    @Named("likedPostsToShort")
    default List<ShortPostDTO> likedPostsToShort(List<Post> likedPosts) {
        if (likedPosts == null) return null;
        return likedPosts.stream()
                .map(post -> ShortPostDTO.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .build())
                .collect(Collectors.toList());
    }
}
