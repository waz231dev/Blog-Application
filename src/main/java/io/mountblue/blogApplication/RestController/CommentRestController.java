package io.mountblue.blogApplication.RestController;

import io.mountblue.blogApplication.entity.Comment;
import io.mountblue.blogApplication.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentRestController {

    private CommentService commentService;
    @Autowired
    public CommentRestController(CommentService commentService) {

        this.commentService = commentService;
    }

    @PostMapping("/comment/create/{id}")
    public ResponseEntity<String> saveComment(@RequestBody Comment comment, @PathVariable("id") Integer id) {
        commentService.createComment(id, comment);
        return new ResponseEntity<>("Done", HttpStatus.CREATED);
    }

    @GetMapping("/api/comment/{id}")
    public ResponseEntity<Comment> getComment(@PathVariable("id") Integer commentId){
        Comment comment = commentService.findById(commentId);
        return new ResponseEntity<>(comment,HttpStatus.OK);
    }

    @DeleteMapping("/api/comment/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable("id") Integer commentId){
        commentService.deleteById(commentId);
        return new ResponseEntity<>("Comment deleted successfully",HttpStatus.OK);
    }

    @PutMapping("/api/comment")
    public ResponseEntity<String> editComment(@RequestParam("commentId") Integer commentId,
                                              @RequestParam("postId") Integer postId,@RequestBody Comment comment){
        commentService.updateComment(postId,commentId,comment);
        return new ResponseEntity<>("Comment edited successfully",HttpStatus.OK);
    }

}
