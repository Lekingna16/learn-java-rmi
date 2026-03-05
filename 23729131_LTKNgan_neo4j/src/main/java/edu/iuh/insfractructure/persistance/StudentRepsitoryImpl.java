package edu.iuh.insfractructure.persistance;

import com.fasterxml.jackson.databind.annotation.NoClass;
import edu.iuh.core.entity.Student;
import edu.iuh.core.resposity.StudentRepository;
import edu.iuh.insfractructure.db.Neo4jConnection;
import edu.iuh.insfractructure.mapper.GenericDataMapper;
import edu.iuh.insfractructure.mapper.impl.JacksonDataMapper;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.types.Node;

import java.util.List;
import java.util.Map;

public class StudentRepsitoryImpl implements StudentRepository {
    private Neo4jConnection connection;
    private GenericDataMapper mapper;

    public StudentRepsitoryImpl(Neo4jConnection connection, GenericDataMapper mapper) {
        this.connection = connection;
        this.mapper = mapper;
    }

    @Override
    public boolean createStudent(Student student) {
        return false;

    }

    @Override
    public boolean updateStudent(Student student) {
        return false;
    }

    @Override
    public boolean deleteStudent(String studentId) {
        return false;
    }

    @Override
    public Student findStudentById(String studentId) {
        String cyper = "MATCH (n:Student{student_id:$student_id}) RETURN n";
        Map<String, Object> params = Map.of("student_id", studentId);

        try (Session session = connection.openSession()){
            return session.executeRead(tx -> {
                Result result = tx.run(cyper, params);
               if (result.hasNext()){
                   Node node = result.single()
                           .get("n").asNode();

                   Map<String, Object> map = node.asMap();
                   return mapper.toObject(map, Student.class);

               }
               return null;
            });
        }


    }

    @Override
    public List<Student> getStudents(int page, int size) {
        return List.of();
    }

    public static void main(String[] args) {
        String url = "neo4j://127.0.0.1:7687";
        String username = "neo4j";
        String password = "12345678";

        String dbName = "learn-neo4j";
        Neo4jConnection conn = new Neo4jConnection(url,username, password, dbName );
        GenericDataMapper dataMapper = new JacksonDataMapper();

        StudentRepository studentRepository = new StudentRepsitoryImpl(conn, dataMapper);

        Student student = studentRepository.findStudentById("13");
        System.out.println(student);
    }
}
