package com.example.FileUpload.dto;

import com.example.FileUpload.entity.Person;

public class EmployeeDTO extends AbstractDTO<Long> {
    private Long id;
    private float salary;
    private Person person;

    public EmployeeDTO() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public float getSalary() {
        return this.salary;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return this.person;
    }
}