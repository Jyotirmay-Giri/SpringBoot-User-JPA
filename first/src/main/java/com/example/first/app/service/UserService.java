package com.example.first.app.service;

import com.example.first.app.Repository.UserRepository;
import com.example.first.app.controller.UserController;
import com.example.first.app.exception.UserNotFoundException;
import com.example.first.app.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //    private Map<Integer, User> userDb = new HashMap<>();
//    Creating instance for logger for adding logs
    private final Logger logger = LoggerFactory.getLogger(this.toString());

    public User createUser(User user) {

        logger.info("CREATING USER....................INFO");
        logger.error("Checking for................... Error");
        logger.trace("Checking for ..................... Trace");
        logger.debug("User email added.........DEBUG " + user.getEmail());
        System.out.println(user.getEmail());

//        userDb.putIfAbsent(user.getId(),user);
        return userRepository.save(user);
    }

    public User updateUser(User user){

        User existing  = userRepository.findById(user.getId())
                .orElseThrow(
                        () -> new UserNotFoundException
                                ("User with ID " + user.getId() + " does not Exist"));
        existing.setName(user.getName());
        existing.setEmail(user.getEmail());
        return userRepository.save(existing);

    }

    public boolean deleteUser(int id) {
        if(!userRepository.existsById(id)){
            throw new UserNotFoundException("User with ID " + id + " does not Exist");
        }
        userRepository.deleteById(id);
        return true;
    }

    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        if(users.isEmpty()){
            throw new NullPointerException("No Users Found in the DB");
        }
        return users;
    }

    public User getUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(
                () -> new UserNotFoundException
                        ("User with ID " + id + " does not Exist"));
    }

    public List<User> searchUser(String name, String email) {
//        System.out.println(name);
        return userRepository.findByNameIgnoreCaseAndEmailIgnoreCase(name,email);
    }
}
