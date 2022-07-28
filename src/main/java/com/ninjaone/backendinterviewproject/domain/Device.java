package com.ninjaone.backendinterviewproject.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @NotNull
    @Column(name="system_name", nullable = false, unique = true)
    private String systemName;

    @OneToOne
    @JoinColumn(name = "device_type_id", referencedColumnName = "id", nullable = false)
    private DeviceType deviceType;

    @ToString.Exclude
    @Builder.Default
    @JsonIgnore
    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL, orphanRemoval = true)
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
