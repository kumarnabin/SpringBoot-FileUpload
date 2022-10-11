package com.example.FileUpload.mapper;

import com.example.FileUpload.dto.EmployeeDTO;
import com.example.FileUpload.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ReferenceMapper.class)
public interface EmployeeMapper extends GenericMapper<Employee, EmployeeDTO> {
    @Override
    @Mapping(target = "id", ignore = false)
    Employee asEntity(EmployeeDTO dto);
}