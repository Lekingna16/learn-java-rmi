package edu.iuh.ex02.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Person {
    private String firstName;
    private String lastName;
    private int age;
    private Address address;
    private List<PhoneNumber> phoneNumbers;




}
