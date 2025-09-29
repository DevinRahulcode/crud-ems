package com.example.crud_ems.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EmployeeRequestDTO {

    @NotBlank
    @Size(max = 150, message = "The Charactor should not exceed 150")
    private String firstName;

    @NotBlank
    @Size(max = 150, message = "The Charactor should not exceed 150")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email Should be valid")
    private String email;

    @NotBlank(message = "The Salary is required")
    private String salary;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }
}
