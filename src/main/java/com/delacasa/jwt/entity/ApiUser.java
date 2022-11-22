package com.delacasa.jwt.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class ApiUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(unique = true)
    @Size(min = 4, max = 255, message = "must have between ${min} and ${max} characters")
    private String username;

    private String password;

    @Column(unique = true)
    @Email(message = "[${validatedValue}] is not a valid email")
    private String email;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "role_id")
    private ApiRole role;


}
