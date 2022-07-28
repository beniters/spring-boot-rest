package com.ninjaone.backendinterviewproject.domain;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DeviceTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void shouldNotValidateWhenMandatoryFieldsAreNull(){
        var constraintViolations = validator.validate(new Device());
        assertThat(constraintViolations).hasSize(3);

        ConstraintViolation<Device> violation = find(constraintViolations, "systemName");
        assertNotNull(violation);
        assertThat(violation.getMessage()).isEqualTo("must not be null");

        violation = find(constraintViolations, "active");
        assertNotNull(violation);
        assertThat(violation.getMessage()).isEqualTo("must not be null");

        violation = find(constraintViolations, "createDate");
        assertNotNull(violation);
        assertThat(violation.getMessage()).isEqualTo("must not be null");

    }

    ConstraintViolation<Device> find(Set<ConstraintViolation<Device>> constraintViolations, String propertyName) {
        return constraintViolations.stream()
                .filter(i -> propertyName.equals(i.getPropertyPath().toString()))
                .findAny()
                .orElse(null);
    }
}
