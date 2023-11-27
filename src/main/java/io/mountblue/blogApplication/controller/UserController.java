package io.mountblue.blogApplication.controller;

import io.mountblue.blogApplication.entity.User;
import io.mountblue.blogApplication.service.UserService;
import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }
    @GetMapping("/access-denied")
    public  String error(){
        return "error";
    }

    @GetMapping("/signup")
    public  String signupPage(Model model){
        model.addAttribute("user",new User());
        return "signup";

    }
    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user){

          userService.saveUser(user);
        return "login";
    }

}
