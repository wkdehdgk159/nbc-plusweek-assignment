package com.example.nbcplusweekassignment.comment.dto;

import com.example.nbcplusweekassignment.comment.dto.GetCommentDTO.Response;
import com.example.nbcplusweekassignment.comment.entity.Comment;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.Builder;

public class ModifyCommentDTO {

    public record Request(
            @NotBlank String contents
    ) {

    }

    @Builder
    public record Response(Long postId, String contents, String nickname, LocalDateTime modifiedDate) {

        public static ModifyCommentDTO.Response of(Comment comment) {

            return ModifyCommentDTO.Response.builder()
                    .postId(comment.getPost().getId())
                    .contents(comment.getContents())
                    .nickname(comment.getUser().getNickname())
                    .modifiedDate(comment.getModifiedDate())
                    .build();
        }
    }

}
