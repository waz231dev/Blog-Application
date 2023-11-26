package io.mountblue.blogApplication.service;

import io.mountblue.blogApplication.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {

    public void createPost(String tagNames, Post post);

    public List<Post> getAllPosts();

    public void deletById(int id);

    public Post findById(int id);

    public void updatePost(int postId,String tagNames,Post post);

    public Page<Post> searchByAuthorOrExcerptOrTitleOrContentOrTag(String query, Pageable pageable);

    public Page<Post> paginationAndSorting(int page,int pageSize,String sortField,String sortDir);

    public Page<Post> fiterByTagsAndAuthor(List<String> tagList,List<String> authorList,Pageable pageable);



}
