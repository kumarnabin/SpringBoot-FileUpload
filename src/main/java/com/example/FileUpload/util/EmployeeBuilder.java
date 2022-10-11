package com.example.FileUpload.util;

import com.example.FileUpload.dto.EmployeeDTO;
import com.example.FileUpload.dto.PersonDTO;
import com.example.FileUpload.entity.Employee;
import com.example.FileUpload.entity.Person;

import java.util.Arrays;
import java.util.List;

public class EmployeeBuilder {
    public static List<EmployeeDTO> getListDTO() {

        return Arrays.asList(new EmployeeDTO(), new EmployeeDTO());
    }

    public static Employee getEntity() {
        return new Employee(1L,50000,new Person(1L, "Admin"));
    }

    public static EmployeeDTO getDTO() {
        return new EmployeeDTO();
    }

    public static List<Employee> getListEntities() {
        return Arrays.asList(new Employee(1L,50000,new Person(1L, "Admin")), new Employee(2L,50000,new Person(2L, "User")));
    }
}
