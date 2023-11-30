package io.mountblue.blogApplication.controller;

import io.mountblue.blogApplication.entity.Comment;
import io.mountblue.blogApplication.entity.Post;
import io.mountblue.blogApplication.entity.Tag;
import io.mountblue.blogApplication.repository.TagRepo;
import io.mountblue.blogApplication.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class PostController {

    private PostService postService;
    private TagRepo tagRepo;
    @Autowired
    public PostController(PostService postService, TagRepo tagRepo) {
        this.postService = postService;
        this.tagRepo = tagRepo;
    }


    @GetMapping("/newpost")
    public String newPost(Model model, @AuthenticationPrincipal UserDetails userDetails){
        String username = userDetails.getUsername();
        Post post = new Post();
        post.setAuthor(username);

        model.addAttribute("post",post);
        return "createPost";
    }

    @PostMapping("/savePost")
    public String createPost(@RequestParam("tagName") String tagNames,
                             @Valid @ModelAttribute("post") Post post, BindingResult result,
                             Model model) {
        if(result.hasErrors()){
            model.addAttribute("post",post);
            model.addAttribute("tag",tagNames);
            return "createPost";
        }

        else{
            postService.createPost(tagNames,post);

            return "redirect:/posts";
        }
    }

    @GetMapping("/filterPost")
    public String searchByFilter(Model model,@RequestParam("name") String name){

        List<Post> allPosts = postService.getAllPosts();

        List<Tag> allTags = tagRepo.findAll();
        Set<String> author= new HashSet<>();
        for(Post post : allPosts){
            author.add(post.getAuthor());
        }
        model.addAttribute("name",name);
        model.addAttribute("post",allPosts);
        model.addAttribute("allAuthor",author);
        model.addAttribute("tags",allTags);

        return "filter";
    }

    @GetMapping("/posts/{postId}/view")
    public String viewPost(@PathVariable("postId") Integer postId, Model model){
        Post post = postService.findById(postId);
        Comment comment = new Comment();

        model.addAttribute("post",post);
        model.addAttribute("comments",comment);

        return "viewPost";
    }

    @GetMapping("/posts/{postId}/delete")
    public String deletePost(@PathVariable("postId") Integer postId) {
        postService.deletById(postId);
        return "redirect:/posts";
    }

    @GetMapping("/posts/{postId}/edit")
    public String editPost(@PathVariable("postId") Integer postId,Model model,
                           @AuthenticationPrincipal UserDetails userDetails) {
        Post post = postService.findById(postId);
        if (userDetails.getUsername().equals(post.getAuthor()) || userDetails.getAuthorities().toString().contains("ROLE_ADMIN")) {
            StringBuilder tagValueWithComma = new StringBuilder();

            for (Tag tag : post.getTags()) {
                tagValueWithComma.append(tag.getName());
                tagValueWithComma.append(",");
            }
            if (tagValueWithComma.length() > 0) {
                tagValueWithComma.deleteCharAt(tagValueWithComma.length() - 1);
            }

            model.addAttribute("tagValue", tagValueWithComma.toString());
            model.addAttribute("post", post);

            return "editPost";
        } else {
            return "error";
        }
    }
    @PostMapping("/editPost/{postId}")
    public String saveChangesOfPost(@PathVariable("postId") Integer postId,
                                    @ModelAttribute("post") Post post,
                                    @RequestParam("postAuthor") String author,
                                    @RequestParam("tagName") String tagNames,
                                     Model model) {

            Post posts = postService.findById(postId);
            post.setAuthor(author);
            postService.updatePost(postId, tagNames, post);
            return "redirect:/posts/" + postId + "/view";
        }



    @GetMapping("/posts/search")
    public String searchPostWithPagination(
            @RequestParam(name = "query",required = false) String query,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "4") Integer size,
            @RequestParam(name="tag",required = false)List<String> tagList,
            @RequestParam(name = "author",required = false)List<String> authorList,
            Model model) {
        System.out.println(query);
        if(tagList == null){
            tagList= Collections.emptyList();
        }
        if(authorList==null){
            authorList= Collections.emptyList();
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Post> posts ;
        if (query != null && !query.isEmpty() && (tagList != null && !tagList.isEmpty() ||
                authorList != null && !authorList.isEmpty())){
            posts = postService.searchByAuthorOrExcerptOrTitleOrContentOrTag(query, pageable);
            posts = postService.fiterByTagsAndAuthor(tagList,authorList,pageable);
        }
        else{
            if(query != null && !query.isEmpty()) {
                posts = postService.searchByAuthorOrExcerptOrTitleOrContentOrTag(query, pageable);
            }else{
                posts = postService.fiterByTagsAndAuthor(tagList,authorList,pageable);
            }


        }

        String authors=String.join("&author=",authorList);
        String tags = String.join("&tag=",tagList);

        model.addAttribute("post", posts);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", posts.getTotalPages());
        model.addAttribute("value",1);
        model.addAttribute("tag",tags);
        model.addAttribute("author",authors);
        model.addAttribute("query",query);

        return "allPost";
    }

    @GetMapping("/posts")
    public String paginationWithSorting(Model model, @RequestParam(defaultValue = "0",required = false) Integer page,
                            @RequestParam(defaultValue = "4",required = false) Integer pageSize,
                            @RequestParam(defaultValue = "title",required = false) String sortField,
                            @RequestParam(defaultValue = "asc",required = false) String sortDir)
                            {

            Page<Post> postPage = postService.paginationAndSorting(page, pageSize, sortField, sortDir);

            model.addAttribute("post", postPage);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", postPage.getTotalPages());
            model.addAttribute("totalItems", postPage.getTotalElements());
            model.addAttribute("sortField", sortField);
            model.addAttribute("sortDir", sortDir);
            model.addAttribute("value",2);

            return "allPost";
        }

    }



