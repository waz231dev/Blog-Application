package io.mountblue.blogApplication.restController;

import io.mountblue.blogApplication.entity.Post;
import io.mountblue.blogApplication.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
public class PostRestController {

   private PostService postService;


    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/api/posts/{id}")
    public ResponseEntity<Post> getPost(@PathVariable("id") Integer id){
        Post post = postService.findById(id);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @PostMapping("/api/posts")
    public ResponseEntity<String> savePost(@RequestParam("tags") String tags,@RequestBody Post post){
       postService.createPost(tags,post);

    return new ResponseEntity<>("Post added successfully",HttpStatus.CREATED);
    }

    @DeleteMapping("/api/posts/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") Integer postId,
                                             @AuthenticationPrincipal UserDetails userDetails) {

        Post posts = postService.findById(postId);
        if (userDetails.getUsername().equals(posts.getAuthor()) || userDetails.getAuthorities().toString().contains("ROLE_ADMIN")) {
            postService.deletById(postId);

            return new ResponseEntity<>("Post is deleted", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("You are not author", HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/api/posts/{id}")
    public ResponseEntity<String> updatePost(@PathVariable("id") Integer postId, @RequestBody Post post,
                                             @RequestParam("tags") String tags,
                                             @AuthenticationPrincipal UserDetails userDetails) {
        Post posts = postService.findById(postId);
        if (userDetails.getUsername().equals(posts.getAuthor()) || userDetails.getAuthorities().toString().contains("ROLE_ADMIN")) {

            postService.updatePost(postId, tags, post);

            return new ResponseEntity<>("Post is updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("You are not author", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/api/posts/search")
    public ResponseEntity<List<Post>> paginationWithSorting(@RequestParam(defaultValue = "0",required = false) Integer page,
                                        @RequestParam(defaultValue = "2",required = false) Integer pageSize,
                                        @RequestParam(defaultValue = "title",required = false) String sortField,
                                        @RequestParam(defaultValue = "asc",required = false) String sortDir){

        Page<Post> posts = postService.paginationAndSorting(page, pageSize, sortField, sortDir);
        List<Post> content = posts.getContent();
        return new ResponseEntity(content,HttpStatus.OK);
    }

    @GetMapping("/api/posts/filter")
    public ResponseEntity<List<Post>> searchPostWithPagination(
            @RequestParam(value = "query",required = false) String query,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "4") Integer size,
            @RequestParam(name="tags",required = false)List<String> tagList,
            @RequestParam(name = "authors",required = false)List<String> authorList) {

        System.out.println(tagList);
        System.out.println(authorList);
        if(tagList == null){
            tagList= Collections.emptyList();
        }
        if(authorList==null){
            authorList= Collections.emptyList();
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Post> posts;
        if(query!=null && !query.isEmpty()) {
            posts = postService.searchByAuthorOrExcerptOrTitleOrContentOrTag(query, pageable);
        }
        else{
            posts = postService.fiterByTagsAndAuthor(tagList,authorList,pageable);
            query="";
        }
        List<Post> post = posts.getContent();
        return new ResponseEntity<>(post,HttpStatus.OK);



    }
}


