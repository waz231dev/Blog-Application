package io.mountblue.blogApplication.service;

import io.mountblue.blogApplication.entity.Comment;
import io.mountblue.blogApplication.entity.Post;
import io.mountblue.blogApplication.repository.CommentRepo;
import io.mountblue.blogApplication.repository.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService{

    CommentRepo commentRepo;
    PostRepo postRepo;
    @Autowired
    public CommentServiceImpl(CommentRepo commentRepo, PostRepo postRepo) {
        this.commentRepo = commentRepo;
        this.postRepo = postRepo;
    }

    public void createComment(int id, Comment comment) {
        Post post = postRepo.findById(id).get();
        comment.setPost(post);
        commentRepo.save(comment);
    }
    public void deleteComment(int commentId){

        commentRepo.deleteById(commentId);
    }
    public  Comment findById(int commentId){

        return commentRepo.findById(commentId).get();
    }

    public void updateComment(int postId,int commentId,Comment comment){
        Post post = postRepo.findById(postId).get();
        Comment oldComment = commentRepo.findById(commentId).get();
        Comment newComment = new Comment();
        newComment.setId(oldComment.getId());
        newComment.setComment(comment.getComment());
        newComment.setName(oldComment.getName());
        newComment.setEmail(oldComment.getEmail());
        newComment.setPost(post);
        commentRepo.save(newComment);
    }

}
