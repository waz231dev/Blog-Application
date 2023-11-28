package io.mountblue.blogApplication.service.Impl;


import io.mountblue.blogApplication.entity.User;
import io.mountblue.blogApplication.repository.UserRepo;
import io.mountblue.blogApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
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
        user.setRole("ROLE_AUTHOR");
        userRepo.save(user);

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
