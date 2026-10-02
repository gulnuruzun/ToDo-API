package org.example.controller;

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
    public ResponseEntity<Todo> addTodo(@RequestParam int userId, @RequestParam String title) {
        Todo createdTodo = todoService.addTodo(userId, title);
        return ResponseEntity.ok(createdTodo);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Todo>> getTodosByUser(@PathVariable int userId) {
        List<Todo> todos = todoService.getTodosByUser(userId);
        return ResponseEntity.ok(todos);
    }
}