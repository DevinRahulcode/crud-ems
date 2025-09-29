package com.example.crud_ems.controller;

import com.example.crud_ems.dto.EmployeeRequestDTO;
import com.example.crud_ems.dto.EmployeeResponseDTO;
import com.example.crud_ems.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    private MockMvc mockMvc;
    private EmployeeRequestDTO employeeRequestDTO;
    private EmployeeResponseDTO employeeResponseDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();

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
    void getEmployees_ReturnsEmployeeList() throws Exception {
        when(employeeService.getEmployees()).thenReturn(Arrays.asList(employeeResponseDTO));

        mockMvc.perform(get("/api/all-employee")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].firstName").value("John"));

        verify(employeeService, times(1)).getEmployees();
    }

    @Test
    void getEmployeeById_ValidId_ReturnsEmployee() throws Exception {
        when(employeeService.getEmployeeById(1L)).thenReturn(employeeResponseDTO);

        mockMvc.perform(get("/api/employee/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.firstName").value("John"));

        verify(employeeService, times(1)).getEmployeeById(1L);
    }

    @Test
    void createEmployee_ValidDTO_ReturnsEmployeeResponseDTO() throws Exception {
        when(employeeService.createEmployee(any(EmployeeRequestDTO.class))).thenReturn(employeeResponseDTO);

        mockMvc.perform(post("/api/create-employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john.doe@example.com\",\"salary\":\"50000\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));

        verify(employeeService, times(1)).createEmployee(any(EmployeeRequestDTO.class));
    }

    @Test
    void createEmployee_InvalidDTO_ReturnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/create-employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"\",\"lastName\":\"Doe\",\"email\":\"invalid\",\"salary\":\"\"}"))
                .andExpect(status().isBadRequest());

        verify(employeeService, never()).createEmployee(any(EmployeeRequestDTO.class));
    }

    @Test
    void updateEmployee_ValidIdAndDTO_ReturnsUpdatedEmployee() throws Exception {
        when(employeeService.updateEmployee(eq(1L), any(EmployeeRequestDTO.class))).thenReturn(employeeResponseDTO);

        mockMvc.perform(put("/api/update-employee/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john.doe@example.com\",\"salary\":\"50000\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.firstName").value("John"));

        verify(employeeService, times(1)).updateEmployee(eq(1L), any(EmployeeRequestDTO.class));
    }

    @Test
    void deleteEmployee_ValidId_ReturnsNoContent() throws Exception {
        doNothing().when(employeeService).deleteEmployee(1L);

        mockMvc.perform(delete("/api/delete-employee/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(employeeService, times(1)).deleteEmployee(1L);
    }
}