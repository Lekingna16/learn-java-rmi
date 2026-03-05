package edu.iuh.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class StudentDTO {
    @JsonProperty("studentID")
    private String id;
    private String name;
    private double gpa;
}