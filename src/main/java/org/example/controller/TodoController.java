package org.example.controller;

import org.example.model.Todo;
import org.example.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public ResponseEntity<List<Todo>> getTodos(@RequestHeader("X-User-Id") String userId) {
        return ResponseEntity.ok(todoService.getTodosByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestHeader("X-User-Id") String userId, @RequestBody Todo todo) {
        return ResponseEntity.ok(todoService.createTodo(userId, todo));
    }

    @PatchMapping("/{id}/toggle")
    public ResponseEntity<Todo> toggleTodo(@RequestHeader("X-User-Id") String userId, @PathVariable int id) {
        Todo updatedTodo = todoService.toggleTodoStatus(userId, id);
        return ResponseEntity.ok(updatedTodo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTodo(@RequestHeader("X-User-Id") String userId, @PathVariable int id) {
        todoService.deleteTodo(userId, id);
        return ResponseEntity.ok("Todo başarıyla silindi.");
    }
}