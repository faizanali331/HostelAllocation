package com.telucko.Allocation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentDTO {
    private String name;
    private int courseId;
    private int totalPoints;
}
