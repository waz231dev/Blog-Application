package io.mountblue.blogApplication.service;

import io.mountblue.blogApplication.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {

    public void createPost(String tagNames, Post post);

    public List<Post> getAllPosts();

    public void deletById(Integer postId);

    public Post findById(Integer postId);

    public void updatePost(Integer postId,String tagNames,Post post);

    public Page<Post> searchByAuthorOrExcerptOrTitleOrContentOrTag(String query, Pageable pageable);

    public Page<Post> paginationAndSorting(Integer page,Integer pageSize,String sortField,String sortDir);

    public Page<Post> fiterByTagsAndAuthor(List<String> tagList,List<String> authorList,Pageable pageable);



}
