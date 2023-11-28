package io.mountblue.blogApplication.repository;

import io.mountblue.blogApplication.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,Integer> {

    Role findByName(String role);
    Role findByUserName(String name);
}
