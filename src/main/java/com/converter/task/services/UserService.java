package com.converter.task.services;

import com.converter.task.models.User;
import com.converter.task.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Transactional
    public User saveUser(User user){
        userRepo.save(user);
        return userRepo.findById(user.getId()).orElse(null);
    }

    @Transactional
    public User findUserByLogin(String login){
        return userRepo.findUserByLogin(login);
    }
}
