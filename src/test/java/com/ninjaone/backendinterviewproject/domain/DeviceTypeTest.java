package com.ninjaone.backendinterviewproject.domain;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DeviceTypeTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void shouldNotValidateWhenMandatoryFieldsAreNull(){
        var constraintViolations = validator.validate(new DeviceType());
        assertThat(constraintViolations).hasSize(2);

        ConstraintViolation<DeviceType> violation = find(constraintViolations, "name");
        assertNotNull(violation);
        assertThat(violation.getMessage()).isEqualTo("must not be null");

        violation = find(constraintViolations, "platform");
        assertNotNull(violation);
        assertThat(violation.getMessage()).isEqualTo("must not be null");
    }

    ConstraintViolation<DeviceType> find(Set<ConstraintViolation<DeviceType>> constraintViolations, String propertyName) {
        return constraintViolations.stream()
                .filter(i -> propertyName.equals(i.getPropertyPath().toString()))
                .findAny()
                .orElse(null);
    }
}
