package com.example.nbcplusweekassignment.post.dto;

import com.example.nbcplusweekassignment.post.entity.Post;
import com.example.nbcplusweekassignment.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Builder;

public class CreatePostDTO {

    public record Request(
            @NotBlank @Size(max = 500) String title,
            @NotBlank @Size(max = 5000) String contents) {

        public Post toEntity(User user) {
            return Post.builder()
                    .user(user)
                    .title(title)
                    .contents(contents)
                    .build();
        }
    }

    @Builder
    public record Response(Long postId, String title, String contents,
                           String nickname, LocalDateTime createdDate) {

        public static Response of(Post post, String nickname) {

            return Response.builder()
                    .postId(post.getId())
                    .title(post.getTitle())
                    .contents(post.getContents())
                    .nickname(nickname)
                    .createdDate(post.getCreatedDate())
                    .build();
        }
    }
}
