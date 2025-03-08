package pet.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreData.PetStoreCustomer;
import pet.store.controller.model.PetStoreData.PetStoreEmployee;
import pet.store.service.PetStoreService;

/**
 * REST Controller to manage pet store operations. Provides endpoints to create,
 * update, and retrieve pet store data.
 */
@RestController
@RequestMapping("/pet_store") // specifies all calls start with pet_store
@Slf4j // Enables logging using Lombok
public class PetStoreController {

	@Autowired // Injects the PetStoreService instance automatically
	private PetStoreService petStoreService;

	/**
	 * Creates a new pet store.
	 * 
	 * @param petStoreData The pet store details received in the request body.
	 * @return The saved pet store data.
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PetStoreData createPetStore(@RequestBody PetStoreData petStoreData) {
		log.info("Creating new pet store: {}", petStoreData);

		if (petStoreData == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pet store data cannot be null.");
		}

		return petStoreService.savePetStore(petStoreData);
	}

	/**
	 * Adds an employee to a specific pet store.
	 * 
	 * @param petStoreId       The ID of the pet store.
	 * @param petStoreEmployee The employee details received in the request body.
	 * @return The added employee data.
	 */
	@PostMapping("/{petStoreId}/employee")
	@ResponseStatus(code = HttpStatus.CREATED) // Returns HTTP 201 Created on success
	public PetStoreEmployee addEmployeeToPetStore(@PathVariable Long petStoreId,
			@RequestBody PetStoreEmployee petStoreEmployee) { // @RequestBody: body is json
		log.info("Adding employee {} to pet store with ID={}", petStoreEmployee, petStoreId);
		return petStoreService.saveEmployee(petStoreId, petStoreEmployee);
	}

	/**
	 * Adds a customer to a specific pet store.
	 * 
	 * @param petStoreId       The ID of the pet store.
	 * @param petStoreCustomer The customer details received in the request body.
	 * @return The added customer data.
	 */
	@PostMapping("/{petStoreId}/customer")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreCustomer addCustomerToPetStore(@PathVariable Long petStoreId,
			@RequestBody PetStoreCustomer petStoreCustomer) {
		log.info("Adding customer {} to pet store with ID={}", petStoreCustomer, petStoreId);
		return petStoreService.saveCustomer(petStoreId, petStoreCustomer);
	}

	/**
	 * Updates an existing pet store by ID.
	 * 
	 * @param petStoreId   The ID of the pet store.
	 * @param petStoreData The updated pet store data.
	 * @return The updated pet store data.
	 */
	@PutMapping("/{petStoreId}")
	public PetStoreData updatePetStore(@PathVariable Long petStoreId, @RequestBody PetStoreData petStoreData) {
		petStoreData.setPetStoreId(petStoreId); // Ensures the correct ID is set
		log.info("Updating pet store with ID: {}", petStoreData);
		return petStoreService.savePetStore(petStoreData);
	}

	/**
	 * Updates an employee in a specific pet store.
	 * 
	 * @param petStoreId       The ID of the pet store.
	 * @param employeeId       The ID of the employee to be updated.
	 * @param petStoreEmployee The updated employee data.
	 * @return The updated employee data.
	 */
	@PutMapping("/{petStoreId}/employee/{employeeId}")
	public PetStoreEmployee updateEmployee(@PathVariable Long petStoreId, @PathVariable Long employeeId,
			@RequestBody PetStoreEmployee petStoreEmployee) {
		log.info("Updating employee with ID={} for pet store with ID={}", employeeId, petStoreId);
		return petStoreService.updateEmployee(petStoreId, employeeId, petStoreEmployee);
	}

	/**
	 * Updates a customer in a specific pet store.
	 * 
	 * @param petStoreId       The ID of the pet store.
	 * @param customerId       The ID of the customer to be updated.
	 * @param petStoreCustomer The updated customer data.
	 * @return The updated customer data.
	 */
	@PutMapping("/{petStoreId}/customer/{customerId}")
	public PetStoreCustomer updateCustomer(@PathVariable Long petStoreId, @PathVariable Long customerId,
			@RequestBody PetStoreCustomer petStoreCustomer) {
		log.info("Updating customer with ID={} for pet store with ID={}", customerId, petStoreId);
		return petStoreService.updateCustomer(petStoreId, customerId, petStoreCustomer);
	}

	@PutMapping("/employee/{employeeId}")
	public PetStoreEmployee updateUnassignedEmployee(@PathVariable Long employeeId,
			@RequestBody PetStoreEmployee petStoreEmployee) {
		log.info("Updating employee with ID={}", employeeId);
		return petStoreService.updateUnassignedEmployee(employeeId, petStoreEmployee);
	}

	@PutMapping("/{petStoreId}/employee/{employeeId}/assign")
	public PetStoreEmployee assignEmployeeToStore(@PathVariable Long petStoreId, @PathVariable Long employeeId) {
		log.info("Assigning employee with ID={} to pet store with ID={}", employeeId, petStoreId);
		return petStoreService.assignEmployeeToStore(petStoreId, employeeId);
	}

	/**
	 * Retrieves all pet stores.
	 * 
	 * @return A list of all stored pet stores.
	 */
	@GetMapping
	public List<PetStoreData> retrieveAllPetStores() {
		log.info("Retrieving all pet stores");
		return petStoreService.retrieveAllPetStores();
	}

	/**
	 * Retrieves a specific pet store by ID.
	 * 
	 * @param petStoreId The ID of the pet store to retrieve.
	 * @return The pet store data if found.
	 */
	@GetMapping("/{petStoreId}")
	public PetStoreData retrievePetStoreById(@PathVariable Long petStoreId) {
		log.info("Retrieving pet store with ID={}", petStoreId);
		return petStoreService.retrievePetStoreById(petStoreId);
	}

	/**
	 * Retrieves all employees for a specific pet store.
	 * 
	 * @param petStoreId The ID of the pet store.
	 * @return A list of employees working in the specified pet store.
	 */
	@GetMapping("/{petStoreId}/employees")
	public List<PetStoreEmployee> getAllEmployeesForPetStore(@PathVariable Long petStoreId) {
		log.info("Retrieving all employees for pet store with ID={}", petStoreId);
		return petStoreService.getEmployeesByPetStoreId(petStoreId);
	}

	/**
	 * Retrieves a specific employee by their ID from a pet store.
	 * 
	 * @param petStoreId The ID of the pet store.
	 * @param employeeId The ID of the employee to retrieve.
	 * @return The employee data if found.
	 */
	@GetMapping("/{petStoreId}/employee/{employeeId}")
	public PetStoreEmployee getEmployeeById(@PathVariable Long petStoreId, @PathVariable Long employeeId) {
		log.info("Retrieving employee with ID={} from pet store with ID={}", employeeId, petStoreId);
		return petStoreService.retrieveEmployeeById(petStoreId, employeeId);
	}

	/**
	 * Retrieves all employees across all pet stores.
	 * 
	 * @return A list of all employees in all pet stores.
	 */
	@GetMapping("/employees")
	public List<PetStoreEmployee> getAllEmployees() {
		log.info("Retrieving all employees across all pet stores");
		return petStoreService.getAllEmployees();
	}

	/**
	 * Retrieves all customers for a specific pet store.
	 * 
	 * @param petStoreId The ID of the pet store.
	 * @return A list of customers associated with the specified pet store.
	 */
	@GetMapping("/{petStoreId}/customers")
	public List<PetStoreCustomer> getAllCustomersForPetStore(@PathVariable Long petStoreId) {
		log.info("Retrieving all customers for pet store with ID={}", petStoreId);
		return petStoreService.getCustomersByPetStoreId(petStoreId);
	}

	/**
	 * Retrieves all customers across all pet stores.
	 * 
	 * @return A list of all customers in all pet stores.
	 */
	@GetMapping("/customers")
	public List<PetStoreCustomer> getAllCustomers() {
		log.info("Retrieving all customers across all pet stores");
		return petStoreService.getAllCustomers();
	}

	/**
	 * Retrieves a specific customer by their ID from a pet store.
	 * 
	 * @param petStoreId The ID of the pet store.
	 * @param customerId The ID of the customer to retrieve.
	 * @return The customer data if found.
	 */
	@GetMapping("/{petStoreId}/customer/{customerId}")
	public PetStoreCustomer getCustomerById(@PathVariable Long petStoreId, @PathVariable Long customerId) {
		log.info("Retrieving customer with ID={} for pet store with ID={}", customerId, petStoreId);
		return petStoreService.getCustomerById(petStoreId, customerId);
	}

	/**
	 * Deletes a specific pet store by ID.
	 * 
	 * @param petStoreId The ID of the pet store to be deleted.
	 */
	@DeleteMapping("/{petStoreId}")
	public void deletePetStoreById(@PathVariable Long petStoreId) {
		log.info("deletePetStoreById {}");
		petStoreService.deletePetStore(petStoreId);
	}

	/**
	 * Deletes a customer from a specific pet store.
	 * 
	 * @param petStoreId The ID of the pet store.
	 * @param customerId The ID of the customer to be deleted.
	 */
	@DeleteMapping("/{petStoreId}/customer/{customerId}")
	@ResponseStatus(HttpStatus.NO_CONTENT) // 204 No Content on successful deletion
	public void deleteCustomer(@PathVariable Long petStoreId, @PathVariable Long customerId) {
		log.info("Deleting customer with ID={} from pet store with ID={}", customerId, petStoreId);
		petStoreService.deleteCustomer(petStoreId, customerId);
	}

	/**
	 * Deletes an employee from a specific pet store.
	 * 
	 * @param petStoreId The ID of the pet store.
	 * @param employeeId The ID of the employee to be deleted.
	 */
	@DeleteMapping("/{petStoreId}/employee/{employeeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT) // 204 No Content on successful deletion
	public void deleteEmployee(@PathVariable Long petStoreId, @PathVariable Long employeeId) {
		log.info("Deleting employee with ID={} from pet store with ID={}", employeeId, petStoreId);
		petStoreService.deleteEmployee(petStoreId, employeeId);
	}
}
