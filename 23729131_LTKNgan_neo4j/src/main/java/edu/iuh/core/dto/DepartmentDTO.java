package edu.iuh.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@JsonIgnoreProperties()
public class DepartmentDTO {
    @JsonProperty("dept_id")
    private String id;
    private String name;
    private String dean;
    private String building;
    private String room;
}
