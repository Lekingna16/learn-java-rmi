
package edu.iuh.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.iuh.core.entity.Course;
import edu.iuh.core.entity.Student;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class EnrollmentDTO {
    @JsonProperty("student_id")
    private String studentID;
    @JsonProperty("course_id")
    private String courseID;
    private String semester;
    private int grade;
}

    