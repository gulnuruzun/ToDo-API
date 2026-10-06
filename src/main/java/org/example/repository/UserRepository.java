package org.example.repository;

import org.example.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private final List<User> users = new ArrayList<>();

    public User save(User user) {
        users.add(user);
        return user;
    }

    public List<User> findAll() {
        return users;
    }

    public Optional<User> findByUsername(String username) {
        if (username == null) return Optional.empty();
        for (User user : users) {
            if (user.getUsername() != null && user.getUsername().trim().equalsIgnoreCase(username.trim())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
}