package com.ninjaone.backendinterviewproject.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientServiceDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @NotNull
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false)
    private Client client;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "service_id", referencedColumnName = "id", nullable = false)
    private Service service;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "device_id", referencedColumnName = "id", nullable = false)
    private Device device;

    @NotNull
    @Column(nullable = false)
    private ZonedDateTime serviceDate;
}
