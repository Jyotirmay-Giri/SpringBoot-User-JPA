package com.example.first.app.controller;

import com.example.first.app.model.User;
import com.example.first.app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService = new UserService();
    public UserController(UserService userService) {
        this.userService = userService;
    }



    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){

        User createdUser = userService.createUser(user);
//        System.out.println(user.getEmail());
//        userDb.putIfAbsent(user.getId(), user);
//        return ResponseEntity.status(HttpStatus.CREATED).body(user);
        return new ResponseEntity<>(createdUser,HttpStatus.CREATED);

    }
    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user){
        User updated = userService.updateUser(user);
        if(updated == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id){

        boolean isDeleted = userService.deleteUser(id);
        if(!isDeleted){
            return new ResponseEntity<>("Id Not Found",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Successfully Deleted",HttpStatus.NO_CONTENT); // deletion succesful
    }

//    @GetMapping({"/users","/user/{id}"})

    @GetMapping
    public List<User> getUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User>getUser(@PathVariable(value = "userId", required = false) int id){
        User user = userService.getUserById(id);
        if(user == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(user);
    }
    // We can access multiple pathVariable in a single url
    @GetMapping("/{userId}/orders/{orderId}")
    public ResponseEntity<User> getUserOrder
    (@PathVariable("userId") int id,
     @PathVariable int orderId) {

        User user = userService.getUserById(id);
        if(user == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(user);
    }

    // Serach / RequestParam
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam(required = false,defaultValue = "Jmg") String name,
                                                  @RequestParam(required = false,defaultValue = "email") String email)
    {
//        System.out.println(name);
        return ResponseEntity.ok(userService.searchUser(name,email));
    }

    // RequestHeader
    @GetMapping("/info/{id}")
    public String getInfo(
            @PathVariable int id,
            @RequestParam String name,
            @RequestHeader("User-Agent") String userAgent){
        return "User-Agent" + userAgent
                + ":" + id
                + ":" + name;
    }



    // Exception Handeling
//    @ExceptionHandler({IllegalArgumentException.class, NullPointerException.class})
//    public ResponseEntity<Map<String, Object>> handleIlligalArgumentException(
//            Exception exception){
//        Map<String,Object> errorResponse = new HashMap<>();
//        errorResponse.put("message",exception.getMessage());
//        errorResponse.put("timestamp", LocalDateTime.now());
//        errorResponse.put("status",HttpStatus.BAD_REQUEST);
//        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
//
//    }
}
