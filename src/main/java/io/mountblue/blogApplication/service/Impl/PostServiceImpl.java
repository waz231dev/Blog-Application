package io.mountblue.blogApplication.service.Impl;

import io.mountblue.blogApplication.entity.Post;
import io.mountblue.blogApplication.entity.Tag;
import io.mountblue.blogApplication.entity.User;
import io.mountblue.blogApplication.repository.PostRepo;
import io.mountblue.blogApplication.repository.TagRepo;
import io.mountblue.blogApplication.service.PostService;
import io.mountblue.blogApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

     private PostRepo postRepo;
    private TagRepo tagRepo;

    UserService userService;
    @Autowired
    public PostServiceImpl(PostRepo postRepo, TagRepo tagRepo, UserService userService) {
        this.postRepo = postRepo;
        this.tagRepo = tagRepo;
        this.userService = userService;
    }

    public void createPost(String tagNames, Post post) {

        String[] tagArray = tagNames.split(",");
        for (String tagName : tagArray) {
            tagName = tagName.trim();
            Tag tag =tagRepo.findByName(tagName);
            if(tag != null){
                post.getTags().add(tag);
            }
            else{
                Tag newTag = new Tag();
                newTag.setName(tagName);
                 tagRepo.save(newTag);
                 post.getTags().add(newTag);
            }
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUserName(username);
        post.setAuthor(username);
        post.setUser(user);
       postRepo.save(post);
    }

    public List<Post> getAllPosts() {
        List<Post> posts = postRepo.findAll();

        return posts;
    }

    public void deletById(Integer id) {
       postRepo.deleteById(id);
    }

    public Post findById(Integer id) {
        Post post = postRepo.findById(id).get();
        return post;
    }
    public void updatePost(Integer postId,String tagNames,Post post){
        post.getTags().clear();
        String[] tagArray = tagNames.split(",");
        for (String tagName : tagArray) {
            tagName = tagName.trim();
            Tag tag =tagRepo.findByName(tagName);
            if(tag != null){
                post.getTags().add(tag);
            }
            else{
                Tag newTag = new Tag();
                newTag.setName(tagName);
                tagRepo.save(newTag);
                post.getTags().add(newTag);
            }
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println(username);
        User user = userService.findByUserName(username);

        post.setAuthor(username);
        post.setUser(user);
        post.setId(postId);
       postRepo.save(post);
    }


    public Page<Post> searchByAuthorOrExcerptOrTitleOrContentOrTag(String query,Pageable pageable) {
        return postRepo.searchPostByTagOrTitleOrAuthorOrContentOrExcerpt(query,pageable);
    }


    public Page<Post> paginationAndSorting(Integer page,Integer pageSize,String sortField,String sortDir){
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(page, pageSize,sort);
        Page<Post> postPage = postRepo.findAll(pageable);

        return postPage;
    }

    public Page<Post> fiterByTagsAndAuthor(List<String> tagList,List<String> authorList,Pageable pageable){
        System.out.println(tagList);
        System.out.println(authorList);
        if(authorList.isEmpty())
        {
            authorList=null;

        }
        if(tagList.isEmpty()){
            tagList=null;
        }

        return postRepo.findByTagNamesAndAuthors(tagList,authorList,pageable);
    }

}
