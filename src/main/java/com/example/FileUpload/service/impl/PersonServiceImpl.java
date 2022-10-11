package com.example.FileUpload.service.impl;

import com.example.FileUpload.dao.PersonRepository;
import com.example.FileUpload.dto.PersonDTO;
import com.example.FileUpload.entity.Person;
import com.example.FileUpload.mapper.PersonMapper;
import com.example.FileUpload.service.PersonService;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {
    private final PersonRepository repository;

    public PersonServiceImpl(PersonRepository repository) {
        this.repository = repository;
    }

    @Override
    public Person save(Person entity) {
        return repository.save(entity);
    }

    @Override
    public List<Person> save(List<Person> entities) {
        return (List<Person>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Person> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Person> findAll() {
        return (List<Person>) repository.findAll();
    }

    @Override
    public Page<Person> findAll(Pageable pageable) {
        Page<Person> entityPage = repository.findAll(pageable);
        List<Person> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public Person update(Person entity, Long id) {
        Optional<Person> optional = findById(id);
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }
}