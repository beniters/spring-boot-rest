package com.ninjaone.backendinterviewproject.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninjaone.backendinterviewproject.domain.Platform;
import com.ninjaone.backendinterviewproject.domain.Service;
import com.ninjaone.backendinterviewproject.service.ServiceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ServiceController.class)
public class ServiceControllerTest {

    private static final String API_PREFIX = "/service";
    private static final String GET_BY_ID = String.format("%s/{id}", API_PREFIX);

    public static final long ID = 1L;
    public static final Platform PLATFORM = Platform.ALL;
    public static final double COAST = 1D;
    public static final String DESCRIPTION = "Description 1";

    @MockBean
    ServiceService service;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    private Service entity() {
        return Service.builder()
                .id(ID)
                .description(DESCRIPTION)
                .coast(COAST)
                .platform(PLATFORM)
            .build();
    }


    @Test
    void shouldCreateDonorNotificationsEndpointIsSuccessfully() throws Exception {
        Service dto = entity();
        given(service.createServiceEntity(any())).willReturn(dto);

        //Test & Result
        MvcResult result = mvc.perform(
                post(API_PREFIX)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("utf-8")
                    .content(objectMapper.writeValueAsString(dto))
                )
            .andExpect(status().isCreated())
            .andReturn();

        Service response =
            objectMapper.readValue(result.getResponse().getContentAsString(), Service.class);

        //Asserts
        assertNotNull(response);
        assertThat(response).isEqualTo(dto);
    }

    @Test
    void shouldDeleteServiceByIdSuccessfully() throws Exception {
        //Test & Result
        MvcResult result = mvc.perform(
                delete(GET_BY_ID, 1)
                    .contentType(MediaType.APPLICATION_JSON)
                )
            .andExpect(status().isNoContent())
            .andReturn();

        assertEquals("", result.getResponse().getContentAsString());
    }

}
