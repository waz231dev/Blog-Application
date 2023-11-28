package io.mountblue.blogApplication.service;

import io.mountblue.blogApplication.entity.Comment;

public interface CommentService {

    public void createComment(Integer id, Comment comment);

    public void deleteComment(Integer commentId);

    public Comment findById(Integer commentId);

    public void updateComment(Integer postId, Integer commentId, Comment comment);

    public void deleteById(Integer id);

}