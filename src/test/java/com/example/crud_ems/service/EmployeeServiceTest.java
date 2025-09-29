package com.example.crud_ems.service;

import com.example.crud_ems.dto.EmployeeRequestDTO;
import com.example.crud_ems.dto.EmployeeResponseDTO;
import com.example.crud_ems.exception.EmailAlreadyExistsException;
import com.example.crud_ems.exception.EmployeeNotFoundException;
import com.example.crud_ems.mapper.EmployeeMapper;
import com.example.crud_ems.model.Employee;
import com.example.crud_ems.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;
    private EmployeeRequestDTO employeeRequestDTO;
    private EmployeeResponseDTO employeeResponseDTO;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("john.doe@example.com");
        employee.setSalary("50000");

        employeeRequestDTO = new EmployeeRequestDTO();
        employeeRequestDTO.setFirstName("John");
        employeeRequestDTO.setLastName("Doe");
        employeeRequestDTO.setEmail("john.doe@example.com");
        employeeRequestDTO.setSalary("50000");

        employeeResponseDTO = new EmployeeResponseDTO();
        employeeResponseDTO.setId("1");
        employeeResponseDTO.setFirstName("John");
        employeeResponseDTO.setLastName("Doe");
        employeeResponseDTO.setEmail("john.doe@example.com");
        employeeResponseDTO.setSalary("50000");
    }

    @Test
    void getEmployees_ReturnsEmployeeList() {
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee));

        List<EmployeeResponseDTO> result = employeeService.getEmployees();

        assertEquals(1, result.size());
        assertEquals(employeeResponseDTO.getId(), result.get(0).getId());
        assertEquals(employeeResponseDTO.getFirstName(), result.get(0).getFirstName());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void getEmployeeById_ValidId_ReturnsEmployee() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        EmployeeResponseDTO result = employeeService.getEmployeeById(1L);

        assertNotNull(result);
        assertEquals(employeeResponseDTO.getId(), result.getId());
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void getEmployeeById_InvalidId_ThrowsException() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> employeeService.getEmployeeById(1L));
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void createEmployee_ValidDTO_ReturnsEmployeeResponseDTO() {
        when(employeeRepository.existsByEmail(employeeRequestDTO.getEmail())).thenReturn(false);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        EmployeeResponseDTO result = employeeService.createEmployee(employeeRequestDTO);

        assertNotNull(result);
        assertEquals(employeeResponseDTO.getId(), result.getId());
        assertEquals(employeeResponseDTO.getEmail(), result.getEmail());
        verify(employeeRepository, times(1)).existsByEmail(employeeRequestDTO.getEmail());
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void createEmployee_DuplicateEmail_ThrowsException() {
        when(employeeRepository.existsByEmail(employeeRequestDTO.getEmail())).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () -> employeeService.createEmployee(employeeRequestDTO));
        verify(employeeRepository, times(1)).existsByEmail(employeeRequestDTO.getEmail());
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void updateEmployee_ValidIdAndDTO_ReturnsUpdatedEmployee() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.existsByEmailAndIdNot(employeeRequestDTO.getEmail(), 1L)).thenReturn(false);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        EmployeeResponseDTO result = employeeService.updateEmployee(1L, employeeRequestDTO);

        assertNotNull(result);
        assertEquals(employeeResponseDTO.getId(), result.getId());
        assertEquals(employeeRequestDTO.getFirstName(), result.getFirstName());
        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).existsByEmailAndIdNot(employeeRequestDTO.getEmail(), 1L);
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void updateEmployee_InvalidId_ThrowsException() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.updateEmployee(1L, employeeRequestDTO));
        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void updateEmployee_DuplicateEmail_ThrowsException() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.existsByEmailAndIdNot(employeeRequestDTO.getEmail(), 1L)).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () -> employeeService.updateEmployee(1L, employeeRequestDTO));
        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).existsByEmailAndIdNot(employeeRequestDTO.getEmail(), 1L);
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void deleteEmployee_ValidId_DeletesEmployee() {
        doNothing().when(employeeRepository).deleteById(1L);

        employeeService.deleteEmployee(1L);

        verify(employeeRepository, times(1)).deleteById(1L);
    }
}