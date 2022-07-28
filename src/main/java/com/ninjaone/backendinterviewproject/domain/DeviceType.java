package com.ninjaone.backendinterviewproject.domain;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @NotNull
    @Column(name="name", nullable = false, unique = true)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Platform platform;
}
