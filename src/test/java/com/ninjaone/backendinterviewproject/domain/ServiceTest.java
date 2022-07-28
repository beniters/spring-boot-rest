package com.ninjaone.backendinterviewproject.domain;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ServiceTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void shouldNotValidateWhenMandatoryFieldsAreNull(){
        var constraintViolations = validator.validate(new Service());
        assertThat(constraintViolations).hasSize(3);

        ConstraintViolation<Service> violation = find(constraintViolations, "description");
        assertNotNull(violation);
        assertThat(violation.getMessage()).isEqualTo("must not be null");

        violation = find(constraintViolations, "platform");
        assertNotNull(violation);
        assertThat(violation.getMessage()).isEqualTo("must not be null");

        violation = find(constraintViolations, "coast");
        assertNotNull(violation);
        assertThat(violation.getMessage()).isEqualTo("must not be null");

    }

    ConstraintViolation<Service> find(Set<ConstraintViolation<Service>> constraintViolations, String propertyName) {
        return constraintViolations.stream()
                .filter(i -> propertyName.equals(i.getPropertyPath().toString()))
                .findAny()
                .orElse(null);
    }
}
