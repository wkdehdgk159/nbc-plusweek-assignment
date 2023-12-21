package com.example.nbcplusweekassignment.schedule.service;

import com.example.nbcplusweekassignment.comment.service.CommentService;
import com.example.nbcplusweekassignment.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SchedulerService {

    private final Long POST_EXPIRATION_DAY = 90L;

    private final PostService postService;

    //매일 정각에
    @Scheduled(cron = "0 0 0 0/1 * *")
    public void deleteOldPost() {

        postService.deleteOldPost(POST_EXPIRATION_DAY);
    }

}
