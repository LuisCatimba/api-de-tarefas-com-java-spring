package com.catimba.todo_list.task;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ITaskRepository extends JpaRepository<TaskModel, UUID> {
    public List<TaskModel> findByUserId(UUID id);
}
