package edu.iuh;


import edu.iuh.core.dto.DepartmentDTO;
import edu.iuh.core.entity.Department;
import edu.iuh.infrastructure.mapper.GenericDataMapper;
import edu.iuh.infrastructure.mapper.impl.JacksonDataMapper;

import java.util.Map;

public class JacksonDemo3 {
    public static void main(String[] args) {

        GenericDataMapper dataMapper = new JacksonDataMapper();
        Department department = Department.builder()
                .id("FIT")
                .room("H1")
                .dean("Dr.Duy")
                .building("H")
                .name("Fit")
                .build();

        DepartmentDTO departmentDTO = dataMapper.toObject(dataMapper.toMap(department), DepartmentDTO.class);

        System.out.println(departmentDTO);
    }
}
