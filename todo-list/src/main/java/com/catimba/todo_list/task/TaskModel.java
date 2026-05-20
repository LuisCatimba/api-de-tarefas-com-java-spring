package com.catimba.todo_list.task;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name="tb_task")
public class TaskModel {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private UUID userId;
    private LocalDateTime startAt;
    private LocalDateTime endAt;

    @Column(length = 50)
    private String title;
    private String description;
    private String priority;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    public void setTitle(String title) throws Exception{
        if (title.length() > 50){
            throw new Exception("Título não pode ter mais de 50 caracteres");
        }

        this.title = title;
    }
}
