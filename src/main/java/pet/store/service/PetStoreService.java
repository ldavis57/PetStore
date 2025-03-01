package pet.store.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pet.store.controller.model.PetStoreData;
import pet.store.dao.PetStoreDao;
import pet.store.entity.PetStore;

/**
 * Service layer for managing pet store operations. Provides methods for
 * creating, updating, retrieving, and deleting pet stores.
 */
@Service // Marks this class as a Spring service component
public class PetStoreService {

	@Autowired // Injects the PetStoreDao dependency
	private PetStoreDao petStoreDao;

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

	/**
	 * Finds a pet store by its ID, throwing an exception if not found.
	 * 
	 * @param petStoreId The ID of the pet store.
	 * @return The found PetStore entity.
	 * @throws NoSuchElementException if the pet store is not found.
	 */
	private PetStore findPetStoreById(Long petStoreId) {
		return petStoreDao.findById(petStoreId)
				.orElseThrow(() -> new NoSuchElementException("Pet store with ID=" + petStoreId + " was not found."));
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
}
