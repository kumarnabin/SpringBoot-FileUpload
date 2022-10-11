package com.example.FileUpload.util;

import com.example.FileUpload.dto.PersonDTO;
import com.example.FileUpload.entity.Person;

import java.util.Arrays;
import java.util.List;

public class PersonBuilder {
    public static List<PersonDTO> getListDTO() {

        return Arrays.asList(new PersonDTO(), new PersonDTO());
    }

    public static Person getEntity() {
        return new Person(1L, "Admin");
    }

    public static PersonDTO getDTO() {
        return new PersonDTO();
    }

    public static List<Person> getListEntities() {
        return Arrays.asList(new Person(1L, "Admin"), new Person(2L, "User"));
    }
}
