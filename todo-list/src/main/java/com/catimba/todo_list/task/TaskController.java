package com.catimba.todo_list.task;

import com.catimba.todo_list.user.IUserRepository;
import com.catimba.todo_list.utils.CopyNonNullProperties;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private ITaskRepository iTaskRepository;

    @Autowired
    private IUserRepository iUserRepository;

    @PostMapping
    public ResponseEntity save(@RequestBody TaskModel taskModel, HttpServletRequest request){

        LocalDateTime currentDate = LocalDateTime.now();

        if (taskModel.getStartAt().isBefore(currentDate))
            return ResponseEntity.status(400).body("Data de início menor que data actual.");

        if (taskModel.getEndAt().isBefore(taskModel.getStartAt()))
            return ResponseEntity.status(400).body("Data de término menor que data de início.");

        taskModel.setUserId((UUID) request.getAttribute("user_id"));

        TaskModel createdTask = this.iTaskRepository.save(taskModel);

        return ResponseEntity.status(HttpStatus.OK).body(createdTask);
    }

    @GetMapping
    public ResponseEntity findAllById(HttpServletRequest request){

       UUID userId = (UUID) request.getAttribute("user_id");

        List<TaskModel> tasks = this.iTaskRepository.findByUserId(userId);

        return ResponseEntity.status(200).body(tasks);
    }

    @PutMapping("/{id}")
    public ResponseEntity update (@PathVariable UUID id, HttpServletRequest request, @RequestBody TaskModel taskModel){

        TaskModel task = iTaskRepository.findById(id).orElse(null);

        if (task == null)
            return ResponseEntity.status(404).body("Tarefa não encontrada!");

        if (!task.getUserId().equals(request.getAttribute("user_id")))
            return ResponseEntity.status(401).body("Acesso negado");

        task = CopyNonNullProperties.execute(taskModel, task);

        TaskModel taskCreated = this.iTaskRepository.save(task);

        return ResponseEntity.status(200).body(taskCreated);
    }
}
