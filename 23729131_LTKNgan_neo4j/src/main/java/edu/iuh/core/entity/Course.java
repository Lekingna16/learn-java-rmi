package edu.iuh.core.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Course {
    @JsonProperty("course_id")
    private String id;
    private String name;
    private int hours;
    private Department department;
}