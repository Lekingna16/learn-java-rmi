package edu.iuh.ex02.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Address {
    private String streetAddress;
    private String city;
    private String state;
    private int potalCode;
}
