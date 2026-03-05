package edu.iuh.presentation;

import edu.iuh.core.dto.StudentDTO;
import edu.iuh.core.entity.Student;
import edu.iuh.core.repository.StudentRepository;
import edu.iuh.core.service.StudentService;
import edu.iuh.core.service.impl.StudentServiceImpl;
import edu.iuh.infrastructure.db.Neo4jConnManager;
import edu.iuh.infrastructure.mapper.GenericDataMapper;
import edu.iuh.infrastructure.mapper.impl.JacksonDataMapper;
import edu.iuh.infrastructure.persistence.StudentRepositoryImpl;

public class StudentMain {
    public static void main(String[] args) {
        String url = "neo4j://127.0.0.1:7687";
        String username = "neo4j";
        String password = "12345678";
        String dbName = "learn-neo4j";

        Neo4jConnManager con = new Neo4jConnManager(url, username, password, dbName);
        GenericDataMapper dataMapper = new JacksonDataMapper();

        StudentRepository studentRepository = new StudentRepositoryImpl(con, dataMapper);
        StudentService studentService = new StudentServiceImpl(studentRepository, dataMapper);
        StudentDTO studentDTO = studentService.findStudentById("13");

        System.out.println(studentDTO);

        StudentDTO student1 = StudentDTO.builder()
                .id("23729133")
                .name("Ngan")
                .gpa(3.5)
                .build();

        boolean success = studentService.createStudent(student1);
        System.out.println(success);

    }
}
