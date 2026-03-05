package edu.iuh.core.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import edu.iuh.core.entity.Department;
import lombok.*;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CourseDTO {
    @JsonProperty("course_id")
    private String id;
    private String name;
    private int hours;
    private String deptId;
    private String deptName;
}