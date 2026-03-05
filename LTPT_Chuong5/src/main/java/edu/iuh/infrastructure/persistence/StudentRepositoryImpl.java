package edu.iuh.infrastructure.persistence;

import edu.iuh.core.entity.Student;
import edu.iuh.core.repository.StudentRepository;
import edu.iuh.infrastructure.db.Neo4jConnManager;
import edu.iuh.infrastructure.mapper.GenericDataMapper;
import edu.iuh.infrastructure.mapper.impl.JacksonDataMapper;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.types.Node;

import java.util.List;
import java.util.Map;

public class StudentRepositoryImpl implements StudentRepository {
    private Neo4jConnManager connManager; // lam viec voi neo4j nen khai bao
    private GenericDataMapper mapper; // de chuyen du lieu tu tang database thanh entity

    public StudentRepositoryImpl(Neo4jConnManager connManager, GenericDataMapper mapper) {
        this.connManager = connManager;
        this.mapper = mapper;
    }

    @Override
    public boolean createStudent(Student student) {
        String cypher = "CREATE (s:Student {studentID: $studentID, name: $name, gpa: $gpa})";
        Map<String, Object> params = mapper.toMap(student);

        try (Session session = connManager.openSession()){
            return session.executeWrite(tx -> {
                Result result = tx.run(cypher, params);
                return result.consume().counters().nodesCreated() > 0; // result.consume() -> ResultSummary
            });
        }

    }

    @Override
    public boolean updateStudent(Student student) {

        String cypher = "MATCH (student: Student {studentID: $studentID}) SET student.name = $name, student.gpa = $gpa";
        Map<String, Object> params = mapper.toMap(student);

        try (Session session = connManager.openSession()){
            return session.executeWrite( tx -> {
                Result result = tx.run(cypher, params);
                return result.consume().counters().propertiesSet() > 0;
            });
        }


    }

    @Override
    public boolean deleteStudent(Student student) {
        String cypher = "MATCH (s : Student {studentID: $studentID}) DETACH DELETE s";
        Map<String, Object> params = Map.of ("studentID", student.getId());

        try (Session session = connManager.openSession()){
            return session.executeWrite(tx -> {
                Result result = tx.run(cypher, params);
                return result.consume().counters().nodesDeleted() > 0;
            });
        }
    }

    @Override
    public Student findStudentById(String studentId) {
        String cypher = "MATCH (n:Student {studentID: $student_id}) RETURN n";
        Map<String, Object> params = Map.of ("student_id", studentId);

        try (Session session = connManager.openSession()) {
            return session.executeRead(tx -> {
                Result result = tx.run(cypher, params);
                if (result.hasNext()){
                    Node node = result.single().get("n").asNode();
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

        Neo4jConnManager con = new Neo4jConnManager(url, username, password, dbName);
        GenericDataMapper dataMapper = new JacksonDataMapper();

        StudentRepository studentRepository = new StudentRepositoryImpl(con, dataMapper);
        Student student = studentRepository.findStudentById("13");
        System.out.println(student);

        Student student1 = Student.builder()
                .id("23729131")
                .name("NganKim")
                .gpa(3.6)
                .build();

        boolean result = studentRepository.deleteStudent(student1);
        System.out.println(result);
    }
}
