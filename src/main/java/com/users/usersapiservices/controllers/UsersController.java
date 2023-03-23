package com.users.usersapiservices.controllers;

import com.users.usersapiservices.entities.Users;
import com.users.usersapiservices.services.UsersService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/users")

public class UsersController {

    private UsersService usersService;
    @Autowired
    public UsersController(UsersService service) {
        usersService = service;
    }
    @GetMapping()
    public ResponseEntity<List<Users>> getUsers() {
        var users = usersService.getUsers();
        if (users.size() == 0)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return ResponseEntity.ok(users);
    }
    @GetMapping("/name")
    public ResponseEntity<Users> getUserByName(@RequestParam("username") String username) {
        if (username == null)
            return ResponseEntity.badRequest().build();
        if (!usersService.getUserByUsername(username).isPresent())
            return ResponseEntity.notFound().build();
        Users user = usersService.getUserByUsername(username).get();
        return ResponseEntity.ok(user);
    }
    @PostMapping()
    public ResponseEntity<?> createUsers(@RequestBody Users user) {
        if (user == null)
            return ResponseEntity.badRequest().build();

        usersService.createUsers(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/search")
    public ResponseEntity<List<Users>> searchUsers(@RequestParam("search") String search) {
        var users = usersService.getUsersSearched(search);
        if (users.size() == 0)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return ResponseEntity.ok(users);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") String id, @RequestBody Users user) {
        if (id == null || id == "" || user == null)
            return ResponseEntity.badRequest().build();
        Optional<Users> target = usersService.getUser(id);
        if (!target.isPresent()) {
            return new ResponseEntity<>("user not found", HttpStatus.NOT_FOUND);
        }
        target.ifPresent(u -> {
            if (user.getUsername() != null)
                u.setUsername(user.getUsername());
            if (user.getName() != null)
                u.setName(user.getName());
            if (user.getPassword() != null)
                u.setPassword(user.getPassword());
            if (user.getLastname() != null)
                u.setLastname(user.getLastname());
            if (user.getCreated() != null)
                u.setCreated(user.getCreated());
            u.setId(id);
            usersService.updateUser(u);
        });

        return new ResponseEntity<>(HttpStatus.CREATED);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") String id) {
        if (id == null || id == "")
            return ResponseEntity.badRequest().build();

        if (!usersService.getUser(id).isPresent())
            return ResponseEntity.notFound().build();

        Users user = usersService.getUser(id).get();
        usersService.deleteUser(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getUser(@PathVariable("id") String id) {
        if (id == null || id == "")
            return ResponseEntity.badRequest().build();

        if (!usersService.getUser(id).isPresent())
            return ResponseEntity.notFound().build();

        Users user = usersService.getUser(id).get();
        return ResponseEntity.ok(user);
    }

}
