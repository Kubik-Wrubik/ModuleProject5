package com.kubik.controller;

import com.kubik.entity.Status;
import lombok.Data;

@Data
public class TaskInfo {
    private String description;
    private Status status;
}
