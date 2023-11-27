package io.mountblue.blogApplication.service;

import io.mountblue.blogApplication.entity.User;

public interface UserService {

    User findByEmail(String email);
    void saveUser(User user);
    User findByUserName(String name);
}
