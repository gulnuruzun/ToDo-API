package org.example.service;

import org.example.model.Todo;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    private final List<Todo> todoList = new ArrayList<>();
    private int idCounter = 1;
    private final UserRepository userRepository;

    public TodoService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private User authenticate(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isEmpty()) {
            throw new RuntimeException("Kullanıcı bulunamadı: " + username);
        }

        User user = userOpt.get();

        if (user.getPassword() == null || !user.getPassword().equals(password)) {
            throw new RuntimeException("Hatalı şifre!");
        }

        return user;
    }

    public Todo addTodo(String username, String password, String title) {
        User user = authenticate(username, password);

        Todo todo = new Todo(idCounter++, user.getId(), title, false);
        todoList.add(todo);
        return todo;
    }

    public List<Todo> getTodosByUser(String username, String password) {
        User user = authenticate(username, password);

        List<Todo> userTodos = new ArrayList<>();
        for (Todo todo : todoList) {
            if (todo.getUserId() == user.getId()) {
                userTodos.add(todo);
            }
        }
        return userTodos;
    }
}