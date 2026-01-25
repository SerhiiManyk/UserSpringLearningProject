package com.manser.pr.domain;

import java.time.LocalDateTime;

public class Task {

    private Long id;

    private String title;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deadline;

    private User owner;

    private TaskStatus status;

    private Priority priority;
}
