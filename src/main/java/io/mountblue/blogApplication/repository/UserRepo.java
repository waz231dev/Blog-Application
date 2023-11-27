package io.mountblue.blogApplication.repository;

import io.mountblue.blogApplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Integer> {

   User findByEmail(String email);
   User findByUsername(String name);
}
