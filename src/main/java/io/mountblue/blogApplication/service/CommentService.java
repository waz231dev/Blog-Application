package io.mountblue.blogApplication.service;

import io.mountblue.blogApplication.entity.Comment;

public interface CommentService {

    public void createComment(int id, Comment comment);

    public void deleteComment(int commentId);

    public Comment findById(int commentId);

    public void updateComment(int postId, int commentId, Comment comment);

}