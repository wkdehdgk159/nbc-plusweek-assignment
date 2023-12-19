package com.example.nbcplusweekassignment.comment.service;

import com.example.nbcplusweekassignment.comment.dto.CreateCommentDTO;
import com.example.nbcplusweekassignment.comment.dto.GetCommentDTO;
import com.example.nbcplusweekassignment.comment.dto.GetCommentDTO.Response;
import com.example.nbcplusweekassignment.comment.entity.Comment;
import com.example.nbcplusweekassignment.comment.repository.CommentRepository;
import com.example.nbcplusweekassignment.global.exception.comment.NotFoundCommentException;
import com.example.nbcplusweekassignment.global.exception.post.NotFoundPostException;
import com.example.nbcplusweekassignment.global.exception.user.NotFoundUserException;
import com.example.nbcplusweekassignment.post.entity.Post;
import com.example.nbcplusweekassignment.post.repository.PostRepository;
import com.example.nbcplusweekassignment.user.entity.User;
import com.example.nbcplusweekassignment.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public List<Response> getAllComments(Long postId, int pageNum, String key, String sortBy) {

        Pageable pageable = PageRequest.of(pageNum, 5, Sort.by(key).descending());
        if ("ASC".equals(sortBy)) {
            pageable = PageRequest.of(pageNum, 5, Sort.by(key).ascending());
        }

        List<Comment> commentList = commentRepository.findAllByPostId(postId);

        return commentList.stream().map(comment ->
                GetCommentDTO.Response.of(comment)
        ).toList();
    }

    public Response getComment(Long id) {

        Comment comment = commentRepository.findById(id).orElseThrow(NotFoundCommentException::new);

        return GetCommentDTO.Response.of(comment);
    }
}
