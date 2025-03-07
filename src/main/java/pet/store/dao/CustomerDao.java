package pet.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import pet.store.entity.Customer;

/**
 * Data Access Object (DAO) for Employee entity. Extends JpaRepository to
 * provide CRUD operations for Employee. JpaRepository provides built-in methods
 * like save, findById, findAll, deleteById, etc.
 */
public interface CustomerDao extends JpaRepository<Customer, Long> {
}
