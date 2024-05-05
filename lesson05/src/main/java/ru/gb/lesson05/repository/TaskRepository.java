package ru.gb.lesson05.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.lesson05.model.Task;
import ru.gb.lesson05.model.TaskStatus;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByStatus(TaskStatus taskStatus);
}
