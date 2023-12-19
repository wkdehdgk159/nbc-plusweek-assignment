package com.example.nbcplusweekassignment.comment.dto;

import com.example.nbcplusweekassignment.comment.entity.Comment;
import com.example.nbcplusweekassignment.post.entity.Post;
import com.example.nbcplusweekassignment.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.Builder;

public class CreateCommentDTO {

    public record Request(@NotBlank String contents) {

        public Comment toEntity(Post post, User user) {
            return Comment.builder()
                    .post(post)
                    .user(user)
                    .contents(contents)
                    .build();
        }
    }

    @Builder
    public record Response(Long postId, String contents, String nickname, LocalDateTime createdAt) {

        public static CreateCommentDTO.Response of(Comment comment) {
            return Response.builder()
                    .postId(comment.getPost().getId())
                    .contents(comment.getContents())
                    .nickname(comment.getUser().getNickname())
                    .createdAt(comment.getCreatedDate())
                    .build();
        }
    }

}
