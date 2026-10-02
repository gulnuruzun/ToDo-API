package org.example.repository;

import org.example.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {
    private static final List<User> userList = new ArrayList<>();

    public static List<User> getUserList() {
        return userList;
    }

    public static Optional<User> findById(int id) {
        return userList.stream().filter(u -> u.getId() == id).findFirst();
    }
}