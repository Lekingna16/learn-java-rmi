package edu.iuh.core.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Department {
    @JsonProperty("dept_id")
    private String id;
    private String name;
    private String dean;
    private String building;
    private String room;
}
