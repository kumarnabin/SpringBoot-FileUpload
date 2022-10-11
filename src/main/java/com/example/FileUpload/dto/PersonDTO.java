package com.example.FileUpload.dto;

public class PersonDTO extends AbstractDTO<Long> {
    private Long id;
    private String name;

    public PersonDTO() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}