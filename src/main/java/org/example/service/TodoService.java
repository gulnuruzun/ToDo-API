package org.example.service;

import org.example.model.Todo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TodoService {

    private final List<Todo> todoList = new ArrayList<>();
    private int idCounter = 1;

    public Todo addTodo(int userId, String title) {
        Todo todo = new Todo(idCounter++, userId, title, false);
        todoList.add(todo);
        return todo;
    }

    public List<Todo> getTodosByUser(int userId) {
        List<Todo> userTodos = new ArrayList<>();
        for (Todo todo : todoList) {
            if (todo.getUserId() == userId) {
                userTodos.add(todo);
            }
        }
        return userTodos;
    }
}