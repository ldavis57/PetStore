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
	@PostMapping("/{petStoreId}/employee")
	@ResponseStatus(code = HttpStatus.CREATED) // Returns HTTP 201 Created on success
	public PetStoreEmployee addEmployeeToPetStore(@PathVariable Long petStoreId,
			@RequestBody PetStoreEmployee petStoreEmployee) { // @RequestBody: body is json
		log.info("Adding employee {} to pet store with ID={}", petStoreEmployee, petStoreId);
		return petStoreService.saveEmployee(petStoreId, petStoreEmployee);
	}

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
	 * @param petStoreId   The ID of the pet store to update.
	 * @param petStoreData The updated pet store details.
	 * @return The updated pet store data.
	 */
	@PutMapping("/pet_store/{petStoreId}")
	public PetStoreData updatePetStore(@PathVariable Long petStoreId, @RequestBody PetStoreData petStoreData) {
		petStoreData.setPetStoreId(petStoreId); // Ensures the correct ID is set
		log.info("Updating pet store with ID: {}", petStoreData);
		return petStoreService.savePetStore(petStoreData);
	}

	/**
	 * Retrieves all pet stores.
	 * 
	 * @return A list of all stored pet stores.
	 * 
	 *         RETRIEVE
	 */
	@GetMapping
	public List<PetStoreData> retrieveAllPetStores() {
		log.info("Retrieving all pet stores");
		return petStoreService.retrieveAllPetStores();
	}

	/**
	 * Retrieves a specific pet store by its ID.
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
	 * Deletes a specific pet store by its ID.
	 * 
	 * @param petStoreId The ID of the pet store to retrieve.
	 * @return The pet store data if found.
	 */
	// HTTP DELETE
	@DeleteMapping("/pet_store/{petStoreId}")
	public void deletePetStoreById(@PathVariable Long petStoreId) {
		log.info("deletePetStoreById {}");
		petStoreService.deletePetStore(petStoreId);
	}
}
