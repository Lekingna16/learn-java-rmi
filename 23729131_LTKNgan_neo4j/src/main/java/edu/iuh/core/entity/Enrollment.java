
package edu.iuh.core.entity;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Enrollment {
    private Student student;
    private Course course;
    private String semester;
    private int grade;
}

    