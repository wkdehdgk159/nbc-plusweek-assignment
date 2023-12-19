package com.example.nbcplusweekassignment.post.entity;

import com.example.nbcplusweekassignment.global.auditing.BaseTimeEntity;
import com.example.nbcplusweekassignment.post.dto.CreatePostDTO;
import com.example.nbcplusweekassignment.post.dto.ModifyPostDTO;
import com.example.nbcplusweekassignment.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String title;

    @Column(nullable = false, length = 5000)
    private String contents;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    public Post(CreatePostDTO.Request requestDTO, User user) {
        this.title = requestDTO.title();
        this.contents = requestDTO.contents();
        this.user = user;
    }

    public void modifyPost(ModifyPostDTO.Request requestDTO) {
        this.title = requestDTO.title();
        this.contents = requestDTO.contents();
    }

}
