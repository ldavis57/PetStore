package pet.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.EmployeeData;
import pet.store.service.EmployeeService;

/**
 * REST Controller to manage employee operations.
 * Provides endpoints to create, update, retrieve, and delete employees.
 */
@RestController
@RequestMapping("/employee") // Base path for all endpoints
@Slf4j // Enables logging using Lombok
public class EmployeeController {

    @Autowired // Injects the EmployeeService instance automatically
    private EmployeeService employeeService;

    /**
     * Creates a new employee.
     * 
     * @param employeeData The employee details received in the request body.
     * @return The saved employee data.
     */
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED) // Returns HTTP 201 Created on success
    public EmployeeData insertEmployee(@RequestBody EmployeeData employeeData) {
        log.info("Creating employee {}", employeeData);
        return employeeService.saveEmployee(employeeData);
    }

    /**
     * Updates an existing employee by ID.
     * 
     * @param employeeId   The ID of the employee to update.
     * @param employeeData The updated employee details.
     * @return The updated employee data.
     */
    @PutMapping("{employeeId}")
    public EmployeeData updateEmployee(@PathVariable Long employeeId, @RequestBody EmployeeData employeeData) {
        employeeData.setEmployeeId(employeeId); // Ensures the correct ID is set
        log.info("Updating employee with ID: {}", employeeData);
        return employeeService.saveEmployee(employeeData);
    }

    /**
     * Retrieves all employees.
     * 
     * @return A list of all stored employees.
     */
    @GetMapping //("/employee")
    public List<EmployeeData> retrieveAllEmployees() {
        log.info("Retrieving all employees.");
        return employeeService.retrieveAllEmployees();
    }

    /**
     * Retrieves a specific employee by their ID.
     * 
     * @param employeeId The ID of the employee to retrieve.
     * @return The employee data if found.
     */
    @GetMapping("/employee/{employeeId}")
    public EmployeeData retrieveEmployeeById(@PathVariable Long employeeId) {
        log.info("Retrieving employee with ID={}", employeeId);
        return employeeService.retrieveEmployeeById(employeeId);
    }

    /**
     * Deletes a specific employee by their ID.
     * 
     * @param employeeId The ID of the employee to delete.
     */
    @DeleteMapping("/employee/{employeeId}")
    public void deleteEmployeeById(@PathVariable Long employeeId) {
        log.info("Deleting employee with ID={}", employeeId);
        employeeService.deleteEmployee(employeeId);
    }
}