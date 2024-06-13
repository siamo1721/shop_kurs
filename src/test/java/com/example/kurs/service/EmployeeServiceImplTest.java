package com.example.kurs.service;
import com.example.kurs.exception.ResourceNotFoundException;
import com.example.kurs.model.Employee;
import com.example.kurs.repository.EmployeeRepository;
import com.example.kurs.service.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateEmployeeSuccess() {
        Long employeeId = 1L;
        Employee employeeDetails = new Employee();
        employeeDetails.setFirstName("John");

        Employee employee = new Employee();
        employee.setId(employeeId);

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        employeeService.updateEmployee(employeeId, employeeDetails);

        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void testUpdateEmployeeNotFound() {
        Long employeeId = 1L;
        Employee employeeDetails = new Employee();

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.updateEmployee(employeeId, employeeDetails));
    }
}

