package com.osla.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.exceptions.UserAlreadyExists;
import com.exceptions.UserNotFoundException;
import com.osla.model.User;
import com.osla.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<User> getUsers() {
        return userRepository.findAll();
    }


    public User getUser(int id) {
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()) throw new UserNotFoundException("Couldn't find user with id " + id);
        return user.get();
    }

    public User getUser(String username) {
        User user = userRepository.findByUsername(username);
        return user;
    }

    @Transactional
    public void addUser(User user) {
        if(userRepository.findByUsername(user.getUsername()) != null)
            throw new UserAlreadyExists("User with name " + user.getUsername() + " already exists");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    public boolean authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            throw new UserNotFoundException("Couldn't find user with name " + username);
        }

        if(bCryptPasswordEncoder.matches(password, user.getPassword()))
            return true;
        else
            return false;
    }
}
