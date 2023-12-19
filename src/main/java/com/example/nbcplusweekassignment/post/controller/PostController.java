package com.example.nbcplusweekassignment.post.controller;

import com.example.nbcplusweekassignment.global.security.UserDetailsImpl;
import com.example.nbcplusweekassignment.post.dto.CreatePostDTO;
import com.example.nbcplusweekassignment.post.dto.GetPostDTO;
import com.example.nbcplusweekassignment.post.dto.GetPostListDTO;
import com.example.nbcplusweekassignment.post.dto.ModifyPostDTO;
import com.example.nbcplusweekassignment.post.service.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<List<GetPostListDTO.Response>> getAllPosts(
            @RequestParam("page") int pageNum,
            @RequestParam("key") String key,
            @RequestParam(value = "sortBy",required = false) String sortBy) {

        List<GetPostListDTO.Response> responseDTOs = postService.getAllPosts(pageNum, key, sortBy);

        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetPostDTO.Response> getPost(@PathVariable Long id) {

        GetPostDTO.Response responseDTO = postService.getPost(id);

        return ResponseEntity.ok(responseDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ModifyPostDTO.Response> modifyPost(@PathVariable Long id,
            @RequestBody ModifyPostDTO.Request requestDTO,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        ModifyPostDTO.Response responseDTO = postService.modifyPost(id, requestDTO, userDetails.user().getId());

        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        postService.deletePost(id, userDetails.user().getId());

        return ResponseEntity.ok("삭제가 완료되었습니다.");
    }
}
