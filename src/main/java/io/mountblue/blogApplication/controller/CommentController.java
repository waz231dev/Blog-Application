package io.mountblue.blogApplication.controller;

import io.mountblue.blogApplication.entity.Comment;
import io.mountblue.blogApplication.entity.Post;
import io.mountblue.blogApplication.service.CommentService;
import io.mountblue.blogApplication.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {

    private CommentService commentService;
    private PostService postService;
    @Autowired
    public CommentController(CommentService commentService, PostService postService) {
        this.commentService = commentService;
        this.postService = postService;
    }

    @PostMapping("/{postId}/comment")
    public String addCommentInPost(@PathVariable("postId") Integer postId, @Valid @ModelAttribute("comments") Comment comment,
                                        BindingResult result, Model model){

        Post post = postService.findById(postId);
        if(result.hasErrors()){
            model.addAttribute("comments",comment);
            model.addAttribute("post",post);
            return "viewPost";
        }
        commentService.createComment(postId,comment);

        return "redirect:/posts/"+postId+"/view";
    }

    @GetMapping("/comment/{postId}/{commentId}/edit")
    public String editCommentForm(@PathVariable("postId") Integer postId, @PathVariable("commentId") Integer commentId,
                                  Model model){

        Post post = postService.findById(postId);
        Comment comment = commentService.findById(commentId);

        model.addAttribute("post",post);
        model.addAttribute("comments",comment);

        return "editComment";
    }

    @PostMapping("/comment/{postId}/{commentId}")
    public String editComment( @ModelAttribute("comments") Comment comment,@PathVariable("postId") Integer postId,
                              @PathVariable("commentId") Integer commentId,Model model){

        Post post = postService.findById(postId);
        commentService.updateComment(postId,commentId,comment);

        return "redirect:/posts/"+postId+"/view";

    }

    @GetMapping("/comment/{postId}/{commentId}/delete")
    public String deleteComment(@PathVariable("commentId") Integer commentId,@PathVariable("postId") Integer postId){

        commentService.deleteComment(commentId);
        return "redirect:/posts/"+postId+"/view";
    }
}

