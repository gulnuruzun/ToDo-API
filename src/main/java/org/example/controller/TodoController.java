package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.example.model.Todo;
import org.example.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    @Operation(summary = "Yeni görev ekle", description = "Header'dan username ve password zorunludur.")
    public ResponseEntity<?> addTodo(
            @Parameter(description = "Kullanıcı adı", required = true) @RequestHeader String username,
            @Parameter(description = "Şifre", required = true) @RequestHeader String password,
            @RequestParam String title) {
        try {
            Todo createdTodo = todoService.addTodo(username, password, title);
            return ResponseEntity.ok(createdTodo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @GetMapping
    @Operation(summary = "Kullanıcının görevlerini listele", description = "Header'dan username ve password zorunludur.")
    public ResponseEntity<?> getTodosByUser(
            @Parameter(description = "Kullanıcı adı", required = true) @RequestHeader String username,
            @Parameter(description = "Şifre", required = true) @RequestHeader String password) {
        try {
            List<Todo> todos = todoService.getTodosByUser(username, password);
            return ResponseEntity.ok(todos);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}