package com.example.kurs.service;

import com.example.kurs.exception.ResourceNotFoundException;
import com.example.kurs.model.Employee;
import com.example.kurs.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAllEmployees() {
        log.info("Fetching all employees");
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(Long id) {
        log.info("Fetching employee with id: {}", id);
        return employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Employee not found with id: {}", id);
                    return new ResourceNotFoundException("Employee not found with id " + id);
                });
    }

    @Override
    public Employee createEmployee(Employee employee) {
        log.info("Creating new employee with details: {}", employee);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(Long id, Employee employeeDetails) {
        log.info("Updating employee with id: {}", id);
        Employee employee = getEmployeeById(id);
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setPhone(employeeDetails.getPhone());
        log.info("Employee with id: {} updated with new details: {}", id, employeeDetails);
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
        log.info("Deleting employee with id: {}", id);
        Employee employee = getEmployeeById(id);
        employeeRepository.delete(employee);
        log.info("Employee with id: {} deleted", id);
    }
}
