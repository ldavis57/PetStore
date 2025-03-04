package pet.store.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pet.store.controller.model.EmployeeData;
import pet.store.dao.EmployeeDao;
import pet.store.entity.Employee;

/**
 * Service layer for managing employee operations.
 * Provides methods for creating, updating, retrieving, and deleting employees.
 */
@Service // Marks this class as a Spring service component
public class EmployeeService {

    @Autowired // Injects the EmployeeDao dependency
    private EmployeeDao employeeDao;

    /**
     * Saves or updates an employee record. Used by both POST (create) and PUT (update) operations.
     * 
     * @param employeeData The employee data transfer object (DTO).
     * @return The saved employee data.
     */
    @Transactional(readOnly = false) // Allows write operations within a transaction
    public EmployeeData saveEmployee(EmployeeData employeeData) {
        Long employeeId = employeeData.getEmployeeId();
        Employee employee = findOrCreateEmployee(employeeId);

        copyEmployeeFields(employee, employeeData); // Copies DTO fields to entity
        return new EmployeeData(employeeDao.save(employee)); // Saves entity and returns DTO
    }

    /**
     * Copies relevant fields from a DTO to an Employee entity.
     * 
     * @param employee The entity to update.
     * @param employeeData The DTO containing updated field values.
     */
    private void copyEmployeeFields(Employee employee, EmployeeData employeeData) {
        employee.setEmployeeFirstName(employeeData.getEmployeeFirstName());
        employee.setEmployeeLastName(employeeData.getEmployeeLastName());
        employee.setEmployeePhone(employeeData.getEmployeePhone());
        employee.setEmployeeJobTitle(employeeData.getEmployeeJobTitle());
    }

    /**
     * Finds an existing employee by ID or creates a new instance if ID is null.
     * 
     * @param employeeId The ID of the employee.
     * @return An existing or new Employee entity.
     */
    private Employee findOrCreateEmployee(Long employeeId) {
        if (Objects.isNull(employeeId)) {
            return new Employee(); // Creates a new Employee if ID is null
        } else {
            return findEmployeeById(employeeId); // Retrieves existing Employee by ID
        }
    }

    /**
     * Finds an employee by its ID, throwing an exception if not found.
     * 
     * @param employeeId The ID of the employee.
     * @return The found Employee entity.
     * @throws NoSuchElementException if the employee is not found.
     */
    private Employee findEmployeeById(Long employeeId) {
        return employeeDao.findById(employeeId)
                .orElseThrow(() -> new NoSuchElementException("Employee with ID=" + employeeId + " was not found."));
    }

    /**
     * Retrieves all employees as a list of DTOs.
     * 
     * @return A list of all employee data.
     */
    @Transactional(readOnly = true) // Optimized for read-only transactions
    public List<EmployeeData> retrieveAllEmployees() {
        List<Employee> employees = employeeDao.findAll();
        List<EmployeeData> response = new LinkedList<>();

        for (Employee employee : employees) {
            response.add(new EmployeeData(employee)); // Converts each entity to a DTO
        }
        return response;
    }

    /**
     * Retrieves an employee by its ID as a DTO.
     * 
     * @param employeeId The ID of the employee to retrieve.
     * @return The corresponding employee data.
     */
    @Transactional(readOnly = true)
    public EmployeeData retrieveEmployeeById(Long employeeId) {
        Employee employee = findEmployeeById(employeeId);
        return new EmployeeData(employee);
    }

    /**
     * Deletes an employee by its ID.
     * 
     * @param employeeId The ID of the employee to delete.
     */
    public void deleteEmployee(Long employeeId) {
        employeeDao.deleteById(employeeId);
    }
}
