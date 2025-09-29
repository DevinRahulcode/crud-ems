package com.example.crud_ems.service;


import com.example.crud_ems.dto.EmployeeRequestDTO;
import com.example.crud_ems.dto.EmployeeResponseDTO;
import com.example.crud_ems.exception.EmailAlreadyExistsException;
import com.example.crud_ems.exception.EmployeeNotFoundException;
import com.example.crud_ems.mapper.EmployeeMapper;
import com.example.crud_ems.model.Employee;
import com.example.crud_ems.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    public List<EmployeeResponseDTO> getEmployees(){
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeResponseDTO> employeeResponseDTOS = employees.stream().map(employee -> EmployeeMapper.toDto(employee)).toList();

        return employeeResponseDTOS;
    }

    public EmployeeResponseDTO getEmployeeById(Long id){
        Employee employee = employeeRepository.findById(id).orElseThrow( () -> new RuntimeException("Employee not found with id: " + id));

        return EmployeeMapper.toDto(employee);
    }

    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO employeeRequestDTO){

        if(employeeRepository.existsByEmail(employeeRequestDTO.getEmail())){
            throw new EmailAlreadyExistsException("A Employee With this Email already Exists" + employeeRequestDTO.getEmail());
        }
        Employee newEmployee = employeeRepository.save(EmployeeMapper.toModel(employeeRequestDTO));
        return EmployeeMapper.toDto(newEmployee);
    }


    public EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO employeeRequestDTO){
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id" + id));

        if(employeeRepository.existsByEmailAndIdNot(employeeRequestDTO.getEmail(), id)){
            throw new EmailAlreadyExistsException("A Employee With this Email already Exists" + employeeRequestDTO.getEmail());
        }

        employee.setFirstName(employeeRequestDTO.getFirstName());
        employee.setLastName(employeeRequestDTO.getLastName());
        employee.setEmail(employeeRequestDTO.getEmail());
        employee.setSalary(employeeRequestDTO.getSalary());

        Employee updatedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.toDto(updatedEmployee);
    }

    public void deleteEmployee(Long id){
        employeeRepository.deleteById(id);
    }
}
