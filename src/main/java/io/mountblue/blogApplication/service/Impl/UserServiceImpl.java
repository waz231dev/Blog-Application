package io.mountblue.blogApplication.service.Impl;

import io.mountblue.blogApplication.entity.Role;
import io.mountblue.blogApplication.entity.User;
import io.mountblue.blogApplication.repository.RoleRepo;
import io.mountblue.blogApplication.repository.UserRepo;
import io.mountblue.blogApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;

    private RoleRepo roleRepo;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImpl(UserRepo userRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByEmail(String email) {

        return userRepo.findByEmail(email);
    }

    @Override
    public void saveUser(User user) {
        String password=passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        userRepo.save(user);
        String userName=user.getUsername();
        Role role = new Role();
        role.setUserName(userName);
        role.setName("ROLE_AUTHOR");
        roleRepo.save(role);
    }

    @Override
    public User findByUserName(String name) {

        return   userRepo.findByUsername(name);
    }

    @Override
    public User findById(Integer id) {
       return userRepo.findById(id).get();
    }

    @Override
    public void deleteById(Integer id) {
        userRepo.deleteById(id);
    }

}
