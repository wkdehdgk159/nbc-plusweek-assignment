package com.example.nbcplusweekassignment.comment.dto;

import com.example.nbcplusweekassignment.comment.entity.Comment;
import com.example.nbcplusweekassignment.post.entity.Post;
import java.time.LocalDateTime;
import lombok.Builder;

public class GetCommentDTO {

    @Builder
    public record Response(Long postId, String contents, String nickname, LocalDateTime modifiedDate) {

        public static Response of(Comment comment) {

            return Response.builder()
                    .postId(comment.getPost().getId())
                    .contents(comment.getContents())
                    .nickname(comment.getUser().getNickname())
                    .modifiedDate(comment.getModifiedDate())
                    .build();
        }
    }

}
