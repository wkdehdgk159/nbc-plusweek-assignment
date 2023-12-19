package com.example.nbcplusweekassignment.post.dto;

import com.example.nbcplusweekassignment.comment.dto.GetCommentDTO;
import com.example.nbcplusweekassignment.post.entity.Post;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

public class GetPostDTO {

    @Builder
    public record Response(Long postId, String title, String contents,
                           String nickname, LocalDateTime createdDate, List<GetCommentDTO.Response> comments) {

        public static GetPostDTO.Response of(Post post) {

            return Response.builder()
                    .postId(post.getId())
                    .title(post.getTitle())
                    .contents(post.getContents())
                    .nickname(post.getUser().getNickname())
                    .createdDate(post.getCreatedDate())
                    .comments(post.getCommentList().stream().map(GetCommentDTO.Response::of).toList())
                    .build();
        }
    }
}
