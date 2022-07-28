package com.ninjaone.backendinterviewproject.web.rest.dto;

import com.ninjaone.backendinterviewproject.domain.Client;
import com.ninjaone.backendinterviewproject.domain.Device;
import com.ninjaone.backendinterviewproject.domain.Service;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
public class ClientServiceDeviceDTO {
    private Long id;

    @NotNull
    private Client client;

    @NotNull
    private Service service;

    @NotNull
    private Device device;

    @NotNull
    private ZonedDateTime serviceDate;
}
