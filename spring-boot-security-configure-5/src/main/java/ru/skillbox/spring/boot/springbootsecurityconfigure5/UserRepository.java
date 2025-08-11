package ru.skillbox.spring.boot.springbootsecurityconfigure5;

import org.springframework.stereotype.Repository;
import java.util.Map;
import java.util.Optional;
import java.util.HashMap;

@Repository
public class UserRepository {
    
    private final Map<String, UsersEntity> users = new HashMap<>();
    
    public UserRepository() {
        users.put("admin", new UsersEntity(1L, "admin",
            "$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi", "ROLE_ADMIN"));
        users.put("user", new UsersEntity(3L, "user",
            "$2a$10$5XvZGA6dWUJv.FTDExu0iu0Uhh6WVMzpwZFGCH3wNbNvpwaHKrI0q", "ROLE_USER"));
        users.put("manager", new UsersEntity(3L, "manager",
            "$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi", "ROLE_MANAGER"));
    }
    
    public Optional<UsersEntity> findByUsername(String username) {
        return Optional.ofNullable(users.get(username));
    }
} 