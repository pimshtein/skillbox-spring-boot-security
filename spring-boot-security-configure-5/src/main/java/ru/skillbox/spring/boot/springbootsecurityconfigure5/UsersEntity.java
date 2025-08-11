package ru.skillbox.spring.boot.springbootsecurityconfigure5;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsersEntity {
    Long id;
    String username;
    String password;
    String role;
} 