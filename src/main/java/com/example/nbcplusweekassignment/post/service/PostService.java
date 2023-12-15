package com.example.nbcplusweekassignment.post.service;

import com.example.nbcplusweekassignment.global.exception.user.NotFoundUserException;
import com.example.nbcplusweekassignment.post.dto.CreatePostDTO;
import com.example.nbcplusweekassignment.post.dto.CreatePostDTO.Request;
import com.example.nbcplusweekassignment.post.dto.CreatePostDTO.Response;
import com.example.nbcplusweekassignment.post.dto.GetPostDTO;
import com.example.nbcplusweekassignment.post.entity.Post;
import com.example.nbcplusweekassignment.post.repository.PostRepository;
import com.example.nbcplusweekassignment.user.entity.User;
import com.example.nbcplusweekassignment.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    public CreatePostDTO.Response createPost(Request requestDTO, Long userId) {

        User user = userRepository.findById(userId).orElseThrow(NotFoundUserException::new);

        Post post = new Post(requestDTO, user);
        postRepository.save(post);
//        Post post = postRepository.save(requestDTO.toEntity(userId));

        return Response.of(post, post.getUser().getNickname());
    }

    public List<GetPostDTO.Response> getAllPosts() {

        List<Post> postList = postRepository.findAll();
//        List<Post> postList = postRepository.findAllOrderByCreated_atDesc();

        return postList.stream().map(post ->
                GetPostDTO.Response.of(post, post.getUser().getNickname())
        ).toList();

    }
}
