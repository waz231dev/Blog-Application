package io.mountblue.BlogApplication.service;

import io.mountblue.BlogApplication.entity.Post;
import io.mountblue.BlogApplication.repository.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private PostRepo postRepo;
   @Autowired
    public PostService(PostRepo postRepo) {
        this.postRepo = postRepo;
    }

    public void createPost(Post post) {
        postRepo.save(post);
    }

    public List<Post> getAllPosts() {
        List<Post> posts = postRepo.findAll();
        return posts;
    }

    public void deletById(int id) {
        postRepo.deleteById(id);
    }

    public Post findById(int id) {
        Post post = postRepo.findById(id).get();
        return post;
    }
}
