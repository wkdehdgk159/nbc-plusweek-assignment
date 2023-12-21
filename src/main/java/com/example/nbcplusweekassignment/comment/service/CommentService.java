package com.example.nbcplusweekassignment.comment.service;

import com.example.nbcplusweekassignment.comment.dto.CreateCommentDTO;
import com.example.nbcplusweekassignment.comment.dto.GetCommentDTO;
import com.example.nbcplusweekassignment.comment.dto.GetCommentDTO.Response;
import com.example.nbcplusweekassignment.comment.dto.ModifyCommentDTO;
import com.example.nbcplusweekassignment.comment.dto.ModifyCommentDTO.Request;
import com.example.nbcplusweekassignment.comment.entity.Comment;
import com.example.nbcplusweekassignment.comment.repository.CommentRepository;
import com.example.nbcplusweekassignment.global.exception.comment.NotFoundCommentException;
import com.example.nbcplusweekassignment.global.exception.common.NotAuthorException;
import com.example.nbcplusweekassignment.post.entity.Post;
import com.example.nbcplusweekassignment.post.service.PostService;
import com.example.nbcplusweekassignment.user.entity.User;
import com.example.nbcplusweekassignment.user.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final UserService userService;

    private final PostService postService;

    public CreateCommentDTO.Response createComment(Long postId, CreateCommentDTO.Request requestDTO,
            Long userId) {

        User user = userService.findUserById(userId);
        Post post = postService.findPostById(postId);
        //이것 자체를 Post 내에

        Comment comment = requestDTO.toEntity(post, user);

        commentRepository.save(comment);

        return CreateCommentDTO.Response.of(comment);
    }

    public List<GetCommentDTO.Response> getAllComments(Long postId, int pageNum, String key, String sortBy) {

        Pageable pageable = PageRequest.of(pageNum, 5, Sort.by(key).descending());
        if ("ASC".equals(sortBy)) {
            pageable = PageRequest.of(pageNum, 5, Sort.by(key).ascending());
        }

        List<Comment> commentList = commentRepository.findAllByPostId(postId);

        return commentList.stream().map(Response::of).toList();
    }

    public GetCommentDTO.Response getComment(Long id) {

        Comment comment = commentRepository.findById(id).orElseThrow(NotFoundCommentException::new);

        return GetCommentDTO.Response.of(comment);
    }

    @Transactional
    public ModifyCommentDTO.Response modifyComment(Long id, Request requestDTO, Long userId) {

        Comment comment = commentRepository.findById(id).orElseThrow(NotFoundCommentException::new);

        if(!comment.getUser().getId().equals(userId)) {
            throw new NotAuthorException();
        }

        String contents = requestDTO.contents();
        comment.modifyComment(contents);

        return ModifyCommentDTO.Response.of(comment);
    }

    @Transactional
    public void deleteComment(Long id, Long userId) {

        Comment comment = commentRepository.findById(id).orElseThrow(NotFoundCommentException::new);

        if(!comment.getUser().getId().equals(userId)) {
            throw new NotAuthorException();
        }

        commentRepository.delete(comment);
    }
}
