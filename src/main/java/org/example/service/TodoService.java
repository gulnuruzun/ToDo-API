package org.example.service;

import org.example.model.Todo;
import org.example.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> getTodosByUserId(UUID userId) {
        return todoRepository.findByUserId(userId);
    }

    public Todo createTodo(UUID userId, Todo todo) {
        todo.setUserId(userId);
        return todoRepository.save(todo);
    }

    public Todo toggleTodoStatus(UUID userId, int id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo bulunamadı, ID: " + id));

        if (!todo.getUserId().equals(userId)) {
            throw new RuntimeException("Bu todo üzerinde işlem yapma yetkiniz yok!");
        }

        todo.setCompleted(!todo.isCompleted());
        return todoRepository.save(todo);
    }

    public void deleteTodo(UUID userId, int id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo bulunamadı, ID: " + id));

        if (!todo.getUserId().equals(userId)) {
            throw new RuntimeException("Bu todo'yu silme yetkiniz yok!");
        }

        todoRepository.deleteById(id);
    }
}