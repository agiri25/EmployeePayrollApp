package com.imaginnovate.DO;

import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class Employee {
    private String employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private Set<Long> phoneNumber;
    private LocalDate doj;
    private Double salary;
}
