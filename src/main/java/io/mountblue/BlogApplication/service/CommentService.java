package io.mountblue.BlogApplication.service;

import io.mountblue.BlogApplication.entity.Comment;
import io.mountblue.BlogApplication.entity.Post;
import io.mountblue.BlogApplication.repository.CommentRepo;
import io.mountblue.BlogApplication.repository.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    CommentRepo commentRepo;
    PostRepo postRepo;
    @Autowired
    public CommentService(CommentRepo commentRepo, PostRepo postRepo) {
        this.commentRepo = commentRepo;
        this.postRepo = postRepo;
    }

    public void createComment(int id, Comment comment) {
        Post post = postRepo.findById(id).get();
        comment.setPost(post);
        commentRepo.save(comment);
    }
}
