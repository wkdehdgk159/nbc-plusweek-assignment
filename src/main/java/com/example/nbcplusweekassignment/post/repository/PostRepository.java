package com.example.nbcplusweekassignment.post.repository;

import com.example.nbcplusweekassignment.post.entity.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

//    List<Post> findAllOrderByCreated_atDesc();
}
