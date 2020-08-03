package com.converter.task.controllers;

import com.converter.task.models.User;
import com.converter.task.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;
/*
    @GetMapping("/")
    public String home(){
        return "index";
    }
*/
    @Autowired
    private UserService userService;

    @GetMapping("/getUser/{id}")
    public User getUser(@PathVariable Integer id){
        return userService.getUser(id);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public User register(@RequestBody String login, String password){
        User user = new User();
        login = login.substring(6, login.indexOf("&"));
        user.setLogin(login); user.setPassword(passwordEncoder.encode(password));
        return userService.saveUser(user);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody String login, String password){
        login = login.substring(6, login.indexOf("&"));
        User user = userService.findUserByLogin(login);
        if(user == null){
            return "Wrong login";
        }
        else if(passwordEncoder.matches(password, user.getPassword())){
            return "Login successful";
        }else {
            return "Wrong password";
        }
    }
}
