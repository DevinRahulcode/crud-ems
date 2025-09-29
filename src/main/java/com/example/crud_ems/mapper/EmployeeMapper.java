package com.example.crud_ems.mapper;

import com.example.crud_ems.dto.EmployeeRequestDTO;
import com.example.crud_ems.dto.EmployeeResponseDTO;
import com.example.crud_ems.model.Employee;

public class EmployeeMapper {

    public static EmployeeResponseDTO toDto(Employee employee){
        EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO();

        employeeResponseDTO.setId(employee.getId().toString());
        employeeResponseDTO.setFirstName(employee.getFirstName());
        employeeResponseDTO.setLastName(employee.getLastName());
        employeeResponseDTO.setEmail(employee.getEmail());
        employeeResponseDTO.setSalary(employee.getSalary().toString());

        return employeeResponseDTO;
    }


    public static Employee toModel(EmployeeRequestDTO employeeRequestDTO){
        Employee employee = new Employee();

        employee.setFirstName(employeeRequestDTO.getFirstName());
        employee.setLastName(employeeRequestDTO.getLastName());
        employee.setEmail(employeeRequestDTO.getEmail());
        employee.setSalary(employeeRequestDTO.getSalary());

        return employee;
    }
}
