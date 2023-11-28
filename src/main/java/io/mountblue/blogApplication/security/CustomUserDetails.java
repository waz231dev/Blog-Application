package io.mountblue.blogApplication.security;

import io.mountblue.blogApplication.entity.User;
import io.mountblue.blogApplication.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetails implements UserDetailsService {
    @Autowired
    UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findByUsername(username);
        if(user==null){
            throw  new UsernameNotFoundException("User not found!!!!!!");
        }
        return new CustomUser(user);

    }
}
