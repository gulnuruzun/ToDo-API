package org.example.controller;

import org.example.model.Todo;
import org.example.service.SessionService;
import org.example.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;
    private final SessionService sessionService;

    public TodoController(TodoService todoService, SessionService sessionService) {
        this.todoService = todoService;
        this.sessionService = sessionService;
    }

    @GetMapping
    public ResponseEntity<List<Todo>> getTodos(@RequestHeader("Session-Id") String sessionId) {
        UUID userId = sessionService.getUserIdBySession(sessionId);
        return ResponseEntity.ok(todoService.getTodosByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestHeader("Session-Id") String sessionId, @RequestBody Todo todo) {
        UUID userId = sessionService.getUserIdBySession(sessionId);
        return ResponseEntity.ok(todoService.createTodo(userId, todo));
    }

    @PatchMapping("/{id}/toggle")
    public ResponseEntity<Todo> toggleTodo(@RequestHeader("Session-Id") String sessionId, @PathVariable int id) {
        UUID userId = sessionService.getUserIdBySession(sessionId);
        return ResponseEntity.ok(todoService.toggleTodoStatus(userId, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTodo(@RequestHeader("Session-Id") String sessionId, @PathVariable int id) {
        UUID userId = sessionService.getUserIdBySession(sessionId);
        todoService.deleteTodo(userId, id);
        return ResponseEntity.ok("Todo başarıyla silindi.");
    }
}