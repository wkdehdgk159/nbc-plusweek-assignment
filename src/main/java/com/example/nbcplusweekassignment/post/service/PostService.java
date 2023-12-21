package com.example.nbcplusweekassignment.post.service;

import com.example.nbcplusweekassignment.comment.entity.Comment;
import com.example.nbcplusweekassignment.comment.repository.CommentRepository;
import com.example.nbcplusweekassignment.global.exception.common.NotAuthorException;
import com.example.nbcplusweekassignment.global.exception.post.NotFoundPostException;
import com.example.nbcplusweekassignment.post.dto.CreatePostDTO;
import com.example.nbcplusweekassignment.post.dto.CreatePostDTO.Request;
import com.example.nbcplusweekassignment.post.dto.CreatePostDTO.Response;
import com.example.nbcplusweekassignment.post.dto.GetPostDTO;
import com.example.nbcplusweekassignment.post.dto.GetPostListDTO;
import com.example.nbcplusweekassignment.post.dto.ModifyPostDTO;
import com.example.nbcplusweekassignment.post.entity.Post;
import com.example.nbcplusweekassignment.post.repository.PostRepository;
import com.example.nbcplusweekassignment.user.entity.User;
import com.example.nbcplusweekassignment.user.service.UserService;
import java.time.LocalDateTime;
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

    private final UserService userService;

    //순환참조 피하기 위해 repository 참조... 다른 방법이?
    private final CommentRepository commentRepository;

    public CreatePostDTO.Response createPost(Request requestDTO, Long userId) {

        User user = userService.findUserById(userId);

        Post post = requestDTO.toEntity(user);
        postRepository.save(post);

        return Response.of(post, post.getUser().getNickname());
    }

    //작성자명 오름차순
    public List<GetPostListDTO.Response> getAllPosts(int pageNum, String key, String sortBy) {

        Pageable pageable = PageRequest.of(pageNum, 5, Sort.by(key).descending());

        if ("ASC".equals(sortBy)) {
            pageable = PageRequest.of(pageNum, 5, Sort.by(key).ascending());
        }
        //도메인 관련 로직은 서비스에서 하는 게 맞는 것 같은데. 이건 디비에서 어떻게 긁어올까에 대한 내용이라

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
    public ModifyPostDTO.Response modifyPost(Long id, ModifyPostDTO.Request requestDTO,
            Long userId) {

        Post post = postRepository.findById(id).orElseThrow(NotFoundPostException::new);

        //변경하려는 사용자가 게시글의 글쓴이와 동일한 지 비교
        if (!post.getUser().getId().equals(userId)) {
            throw new NotAuthorException();
        }

        String title = requestDTO.title();
        String contents = requestDTO.contents();

        post.modifyPost(title, contents);

        return ModifyPostDTO.Response.of(post);
    }

    @Transactional
    public void deletePost(Long id, Long userId) {

        Post post = postRepository.findById(id).orElseThrow(NotFoundPostException::new);

        if (!post.getUser().getId().equals(userId)) {
            throw new NotAuthorException();
        }

        deleteRelatedComments(id);

        postRepository.delete(post);
    }

    private void deleteRelatedComments(Long id) {

        List<Comment> commentList = commentRepository.findAllByPostId(id);

        commentRepository.deleteAll(commentList);
    }
    //cascade 검색.. 통일성~
    //현업에서는 백업이나 삭제처리 필드만 바꾸는데 cascade는 아예 삭제임 -> 아니다.
    //delete annotation  옵션이 있어서 진짜 삭제를 할 지 soft delete 이건 쿼리를 짜야한다.
    //정확한 이름이 SQL delete -> 어떤 떄의 차이가 나냐면
    //여러 이유로 갈리긴 한다. 신고할만한 댓글이면 아예 삭제. 데이터나 시간 부하, 혹은 임시 데이터 백업
    //보통은 가지고 있을 떄까지 가지고 있다가 정리할 때 완전 정리

    public Post findPostById(Long postId) {

        return postRepository.findById(postId).orElseThrow(NotFoundPostException::new);
    }

    public void deleteOldPost(Long POST_EXPIRATION_TIME_DAY) {

        LocalDateTime currentTime = LocalDateTime.now();
        List<Post> postList = postRepository.findAll();

        for(Post post: postList) {
            if(post.getModifiedDate().plusDays(POST_EXPIRATION_TIME_DAY).isBefore(currentTime)) {
                postRepository.delete(post);
            }
        }
    }




}
