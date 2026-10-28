package org.example.service;

import org.example.model.Todo;
import org.example.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> getTodosByUserId(String userId) {
        return todoRepository.findByUserId(userId);
    }

    public Todo createTodo(String userId, Todo todo) {
        todo.setUserId(userId); // Todo'yu doğrudan istek atan kullanıcının UUID'si ile ilişkilendiriyoruz
        return todoRepository.save(todo);
    }

    public Todo toggleTodoStatus(String userId, int id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo bulunamadı, ID: " + id));

        // Başkasının todiosu üzerinde işlem yapmasını engelliyoruz
        if (!todo.getUserId().equals(userId)) {
            throw new RuntimeException("Bu todo üzerinde işlem yapma yetkiniz yok!");
        }

        todo.setCompleted(!todo.isCompleted());
        return todoRepository.save(todo);
    }

    public void deleteTodo(String userId, int id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo bulunamadı, ID: " + id));


        if (!todo.getUserId().equals(userId)) {
            throw new RuntimeException("Bu todo'yu silme yetkiniz yok!");
        }

        todoRepository.deleteById(id);
    }
}