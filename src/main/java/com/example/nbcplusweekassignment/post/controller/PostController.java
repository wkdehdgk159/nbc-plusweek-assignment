package com.example.nbcplusweekassignment.post.controller;

import com.example.nbcplusweekassignment.global.security.UserDetailsImpl;
import com.example.nbcplusweekassignment.post.dto.GetPostDTO;
import com.example.nbcplusweekassignment.post.dto.GetPostDTO.Response;
import com.example.nbcplusweekassignment.post.service.PostService;
import com.example.nbcplusweekassignment.post.dto.CreatePostDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<CreatePostDTO.Response> createPost(@RequestBody CreatePostDTO.Request requestDTO,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        CreatePostDTO.Response responseDTO = postService.createPost(requestDTO, userDetails.user().getId());

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<GetPostDTO.Response>> getAllPosts() {

        List<GetPostDTO.Response> responseDTOs = postService.getAllPosts();

        return ResponseEntity.ok(responseDTOs);
    }
}
