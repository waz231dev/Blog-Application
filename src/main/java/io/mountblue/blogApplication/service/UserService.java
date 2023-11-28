package io.mountblue.blogApplication.service;

import io.mountblue.blogApplication.entity.User;
import jakarta.persistence.criteria.CriteriaBuilder;

public interface UserService {

    User findByEmail(String email);
    void saveUser(User user);
    User findByUserName(String name);
    User findById(Integer id);
    void deleteById(Integer id);
}
