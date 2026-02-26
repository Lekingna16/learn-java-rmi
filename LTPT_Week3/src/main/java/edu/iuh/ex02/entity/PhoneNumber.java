package edu.iuh.ex02.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor


public class PhoneNumber {
    private String type;
    private String number;
}
