package edu.iuh;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.iuh.core.dto.DepartmentDTO;
import edu.iuh.core.entity.Department;

import java.util.Map;

public class JacksonDemo2 {
    public static void main(String[] args) {

        ObjectMapper mapper = new ObjectMapper();


        Map<String, Object> map = Map.of(
                "name", "Math", "dept_id", "Math",
                "dean", "Dr.Smith", "room", "V4.02",
                "building", "V"
        );
       Department department = mapper.convertValue(map,  Department.class);
        DepartmentDTO departmentDTO = mapper.convertValue(department, DepartmentDTO.class);

        System.out.println(department);
        System.out.println(departmentDTO);

    }
}
