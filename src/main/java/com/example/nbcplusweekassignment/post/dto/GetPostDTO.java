package com.example.nbcplusweekassignment.post.dto;

import com.example.nbcplusweekassignment.post.dto.CreatePostDTO.Response;
import com.example.nbcplusweekassignment.post.entity.Post;
import java.time.LocalDateTime;
import lombok.Builder;

public class GetPostDTO {

    @Builder
    public record Response(Long postId, String title, String contents,
                           String nickname, LocalDateTime createdDate) {

        public static GetPostDTO.Response of(Post post) {

            return Response.builder()
                    .postId(post.getId())
                    .title(post.getTitle())
                    .contents(post.getContents())
                    .nickname(post.getUser().getNickname())
                    .createdDate(post.getCreatedDate())
                    .build();
        }
    }
}
