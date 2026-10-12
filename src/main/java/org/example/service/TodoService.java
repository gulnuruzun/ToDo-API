package org.example.service;

import org.example.model.Todo;
import org.example.model.User;
import org.example.repository.TodoRepository;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    private final UserRepository userRepository;
    private final TodoRepository todoRepository;

    public TodoService(UserRepository userRepository, TodoRepository todoRepository) {
        this.userRepository = userRepository;
        this.todoRepository = todoRepository;
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

        Todo todo = new Todo();
        todo.setUserId(user.getId());
        todo.setTitle(title);
        todo.setCompleted(false);

        return todoRepository.save(todo);
    }

    public List<Todo> getTodosByUser(String username, String password) {
        User user = authenticate(username, password);

        return todoRepository.findByUserId(user.getId());
    }
}