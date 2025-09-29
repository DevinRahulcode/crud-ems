package com.example.crud_ems.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeRequestDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validDTO_NoValidationErrors() {
        EmployeeRequestDTO dto = new EmployeeRequestDTO();
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setEmail("john.doe@example.com");
        dto.setSalary("50000");

        Set<ConstraintViolation<EmployeeRequestDTO>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
    }

    @Test
    void invalidEmail_ValidationError() {
        EmployeeRequestDTO dto = new EmployeeRequestDTO();
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setEmail("invalid");
        dto.setSalary("50000");

        Set<ConstraintViolation<EmployeeRequestDTO>> violations = validator.validate(dto);

        assertEquals(1, violations.size());
        assertEquals("Email Should be valid", violations.iterator().next().getMessage());
    }

    @Test
    void blankSalary_ValidationError() {
        EmployeeRequestDTO dto = new EmployeeRequestDTO();
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setEmail("john.doe@example.com");
        dto.setSalary("");

        Set<ConstraintViolation<EmployeeRequestDTO>> violations = validator.validate(dto);

        assertEquals(1, violations.size());
        assertEquals("The Salary is required", violations.iterator().next().getMessage());
    }
}