package com.catimba.todo_list.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserRepository iUserRepository;

    @PostMapping
    public ResponseEntity create(@RequestBody UserModel userModel){
        UserModel user = this.iUserRepository.findByUsername(userModel.getUsername());

        if (user != null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuário já existe!");
        }

        String password_hash = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());

        userModel.setPassword(password_hash);

        UserModel createdUser = this.iUserRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.OK).body(createdUser);
    }
}
