package com.example.nbcplusweekassignment.post.dto;

import com.example.nbcplusweekassignment.post.dto.GetPostDTO.Response;
import com.example.nbcplusweekassignment.post.entity.Post;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.Builder;

public class ModifyPostDTO {

    public record Request(
            @NotBlank String title,
            @NotBlank String contents
    ) {
    }

    @Builder
    public record Response(Long postId, String title, String contents,
                           String nickname, LocalDateTime createdDate) {

        public static ModifyPostDTO.Response of(Post post) {

            return ModifyPostDTO.Response.builder()
                    .postId(post.getId())
                    .title(post.getTitle())
                    .contents(post.getContents())
                    .nickname(post.getUser().getNickname())
                    .createdDate(post.getCreatedDate())
                    .build();
        }
    }
}
