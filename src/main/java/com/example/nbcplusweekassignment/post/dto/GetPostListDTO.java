package com.example.nbcplusweekassignment.post.dto;

import com.example.nbcplusweekassignment.post.entity.Post;
import java.time.LocalDateTime;
import lombok.Builder;

public class GetPostListDTO {

    @Builder
    public record Response(String title, String nickname, LocalDateTime createdDate) {

        public static GetPostListDTO.Response of(Post post, String nickname) {
            return Response.builder()
                    .title(post.getTitle())
                    .nickname(nickname)
                    .createdDate(post.getCreatedDate())
                    .build();
        }
    }

}
