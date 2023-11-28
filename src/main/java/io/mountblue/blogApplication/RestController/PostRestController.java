package io.mountblue.blogApplication.RestController;

import io.mountblue.blogApplication.entity.Comment;
import io.mountblue.blogApplication.entity.Post;
import io.mountblue.blogApplication.service.CommentService;
import io.mountblue.blogApplication.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostRestController {

   private PostService postService;


    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/post/get/{id}")
    public ResponseEntity<Post> getPost(@PathVariable("id") Integer id){
        Post post = postService.findById(id);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @PostMapping("/post/save")
    public ResponseEntity<String> savePost(@RequestParam("tags") String tags,@RequestBody Post post){
       postService.createPost(tags,post);

    return new ResponseEntity<>("Post added successfully",HttpStatus.CREATED);
    }

    @DeleteMapping("/post/delete/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") Integer postId){

        postService.deletById(postId);

        return  new ResponseEntity<>("Post is delete",HttpStatus.OK);

    }

    @PutMapping("/post/update/{id}")
    public ResponseEntity<String> updatePost(@PathVariable("id") Integer postId,@RequestBody Post post,
                                             @RequestParam("tags") String tags){
        postService.updatePost(postId,tags,post);

        return new ResponseEntity<>("Post is updated successfully",HttpStatus.OK);
    }

    @GetMapping("api/search")
    public ResponseEntity<List<Post>> paginationWithSorting(@RequestParam(defaultValue = "0",required = false) Integer page,
                                        @RequestParam(defaultValue = "2",required = false) Integer pageSize,
                                        @RequestParam(defaultValue = "title",required = false) String sortField,
                                        @RequestParam(defaultValue = "asc",required = false) String sortDir){

        Page<Post> posts = postService.paginationAndSorting(page, pageSize, sortField, sortDir);
        List<Post> content = posts.getContent();
        return new ResponseEntity(content,HttpStatus.OK);
    }
}


