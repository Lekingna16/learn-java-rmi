package edu.iuh.core.service;

import edu.iuh.core.dto.StudentDTO;
import edu.iuh.core.entity.Student;

public interface StudentService {

    boolean createStudent (StudentDTO studentDTO);
    StudentDTO findStudentById (String studentId);


}
