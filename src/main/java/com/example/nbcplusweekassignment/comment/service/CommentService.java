package com.example.nbcplusweekassignment.comment.service;

import com.example.nbcplusweekassignment.comment.dto.CreateCommentDTO;
import com.example.nbcplusweekassignment.comment.entity.Comment;
import com.example.nbcplusweekassignment.comment.repository.CommentRepository;
import com.example.nbcplusweekassignment.global.exception.post.NotFoundPostException;
import com.example.nbcplusweekassignment.global.exception.user.NotFoundUserException;
import com.example.nbcplusweekassignment.post.entity.Post;
import com.example.nbcplusweekassignment.post.repository.PostRepository;
import com.example.nbcplusweekassignment.user.entity.User;
import com.example.nbcplusweekassignment.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    public CreateCommentDTO.Response createComment(Long postId, CreateCommentDTO.Request requestDTO,
            Long userId) {

        User user = userRepository.findById(userId).orElseThrow(NotFoundUserException::new);
        Post post = postRepository.findById(postId).orElseThrow(NotFoundPostException::new);

        Comment comment = requestDTO.toEntity(post, user);

        commentRepository.save(comment);

        return CreateCommentDTO.Response.of(comment);
    }
}
