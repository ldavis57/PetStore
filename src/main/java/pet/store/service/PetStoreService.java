package pet.store.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreData.PetStoreCustomer;
import pet.store.controller.model.PetStoreData.PetStoreEmployee;
import pet.store.dao.CustomerDao;
import pet.store.dao.EmployeeDao;
import pet.store.dao.PetStoreDao;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

/**
 * Service layer for managing pet store operations. Provides methods for
 * creating, updating, retrieving, and deleting pet stores.
 */
@Service // Marks this class as a Spring service component
public class PetStoreService {

	@Autowired // Injects the PetStoreDao dependency
	private PetStoreDao petStoreDao;

	@Autowired // Injects the PetStoreDao dependency
	private EmployeeDao employeeDao;

	@Autowired // Injects the PetStoreDao dependency
	private CustomerDao customerDao;

	/**
	 * Saves or updates a pet store record. Used by both POST (create) and PUT
	 * (update) operations.
	 * 
	 * @param petStoreData The pet store data transfer object (DTO).
	 * @return The saved pet store data.
	 */
	@Transactional(readOnly = false) // Allows write operations within a transaction
	public PetStoreData savePetStore(PetStoreData petStoreData) {
		Long petStoreId = petStoreData.getPetStoreId();
		PetStore petStore = findOrCreatePetStore(petStoreId);

		copyPetStoreFields(petStore, petStoreData); // Copies DTO fields to entity
		return new PetStoreData(petStoreDao.save(petStore)); // Saves entity and returns DTO
	}

	/**
	 * Copies relevant fields from a DTO to a PetStore entity.
	 * 
	 * @param petStore     The entity to update.
	 * @param petStoreData The DTO containing updated field values.
	 */
	private void copyPetStoreFields(PetStore petStore, PetStoreData petStoreData) {
		petStore.setPetStoreAddress(petStoreData.getPetStoreAddress());
		petStore.setPetStoreCity(petStoreData.getPetStoreCity());
		petStore.setPetStoreId(petStoreData.getPetStoreId());
		petStore.setPetStoreName(petStoreData.getPetStoreName());
		petStore.setPetStorePhone(petStoreData.getPetStorePhone());
		petStore.setPetStoreState(petStoreData.getPetStoreState());
		petStore.setPetStoreZip(petStoreData.getPetStoreZip());
	}

	private void copyEmployeeFields(Employee employee, PetStoreEmployee petStoreEmployee) {
		employee.setEmployeeId(petStoreEmployee.getEmployeeId());
		employee.setEmployeeFirstName(petStoreEmployee.getEmployeeFirstName());
		employee.setEmployeeLastName(petStoreEmployee.getEmployeeLastName());
		employee.setEmployeeJobTitle(petStoreEmployee.getEmployeeJobTitLe());
		employee.setEmployeePhone(petStoreEmployee.getEmployeePhone());
	}

	private void copyCustomerFields(Customer customer, PetStoreCustomer petStoreCustomer) {
		customer.setCustomerId(petStoreCustomer.getCustomerId());
		customer.setCustomerFirstName(petStoreCustomer.getCustomerFirstName()); // Stores the customer's first name
		customer.setCustomerLastName(petStoreCustomer.getCustomerLastName()); // Stores the customer's last name
		customer.setCustomerEmail(petStoreCustomer.getCustomerEmail()); // Stores the customer's email address
	}

	/**
	 * Finds an existing pet store by ID or creates a new instance if ID is null.
	 * 
	 * @param petStoreId The ID of the pet store.
	 * @return An existing or new PetStore entity.
	 */
	private PetStore findOrCreatePetStore(Long petStoreId) {
		if (Objects.isNull(petStoreId)) {
			return new PetStore(); // Creates a new PetStore if ID is null
		} else {
			return findPetStoreById(petStoreId); // Retrieves existing PetStore by ID
		}
	}

	private Employee findOrCreateEmployee(Long petStoreId, Long employeeId) {
		if (Objects.isNull(employeeId)) {
			return new Employee();
		}
		return findEmployeeById(petStoreId, employeeId);
	}

	private Customer findOrCreateCustomer(Long petStoreId, Long customerId) {
		if (Objects.isNull(customerId)) {
			return new Customer();
		}
		return findCustomerById(petStoreId, customerId);
	}

	private Employee findEmployeeById(Long petStoreId, Long employeeId) {
		Employee employee = employeeDao.findById(employeeId)
				.orElseThrow(() -> new NoSuchElementException("Employee with ID+" + employeeId + " was not found."));

		if (employee.getPetStore().getPetStoreId() != petStoreId) {
			throw new IllegalArgumentException("The employee with ID=" + employeeId
					+ " is not employed by the pet store with ID=" + petStoreId + ".");
		}
		return employee;
	}

	private PetStore findPetStoreById(Long petStoreId) {
		return petStoreDao.findById(petStoreId)
				.orElseThrow(() -> new NoSuchElementException("Pet store with ID=" + petStoreId + " was not found."));
	}

	private Customer findCustomerById(Long petStoreId, Long customerId) {
		Customer customer = customerDao.findById(customerId)
				.orElseThrow(() -> new NoSuchElementException("Customer with ID+" + customerId + " was not found."));

		boolean found = false;

		for (PetStore petStore : customer.getPetStores()) {
			if (petStore.getPetStoreId().equals(petStoreId)) {
				found = true;
				break;
			}
		}
		if (!found) {
			throw new IllegalArgumentException(
					"The customer with ID=" + customerId + " is not a member of the pet store with ID=" + petStoreId);
		}

		return customer;
	}

	/**
	 * Retrieves all pet stores as a list of DTOs.
	 * 
	 * @return A list of all pet store data.
	 */
	@Transactional(readOnly = true) // Optimized for read-only transactions
	public List<PetStoreData> retrieveAllPetStores() {
		List<PetStore> petStores = petStoreDao.findAll();
		List<PetStoreData> response = new LinkedList<>();

		for (PetStore petStore : petStores) {
			response.add(new PetStoreData(petStore)); // Converts each entity to a DTO
		}
		return response;
	}

	/**
	 * Retrieves a pet store by its ID as a DTO.
	 * 
	 * @param petStoreId The ID of the pet store to retrieve.
	 * @return The corresponding pet store data.
	 */
	@Transactional(readOnly = true)
	public PetStoreData retrievePetStoreById(Long petStoreId) {
		PetStore petStore = findPetStoreById(petStoreId);
		return new PetStoreData(petStore);
	}

	/**
	 * Deletes a pet store by its ID.
	 * 
	 * @param petStoreId The ID of the pet store to delete.
	 */
	public void deletePetStore(Long petStoreId) {
		petStoreDao.deleteById(petStoreId);
	}

	@Transactional(readOnly = false)
	public PetStoreEmployee saveEmployee(Long petStoreId, PetStoreEmployee petStoreEmployee) {
	    try {
	        PetStore petStore = findPetStoreById(petStoreId);
	        Long employeeId = petStoreEmployee.getEmployeeId();
	        Employee employee = findOrCreateEmployee(petStoreId, employeeId);

	        copyEmployeeFields(employee, petStoreEmployee);
	        employee.setPetStore(petStore);
	        petStore.getEmployees().add(employee);

	        Employee dbEmployee = employeeDao.save(employee);
	        return new PetStoreEmployee(dbEmployee);
	    } catch (NoSuchElementException e) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet store not found with ID=" + petStoreId, e);
	    }
	}
	@Transactional
	public PetStoreCustomer saveCustomer(Long petStoreId, PetStoreCustomer petStoreCustomer) {
		PetStore petStore = findPetStoreById(petStoreId);
		Long customerId = petStoreCustomer.getCustomerId();
		Customer customer = findOrCreateCustomer(petStoreId, customerId);

		copyCustomerFields(customer, petStoreCustomer);

		customer.getPetStores().add(petStore);
		petStore.getCustomers().add(customer);

		Customer dbCustomer = customerDao.save(customer);
		return new PetStoreCustomer(dbCustomer);
	}
}
