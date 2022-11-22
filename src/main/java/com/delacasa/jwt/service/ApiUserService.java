package com.delacasa.jwt.service;

import com.delacasa.jwt.entity.ApiUser;
import com.delacasa.jwt.repo.ApiUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApiUserService {

    private final ApiUserRepo apiUserRepo;
    private final PasswordEncoder passwordEncoder;

    public List<ApiUser> findAll() {
        return apiUserRepo.findAll();
    }

    public Optional<ApiUser> findByUsername(String username) {
        return apiUserRepo.findByUsername(username);
    }

    public ApiUser signUp(ApiUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println(user);
        return apiUserRepo.save(user);
    }


}
