package com.delacasa.jwt.controller;

import com.delacasa.jwt.entity.ApiUser;
import com.delacasa.jwt.service.ApiUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController

@RequiredArgsConstructor
public class ApiUserController {


    private final ApiUserService apiUserService;

    @GetMapping("/users")
    public ResponseEntity<Object> getAll() {
        try {
            final List<ApiUser> data = apiUserService.findAll();
            return new ResponseEntity<>(data, OK);
        } catch (Exception e) {
            //Peut aussi être gérer autrement de façon à couvrir toute l'api.
            return new ResponseEntity<>(e, INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/sign-up")
    //Préférable d'utiliser un DTO.
    public ResponseEntity<Object> signUp(@RequestBody ApiUser apiUser) {
        try {
            final ApiUser data = apiUserService.signUp(apiUser);
            return new ResponseEntity<>(data, CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e, INTERNAL_SERVER_ERROR);
        }
    }


}
