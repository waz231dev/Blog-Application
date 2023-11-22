package io.mountblue.BlogApplication.controller;

import io.mountblue.BlogApplication.entity.Comment;
import io.mountblue.BlogApplication.entity.Post;
import io.mountblue.BlogApplication.service.CommentService;
import io.mountblue.BlogApplication.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {

    CommentService commentService;
    PostService postService;
    @Autowired
    public CommentController(CommentService commentService, PostService postService) {
        this.commentService = commentService;
        this.postService = postService;
    }

    @PostMapping("/{postId}/comment")
    public String fillDataInCommentForm(@PathVariable("postId") int id, @Valid @ModelAttribute("comments") Comment comment,
                                        BindingResult result, Model model){
        Post post = postService.findById(id);
        if(result.hasErrors()){
            model.addAttribute("comments",comment);
            model.addAttribute("post",post);
            return "viewPost";
        }
        commentService.createComment(id,comment);

        System.out.println("hello world");

        return "redirect:/posts/"+id+"/view";
    }
}

