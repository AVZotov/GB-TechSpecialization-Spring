package ru.gb.lesson05.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.lesson05.exception.EmptyFieldException;
import ru.gb.lesson05.exception.ResourceNotFoundException;
import ru.gb.lesson05.model.Task;
import ru.gb.lesson05.model.TaskInsertRequest;
import ru.gb.lesson05.model.TaskStatus;
import ru.gb.lesson05.repository.TaskRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    public Task addNewTask(TaskInsertRequest insertRequest){
        if (insertRequest.description().isBlank()){
            throw new EmptyFieldException("description can't be empty");
        }

        Task task = new Task(insertRequest.description());
        taskRepository.save(new Task(insertRequest.description()));

        return task;
    }

    public void deleteById(Long id) {
        if (!taskRepository.existsById(id)){
            throw new ResourceNotFoundException("No tasks found with id: [%d]".formatted(id));
        }

        taskRepository.deleteById(id);
    }

    public List<Task> getTasksByStatus(TaskStatus status) {
        List<Task> filteredTasks = taskRepository.findAllByStatus(status);

        if (filteredTasks.isEmpty()){
            throw new ResourceNotFoundException("No tasks found with status: [%s]".formatted(status));
        }

        return filteredTasks;
    }

    public Task updateTaskStatus(Long id, TaskStatus newStatus) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No tasks found with id: [%d]".formatted(id)));

        task.setStatus(newStatus);
        taskRepository.save(task);

        return task;
    }
}
