package io.mountblue.BlogApplication.repository;

import io.mountblue.BlogApplication.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {
    @Query(value = "SELECT p from Post p WHERE " +
            " p.title LIKE CONCAT('%', :query, '%') ")
//            " p.excerpt LIKE CONCAT('%', :n, '%')")
    List<Post> searchPost( String query);
    List<Post> findAllByOrderByTitleDesc();
}
