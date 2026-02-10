package com.manser.pr.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "TASKS")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TASK_ID")
    private Long id;

    @NotBlank(message = "{NotEmpty.task.name}")
    @Size(max = 100, message = "{Size.task.name}")
    @Column(name = "TASK_NAME", length = 100, nullable = false)
    private String title;

    @NotBlank(message = "{NotEmpty.task.description}")
    @Size(max = 500, message = "{Size.task.description}")
    @Column(name = "DESCRIPTION", length = 500, nullable = false)
    private String description;

    @Column(name = "CREATE_TIME", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATE_TIME", nullable = true)
    private LocalDateTime updatedAt;

    @Column(name = "DEADLINE", nullable = false)
    private LocalDateTime deadline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User owner;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "PRIORITY", nullable = false)
    private Priority priority;

    @PrePersist
    void initializeCreatedAtAndDeadline() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (deadline == null) {
            deadline = createdAt.plusDays(14);
        }
    }

    @PreUpdate
    void updateTimestamp() {
        updatedAt = LocalDateTime.now();
    }

    public Task() {
    }

    public Task(String title, String description, TaskStatus status, Priority priority) {

        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }


    public String getCreatedAtFormatted() {
        return createdAt != null
                ? createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                : "";
    }

    public String getUpdatedAtFormatted() {
        return updatedAt != null
                ? updatedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                : "";
    }

    public String getDeadlineFormatted() {
        return deadline != null
                ? deadline.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                : "";
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", priority=" + priority +
                '}';
    }
}
