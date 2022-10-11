package com.example.FileUpload.mapper;

import com.example.FileUpload.dto.PersonDTO;
import com.example.FileUpload.entity.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ReferenceMapper.class)
public interface PersonMapper extends GenericMapper<Person, PersonDTO> {
    @Override
    @Mapping(target = "id", ignore = false)
    Person asEntity(PersonDTO dto);
}