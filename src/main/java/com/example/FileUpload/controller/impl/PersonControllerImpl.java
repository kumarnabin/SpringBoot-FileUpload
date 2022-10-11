package com.example.FileUpload.controller.impl;

import com.example.FileUpload.controller.PersonController;
import com.example.FileUpload.dto.PersonDTO;
import com.example.FileUpload.entity.Person;
import com.example.FileUpload.mapper.PersonMapper;
import com.example.FileUpload.service.PersonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/api/person")
@RestController
public class PersonControllerImpl implements PersonController {
    private final PersonService personService;
    private final PersonMapper personMapper;

    public PersonControllerImpl(PersonService personService, PersonMapper personMapper) {
        this.personService = personService;
        this.personMapper = personMapper;
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonDTO save(@RequestBody PersonDTO personDTO) {
        Person person = personMapper.asEntity(personDTO);
        return personMapper.asDTO(personService.save(person));
    }

    @Override
    @GetMapping("/{id}")
    public PersonDTO findById(@PathVariable("id") Long id) {
        Person person = personService.findById(id).orElse(null);
        return personMapper.asDTO(person);
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        personService.deleteById(id);
    }

    @Override
    @GetMapping
    public List<PersonDTO> list() {
        return personMapper.asDTOList(personService.findAll());
    }

    @Override
    @GetMapping("/page-query")
    public Page<PersonDTO> pageQuery(Pageable pageable) {
        Page<Person> personPage = personService.findAll(pageable);
        List<PersonDTO> dtoList = personPage
                .stream()
                .map(personMapper::asDTO).collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, personPage.getTotalElements());
    }

    @Override
    @PutMapping("/{id}")
    public PersonDTO update(@RequestBody PersonDTO personDTO, @PathVariable("id") Long id) {
        Person person = personMapper.asEntity(personDTO);
        return personMapper.asDTO(personService.update(person, id));
    }
}