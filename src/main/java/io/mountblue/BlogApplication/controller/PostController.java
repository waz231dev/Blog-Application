package io.mountblue.BlogApplication.controller;

import io.mountblue.BlogApplication.entity.Comment;
import io.mountblue.BlogApplication.entity.Post;
import io.mountblue.BlogApplication.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PostController {

    PostService postService;
    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/post")
    public String createPost(Model model){
        model.addAttribute("post",new Post());

        return "createPost";
    }
    @PostMapping("/savePost")
    public String fillDataInDatabase( @Valid @ModelAttribute("post") Post post,
                                     BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("post",post);
            return "createPost";
        }
        else{
            postService.createPost(post);
            return "redirect:/";
        }
    }
    @GetMapping("/")
    public String showAllPost(Model model){
        List<Post> allPosts = postService.getAllPosts();
        model.addAttribute("post",allPosts);
        return "allPost";
    }

    @GetMapping("/posts/{postId}/view")
    public String viewPost(@PathVariable("postId") int id, Model model){
        Post post = postService.findById(id);
        Comment comment = new Comment();
        model.addAttribute("post",post);
        model.addAttribute("comments",comment);
        return "viewPost";

    }

    @GetMapping("/posts/{postId}/delete")
    public String deletePost(@PathVariable("postId") int id) {
        postService.deletById(id);
        return "redirect:/";
    }

    @GetMapping("/posts/{postId}/edit")
    public String editPost(@PathVariable("postId") int id,Model model){
        Post post = postService.findById(id);
        model.addAttribute("post",post);
        return "editPost";
    }
    @PostMapping("/editPost/{postId}")
    public String saveChangesOfPost(@PathVariable("postId") int id,@Valid @ModelAttribute("post") Post post,
                                    BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("post",post);
            return "editPost";
        }
        else{
            post.setId(id);
            postService.createPost(post);
            return "redirect:/";
        }
    }


}
