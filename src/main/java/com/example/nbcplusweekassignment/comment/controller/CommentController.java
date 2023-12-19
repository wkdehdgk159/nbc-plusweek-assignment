package com.example.nbcplusweekassignment.comment.controller;

import com.example.nbcplusweekassignment.comment.dto.CreateCommentDTO;
import com.example.nbcplusweekassignment.comment.dto.GetCommentDTO;
import com.example.nbcplusweekassignment.comment.dto.GetCommentDTO.Response;
import com.example.nbcplusweekassignment.comment.service.CommentService;
import com.example.nbcplusweekassignment.global.security.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CreateCommentDTO.Response> createComment(
            @PathVariable Long postId,
            @RequestBody CreateCommentDTO.Request requestDTO,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        CreateCommentDTO.Response responseDTO = commentService.createComment(postId, requestDTO,
                userDetails.user().getId());

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<GetCommentDTO.Response>> getAllComments(
            @PathVariable Long postId,
            @RequestParam("page") int pageNum,
            @RequestParam("key") String key,
            @RequestParam(value = "sortBy",required = false) String sortBy) {

        List<GetCommentDTO.Response> responseDTOs = commentService.getAllComments(postId, pageNum, key, sortBy);

        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<GetCommentDTO.Response> getComment(@PathVariable Long id) {

        GetCommentDTO.Response responseDTO = commentService.getComment(id);

        return ResponseEntity.ok(responseDTO);
    }



}
