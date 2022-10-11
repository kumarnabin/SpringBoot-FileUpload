package com.example.FileUpload.controller.impl;

import com.example.FileUpload.controller.impl.CustomUtils;
import com.example.FileUpload.controller.impl.PersonControllerImpl;
import com.example.FileUpload.dto.PersonDTO;
import com.example.FileUpload.entity.Person;
import com.example.FileUpload.mapper.PersonMapper;
import com.example.FileUpload.mapper.ReferenceMapper;
import com.example.FileUpload.service.PersonService;
import com.example.FileUpload.util.PersonBuilder;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class PersonControllerImplTest {
    //TODO: create the data Test generator class PersonBuilder
    private static final String ENDPOINT_URL = "/persons";
    @MockBean
    private ReferenceMapper referenceMapper;
    @InjectMocks
    private PersonControllerImpl personController;
    @MockBean
    private PersonService personService;
    @MockBean
    private PersonMapper personMapper;
    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.personController).build();
    }

    @Test
    public void getAll() throws Exception {
        Mockito.when(personMapper.asDTOList(ArgumentMatchers.any())).thenReturn(PersonBuilder.getListDTO());

        Mockito.when(personService.findAll()).thenReturn(PersonBuilder.getListEntities());
        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));

    }

    @Test
    public void getById() throws Exception {
        Mockito.when(personMapper.asDTO(ArgumentMatchers.any())).thenReturn(PersonBuilder.getDTO());

        Mockito.when(personService.findById(ArgumentMatchers.anyLong())).thenReturn(java.util.Optional.of(PersonBuilder.getEntity()));

        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)));
        Mockito.verify(personService, Mockito.times(1)).findById(1L);
        Mockito.verifyNoMoreInteractions(personService);
    }

    @Test
    public void save() throws Exception {
        Mockito.when(personMapper.asEntity(ArgumentMatchers.any())).thenReturn(PersonBuilder.getEntity());
        Mockito.when(personService.save(ArgumentMatchers.any(Person.class))).thenReturn(PersonBuilder.getEntity());

        mockMvc.perform(
                        MockMvcRequestBuilders.post(ENDPOINT_URL)
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(CustomUtils.asJsonString(PersonBuilder.getDTO())))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        Mockito.verify(personService, Mockito.times(1)).save(ArgumentMatchers.any(Person.class));
        Mockito.verifyNoMoreInteractions(personService);
    }

    @Test
    public void update() throws Exception {
        Mockito.when(personMapper.asEntity(ArgumentMatchers.any())).thenReturn(PersonBuilder.getEntity());
        Mockito.when(personService.update(ArgumentMatchers.any(), ArgumentMatchers.anyLong())).thenReturn(PersonBuilder.getEntity());

        mockMvc.perform(
                        MockMvcRequestBuilders.put(ENDPOINT_URL + "/1")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(CustomUtils.asJsonString(PersonBuilder.getDTO())))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(personService, Mockito.times(1)).update(ArgumentMatchers.any(Person.class), ArgumentMatchers.anyLong());
        Mockito.verifyNoMoreInteractions(personService);
    }

    @Test
    public void delete() throws Exception {
        Mockito.doNothing().when(personService).deleteById(ArgumentMatchers.anyLong());
        mockMvc.perform(
                        MockMvcRequestBuilders.delete(ENDPOINT_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(personService, Mockito.times(1)).deleteById(Mockito.anyLong());
        Mockito.verifyNoMoreInteractions(personService);
    }
}