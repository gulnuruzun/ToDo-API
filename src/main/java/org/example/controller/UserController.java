package org.example.controller;

import org.example.model.User;
import org.example.service.SessionService;
import org.example.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final SessionService sessionService;

    public UserController(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(userService.registerUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User loginRequest) {
        String sessionId = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok("Oturum açıldı! Session ID: " + sessionId);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Session-Id") String sessionId) {
        sessionService.invalidateSession(sessionId);
        return ResponseEntity.ok("Başarıyla çıkış yapıldı. Oturum sonlandırıldı.");
    }
}