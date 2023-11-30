package io.mountblue.blogApplication.restController;

import io.mountblue.blogApplication.entity.Comment;
import io.mountblue.blogApplication.entity.Post;
import io.mountblue.blogApplication.service.CommentService;
import io.mountblue.blogApplication.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentRestController {

    private CommentService commentService;
    private PostService postService;

    @Autowired
    public CommentRestController(CommentService commentService, PostService postService) {
        this.commentService = commentService;
        this.postService = postService;
    }

    @PostMapping("/api/comments/{id}")
    public ResponseEntity<String> saveComment(@RequestBody Comment comment, @PathVariable("id") Integer id) {
        commentService.createComment(id, comment);
        return new ResponseEntity<>("Done", HttpStatus.CREATED);
    }

    @GetMapping("/api/comments/{id}")
    public ResponseEntity<Comment> getComment(@PathVariable("id") Integer commentId) {
        Comment comment = commentService.findById(commentId);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @DeleteMapping("/api/comments/{commentId}/{postId}")
    public ResponseEntity<String> deleteComment(@PathVariable("commentId") Integer commentId,
                                                @PathVariable("postId") Integer postId,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        Post post = postService.findById(postId);
        if (userDetails.getUsername().equals(post.getAuthor()) || userDetails.getAuthorities().toString().contains("ROLE_ADMIN")) {

            commentService.deleteById(commentId);
            return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("You are not author", HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/api/comments")
    public ResponseEntity<String> editComment(@RequestParam("commentId") Integer commentId,
                                              @RequestParam("postId") Integer postId, @RequestBody Comment comment,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        Post post = postService.findById(postId);
        if (userDetails.getUsername().equals(post.getAuthor()) || userDetails.getAuthorities().toString().contains("ROLE_ADMIN")) {
            commentService.updateComment(postId, commentId, comment);
            return new ResponseEntity<>("Comment edited successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("You are not author", HttpStatus.UNAUTHORIZED);
        }

    }
}
