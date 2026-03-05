package edu.iuh.core.repository;

import edu.iuh.core.entity.Student;

import java.util.List;

public interface StudentRepository {

    boolean createStudent (Student student);
    boolean updateStudent (Student student);
    boolean deleteStudent (Student student);
    Student findStudentById (String studentId);
    List<Student> getStudents (int page, int size);
}
