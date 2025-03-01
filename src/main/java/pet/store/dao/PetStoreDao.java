package pet.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pet.store.entity.PetStore;

/**
 * Data Access Object (DAO) for PetStore entity. Extends JpaRepository to
 * provide CRUD operations for PetStore. JpaRepository provides built-in methods
 * like save, findById, findAll, deleteById, etc.
 */
public interface PetStoreDao extends JpaRepository<PetStore, Long> {
}
