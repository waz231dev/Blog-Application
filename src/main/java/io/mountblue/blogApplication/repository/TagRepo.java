package io.mountblue.blogApplication.repository;

import io.mountblue.blogApplication.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepo extends JpaRepository<Tag,Integer> {

    Tag findByName(String name);
}
