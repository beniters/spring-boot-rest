package com.ninjaone.backendinterviewproject.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninjaone.backendinterviewproject.domain.Device;
import com.ninjaone.backendinterviewproject.domain.DeviceType;
import com.ninjaone.backendinterviewproject.domain.Platform;
import com.ninjaone.backendinterviewproject.service.DeviceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeviceController.class)
public class DeviceControllerTest {

    private static final String API_PREFIX = "/device";
    private static final String GET_BY_ID = String.format("%s/{id}", API_PREFIX);
    private static final Long ENTITY_ID = 1L;

    public static final long ID = 1L;
    public static final Boolean ACTIVE = Boolean.TRUE;
    public static final ZoneId UTC = ZoneId.of("UTC");
    public static final ZonedDateTime CREATE_DATE = ZonedDateTime.now().withZoneSameInstant(UTC);
    public static final String SYSTEM_NAME = "System Name";
    public static final Platform PLATFORM = Platform.WINDOWS;
    public static final String DEVICE_TYPE_NAME = "Windows Workstation";

    @MockBean
    DeviceService service;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void before() {
        given(service.getDeviceEntity(eq(ENTITY_ID))).willReturn(Optional.of(entity()));
    }

    private Device entity() {

        return Device.builder()
                .id(ID)
                .systemName(SYSTEM_NAME)
                .active(ACTIVE)
                .createDate(CREATE_DATE)
                .deviceType(getChild())
            .build();
    }


    private DeviceType getChild() {
        return DeviceType.builder()
                .id(ID)
                .name(DEVICE_TYPE_NAME)
                .platform(PLATFORM)
            .build();
    }

    @Test
    void shouldCreateDonorNotificationsEndpointIsSuccessfully() throws Exception {
        Device dto = entity();
        given(service.createDeviceEntity(any())).willReturn(dto);

        //Test & Result
        MvcResult result = mvc.perform(
                post(API_PREFIX)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("utf-8")
                    .content(objectMapper.writeValueAsString(dto))
                )
            .andExpect(status().isCreated())
            .andReturn();

        Device response =
            objectMapper.readValue(result.getResponse().getContentAsString(), Device.class);

        //Asserts
        assertNotNull(response);
        assertThat(response).isEqualTo(dto);
    }

    @Test
    void shouldDeleteDeviceByIdSuccessfully() throws Exception {
        //Test & Result
        MvcResult result = mvc.perform(
                delete(GET_BY_ID, 1)
                    .contentType(MediaType.APPLICATION_JSON)
                )
            .andExpect(status().isNoContent())
            .andReturn();

        assertEquals("", result.getResponse().getContentAsString());
    }

    @Test
    void shouldFindDeviceByIdSuccessfully() throws Exception {
        Device dto = entity();
        given(service.getDeviceEntity(any())).willReturn(Optional.of(dto));

        //Test & Result
        MvcResult result = mvc.perform(
                get(GET_BY_ID, 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("utf-8")
                )
            .andExpect(status().isOk())
            .andReturn();

        Device response = objectMapper.readValue(result.getResponse().getContentAsString(), Device.class);
        ZonedDateTime createDateTZWithUTC = response.getCreateDate().withZoneSameInstant(UTC);
        response.setCreateDate(createDateTZWithUTC);

        //Asserts
        assertNotNull(response);
        assertThat(response).isNotNull();
        assertThat(response).isEqualTo(dto);
    }

    @Test
    void shouldUpdateDeviceSuccessfully() throws Exception {
        Device dto = entity();
        given(service.updateDeviceEntity(any())).willReturn(dto);

        //Test & Result
        MvcResult result = mvc.perform(
                        put(API_PREFIX)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                                .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())
                .andReturn();


        Device response = objectMapper.readValue(result.getResponse().getContentAsString(), Device.class);

        //Asserts
        assertNotNull(response);
        assertThat(response).isEqualTo(dto);
    }
}
