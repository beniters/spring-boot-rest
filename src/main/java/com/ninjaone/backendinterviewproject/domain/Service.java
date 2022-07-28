package com.ninjaone.backendinterviewproject.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.constraints.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false)
    private Long id;

    @NotNull
    @Column(unique = true, nullable = false)
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Platform platform;

    @NotNull
    @Column(nullable = false, columnDefinition = "double not null default 0")
    private Double coast;

    @ToString.Exclude
    @Builder.Default
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClientServiceDevice> clientServiceDevices = new ArrayList<>();
}
