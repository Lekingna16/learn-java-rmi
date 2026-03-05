package edu.iuh;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.iuh.core.entity.Department;

import java.util.Map;

public class JacksonDemo {
    public static void main(String[] args) {

        ObjectMapper mapper = new ObjectMapper();
        Department department = Department.builder()
                .id("FIT")
                .room("H1")
                .dean("Dr.Duy")
                .building("H")
                .name("Fit")
                .build();

//        Map<String, Object> map = Map.of(
//                "name", department.getName(), "id", department.getId(),
//                "dean", department.getDean(), "room", department.getRoom(),
//                "building", department.getBuilding()
//        );
        Map<String, Object> map = mapper.convertValue(department, Map.class);
        System.out.println(map);

    }
}
