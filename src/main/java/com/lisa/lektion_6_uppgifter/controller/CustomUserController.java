package com.lisa.lektion_6_uppgifter.controller;

import com.lisa.lektion_6_uppgifter.model.CustomUser;
import com.lisa.lektion_6_uppgifter.repository.CustomUserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/user")
public class CustomUserController {

    @Autowired
    CustomUserRepository customUserRepository;

    @GetMapping("/")
    public ResponseEntity<List<CustomUser>> findAll() {

        List<CustomUser> userList = customUserRepository.findAll();

        if (userList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userList);
    }

    @PostMapping("/add")
    public ResponseEntity<CustomUser> addUser(@Valid @RequestBody CustomUser customUser) {

        // if the user already exists
        if (customUserRepository.findByUsername(customUser.getUsername()).isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        CustomUser savedUser = customUserRepository.save(customUser);
        return ResponseEntity.ok(savedUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomUser> updateUser(@PathVariable Long id, @Valid @RequestBody CustomUser customUser) {

        Optional<CustomUser> foundUser = customUserRepository.findById(id);

        if(foundUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        CustomUser userToBeUpdated = foundUser.get();

        userToBeUpdated.setUsername(customUser.getUsername());
        userToBeUpdated.setPassword(customUser.getPassword());
        userToBeUpdated.setEnabled(customUser.isEnabled());

        CustomUser updatedUser = customUserRepository.save(userToBeUpdated);

        return ResponseEntity.ok(updatedUser);

    }
}
