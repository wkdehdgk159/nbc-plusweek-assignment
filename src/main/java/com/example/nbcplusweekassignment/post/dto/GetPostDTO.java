package com.example.nbcplusweekassignment.post.dto;

import com.example.nbcplusweekassignment.post.dto.CreatePostDTO.Response;
import com.example.nbcplusweekassignment.post.entity.Post;
import java.time.LocalDateTime;
import lombok.Builder;

public class GetPostDTO {

    @Builder
    public record Response(Long postId, String title, String contents,
                           String nickname, LocalDateTime created_at) {

        public static GetPostDTO.Response of(Post post, String nickname) {

            return GetPostDTO.Response.builder()
                    .postId(post.getId())
                    .title(post.getTitle())
                    .contents(post.getContents())
                    .nickname(nickname)
                    .created_at(post.getCreated_at())
                    .build();
        }
    }
}
