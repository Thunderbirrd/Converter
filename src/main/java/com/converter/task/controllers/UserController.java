package com.converter.task.controllers;

import com.converter.task.models.User;
import org.json.*;
import com.converter.task.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;

@RestController
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public User register(@RequestBody String userData) throws JSONException {
        User user = new User();
        JSONObject data = new JSONObject(userData);
        String login = data.getString("login");
        String password = data.getString("password");
        if(userService.findUserByLogin(login) == null) {
            user.setLogin(login);
            user.setPassword(passwordEncoder.encode(password));
            return userService.saveUser(user);
        }
        return null;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody String userData) throws JSONException, ParserConfigurationException, SAXException, ParseException, IOException {
        JSONObject data = new JSONObject(userData);
        String login = data.getString("login");
        String password = data.getString("password");
        User user = userService.findUserByLogin(login);
        if(user == null){
            return "Wrong login";
        }
        else if(passwordEncoder.matches(password, user.getPassword())){
            return user.getId().toString();
        }else {
            return "Wrong password";
        }
    }
}
