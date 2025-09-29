package com.example.crud_ems.controller;



import com.example.crud_ems.dto.EmployeeRequestDTO;
import com.example.crud_ems.dto.EmployeeResponseDTO;
import com.example.crud_ems.service.EmployeeService;
import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @GetMapping("/all-employee")
    public ResponseEntity<List<EmployeeResponseDTO>> getEmployees(){
        List<EmployeeResponseDTO> employees = employeeService.getEmployees();
        return ResponseEntity.ok().body(employees);
    }


    @GetMapping("/employee/{id}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable Long id){
        EmployeeResponseDTO employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);

    }


    @PostMapping("/create-employee")
    public ResponseEntity<EmployeeResponseDTO> createEmployee(
            @Validated({Default.class}) @RequestBody EmployeeRequestDTO employeeRequestDTO){
        EmployeeResponseDTO employeeResponseDTO = employeeService.createEmployee(employeeRequestDTO);
        return ResponseEntity.ok().body(employeeResponseDTO);
    }

    @PutMapping("/update-employee/{id}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(@PathVariable Long id, @Validated({Default.class}) @RequestBody EmployeeRequestDTO employeeRequestDTO){
        EmployeeResponseDTO employeeResponseDTO = employeeService.updateEmployee(id, employeeRequestDTO);

        return ResponseEntity.ok().body(employeeResponseDTO);
    }

    @DeleteMapping("/delete-employee/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id){
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

}
