package edu.iuh.core.service.impl;

import edu.iuh.core.dto.StudentDTO;
import edu.iuh.core.entity.Student;
import edu.iuh.core.repository.StudentRepository;
import edu.iuh.core.service.StudentService;
import edu.iuh.infrastructure.mapper.GenericDataMapper;
import edu.iuh.infrastructure.persistence.StudentRepositoryImpl;

public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;
    private GenericDataMapper mapper;

    public StudentServiceImpl(StudentRepository studentRepository, GenericDataMapper mapper) {
        this.studentRepository = studentRepository;
        this.mapper = mapper;
    }

    @Override
    public boolean createStudent(StudentDTO studentDTO) {
        //1. Business rules validation
        if (studentDTO == null || studentDTO.getId() == null || studentDTO.getId().isBlank())
            throw new IllegalArgumentException("Id must be not null");
        if (studentDTO.getGpa() < 0 || studentDTO.getGpa() > 4)
            throw new IllegalArgumentException("GPA must be between 0.0 to 4.0");

        // 2. Convert from DTO to entity

        Student student = mapper.toObject(mapper.toMap(studentDTO), Student.class);

        //3. Call responsitory
        return studentRepository.createStudent(student);

    }

    @Override
    public StudentDTO findStudentById(String studentId) {

        if (studentId == null || studentId.isBlank()) {
            throw  new IllegalArgumentException("ID must be not null");
        }
        Student student =studentRepository.findStudentById(studentId);

        return mapper.toObject(mapper.toMap(student), StudentDTO.class);

    }
}
