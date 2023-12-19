package com.example.nbcplusweekassignment.post.service;

import com.example.nbcplusweekassignment.global.exception.common.NotAuthorException;
import com.example.nbcplusweekassignment.global.exception.post.NotFoundPostException;
import com.example.nbcplusweekassignment.global.exception.user.NotFoundUserException;
import com.example.nbcplusweekassignment.post.dto.CreatePostDTO;
import com.example.nbcplusweekassignment.post.dto.CreatePostDTO.Request;
import com.example.nbcplusweekassignment.post.dto.CreatePostDTO.Response;
import com.example.nbcplusweekassignment.post.dto.GetPostDTO;
import com.example.nbcplusweekassignment.post.dto.GetPostListDTO;
import com.example.nbcplusweekassignment.post.dto.ModifyPostDTO;
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
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    public CreatePostDTO.Response createPost(Request requestDTO, Long userId) {

        User user = userRepository.findById(userId).orElseThrow(NotFoundUserException::new);

        Post post = requestDTO.toEntity(user);
        postRepository.save(post);

        return Response.of(post, post.getUser().getNickname());
    }

    //작성자명 오름차순
    public List<GetPostListDTO.Response> getAllPosts(int pageNum, String key, String sortBy) {

        Pageable pageable = PageRequest.of(pageNum, 5, Sort.by(key).descending());;
        if("ASC".equals(sortBy)) {
            pageable = PageRequest.of(pageNum, 5, Sort.by(key).ascending());
        }

        List<Post> postList = postRepository.findAll(pageable).toList();

        return postList.stream().map(post ->
                GetPostListDTO.Response.of(post, post.getUser().getNickname())
        ).toList();
    }

    public GetPostDTO.Response getPost(Long id) {

        Post post = postRepository.findById(id).orElseThrow(NotFoundPostException::new);

        return GetPostDTO.Response.of(post);
    }

    @Transactional
    public ModifyPostDTO.Response modifyPost(Long id, ModifyPostDTO.Request requestDTO, Long userId) {

        Post post = postRepository.findById(id).orElseThrow(NotFoundPostException::new);

        //변경하려는 사용자가 게시글의 글쓴이와 동일한 지 비교
        if(!post.getUser().getId().equals(userId)) {
            throw new NotAuthorException();
        }

        String title = requestDTO.title();
        String contents = requestDTO.contents();

        post.modifyPost(requestDTO.title(), requestDTO.contents());

        return ModifyPostDTO.Response.of(post);
    }

    @Transactional
    public void deletePost(Long id, Long userId) {

        Post post = postRepository.findById(id).orElseThrow(NotFoundPostException::new);

        if(!post.getUser().getId().equals(userId)) {
            throw new NotAuthorException();
        }

        postRepository.delete(post);
    }
}
