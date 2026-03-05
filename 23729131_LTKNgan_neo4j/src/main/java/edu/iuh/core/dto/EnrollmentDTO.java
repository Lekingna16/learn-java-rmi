
package edu.iuh.core.dto;

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
    private String studentID;
    private Course course;
    private String semester;
    private int grade;
}

    