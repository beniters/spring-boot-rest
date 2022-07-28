package com.ninjaone.backendinterviewproject.domain;

import javax.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Where(clause = "active = 'Y'")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String name;

    @ToString.Exclude
    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClientServiceDevice> clientServiceDevices = new ArrayList<>();

    @NotNull
    @Type(type = "yes_no")
    @Column(length = 1, nullable = false, columnDefinition = "varchar(1) not null default 'Y'")
    private Boolean active;

    @NotNull
    @Column(nullable = false)
    private ZonedDateTime createDate;

    private ZonedDateTime deleteDate;
}

