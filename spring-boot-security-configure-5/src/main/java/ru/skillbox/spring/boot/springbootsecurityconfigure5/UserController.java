package ru.skillbox.spring.boot.springbootsecurityconfigure5;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/me")
    public String getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        return "Вы вошли как: " + userDetails.getUsername() + ", роли: " + userDetails.getAuthorities();
    }

    @GetMapping("/encode")
    public String encodePassword(@RequestParam String password) {
        return "Хеш для пароля '" + password + "': " + passwordEncoder.encode(password);
    }
} 