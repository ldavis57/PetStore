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

@Service // Marks this class as a Spring service component
public class PetStoreService {
	@Autowired // Injects the PetStoreDao dependency
	private PetStoreDao petStoreDao;

	/*
	 * POST and PUT use this method to create or update a record
	 */
	@Transactional(readOnly = false) // Marks this method as transactional and allows write operations
	public PetStoreData savePetStore(PetStoreData petStoreData) {
		Long petStoreId = petStoreData.getPetStoreId();
		PetStore petStore = findOrCreatePetStore(petStoreId);

		copyPetStoreFields(petStore, petStoreData); // Copies fields from Data Transfer Object (DTO) to entity
		return new PetStoreData(petStoreDao.save(petStore)); // Saves entity and returns DTO
	}

	// Copies fields from PetStoreData DTO to PetStore entity
	private void copyPetStoreFields(PetStore petStore, PetStoreData petStoreData) {
		petStore.setPetStoreAddress(petStoreData.getPetStoreAddress());
		petStore.setPetStoreCity(petStoreData.getPetStoreCity());
		petStore.setPetStoreId(petStoreData.getPetStoreId());
		petStore.setPetStoreName(petStoreData.getPetStoreName());
		petStore.setPetStorePhone(petStoreData.getPetStorePhone());
		petStore.setPetStoreState(petStoreData.getPetStoreState());
		petStore.setPetStoreZip(petStoreData.getPetStoreZip());
	}

	// Finds an existing PetStore by ID or creates a new one if ID is null
	private PetStore findOrCreatePetStore(Long petStoreId) {
		if (Objects.isNull(petStoreId)) {
			return new PetStore(); // Creates a new PetStore if ID is null
		} else {
			return findPetStoreById(petStoreId); // Finds existing PetStore by ID
		}
	}

	// Retrieves a PetStore entity by ID, throws exception if not found
	private PetStore findPetStoreById(Long petStoreId) {
		return petStoreDao.findById(petStoreId)
				.orElseThrow(() -> new NoSuchElementException("Pet store with ID=" + petStoreId + " was not found."));
	}

	/*
	 * Used for GET 
	 */
	@Transactional(readOnly = true) // Marks this method as read-only for better performance
	public List<PetStoreData> retrieveAllPetStores() {
		List<PetStore> petStores = petStoreDao.findAll();
		List<PetStoreData> response = new LinkedList<>(); // Using LinkedList; consider ArrayList for efficiency

		for (PetStore petStore : petStores) {
			response.add(new PetStoreData(petStore)); // Converts each entity to DTO
		}
		return response;
	}

	/*
	 * Used for GET by ID
	 */
	@Transactional(readOnly = true) // Retrieves a single PetStore by ID as a DTO
	public PetStoreData retrievePetStoreById(Long petStoreId) {
		PetStore petStore = findPetStoreById(petStoreId);
		return new PetStoreData(petStore);
	}
}
