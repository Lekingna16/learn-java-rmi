package edu.iuh;

import edu.iuh.core.dto.DepartmentDTO;
import edu.iuh.core.entity.Department;
import edu.iuh.insfractructure.mapper.GenericDataMapper;
import edu.iuh.insfractructure.mapper.impl.JacksonDataMapper;

public class JsonDemo3 {
    public static void main(String[] args) {
        GenericDataMapper mapper = new JacksonDataMapper();
        Department department = Department.builder()
                .id("FIT")
                .room("H1")
                .dean("Dr.Duy")
                .building("H")
                .name("FIT")
                .build();

        DepartmentDTO departmentDTO = mapper.toObject(mapper.toMap(department), DepartmentDTO.class);
        System.out.println(departmentDTO);

    }
}
