package ru.gb.lesson05.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.gb.lesson05.model.Task;
import ru.gb.lesson05.model.TaskInsertRequest;
import ru.gb.lesson05.model.TaskStatus;
import ru.gb.lesson05.service.TaskService;

import java.util.List;

@RestController
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/add")
    public Task insertNewTask(@RequestBody TaskInsertRequest insertRequest){
        return taskService.addNewTask(insertRequest);
    }

    @GetMapping("/tasks")
    public List<Task> getAllTasks(){
        return taskService.getAllTasks();
    }

    @GetMapping("/status/{status}")
    public List<Task> getTasksByStatus(@PathVariable("status") TaskStatus status){
        return taskService.getTasksByStatus(status);
    }

    @PutMapping("/status/{id}/{newStatus}")
    public Task updateTaskStatus(@PathVariable("id") Long id, @PathVariable("newStatus") TaskStatus newStatus){
        return taskService.updateTaskStatus(id, newStatus);
    }

    @DeleteMapping("/tasks/{id}")
    public void deleteTask(@PathVariable("id") Long id){
        taskService.deleteById(id);
    }
}
