package com.example.nbcplusweekassignment.comment.repository;

import com.example.nbcplusweekassignment.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
