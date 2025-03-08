package pet.store.entity; // Defines the package where this class belongs

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Represents a PetStore entity in the pet store system. This class is mapped to
 * a database table using JPA annotations.
 */
@Entity // Marks this class as a JPA entity, mapping it to a database table
@Data // Lombok annotation that generates getter, setter, equals, hashCode, and
		// toString methods automatically
public class PetStore {

	@Id // Marks this field as the primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// Specifies that the primary key will be automatically generated using the
	// database identity column
	private Long petStoreId; // Unique identifier for a pet store
	private String petStoreName; // Name of the pet store
	private String petStoreAddress; // Address of the pet store
	private String petStoreCity; // City where the pet store is located
	private String petStoreState; // State where the pet store is located
	private String petStoreZip; // ZIP code of the pet store
	private String petStorePhone; // Contact phone number of the pet store

	@ManyToMany(cascade = CascadeType.PERSIST)
	// Defines a many-to-many relationship with the Customer entity.
	// CascadeType.PERSIST ensures that when a PetStore is persisted, its associated
	// Customers are also persisted.

	@JoinTable(name = "pet_store_customer", // Name of the join table for the many-to-many relationship
			joinColumns = @JoinColumn(name = "pet_store_id"), // Foreign key column for PetStore in the join table
			inverseJoinColumns = @JoinColumn(name = "customer_id") // Foreign key column for Customer in the join table
	)
	// @formatter:off
	@EqualsAndHashCode.Exclude // Excludes  field from equals() and hashCode() methods to prevent infinite loops
	@ToString.Exclude // Excludes this field from the toString() method to avoid circular references
	// @formatter:on
	private Set<Customer> customers = new HashSet<>(); // Initializes the Set to prevent NullPointerException issues

	@OneToMany(mappedBy = "petStore", cascade = CascadeType.ALL, orphanRemoval = true)

	// Defines a one-to-many relationship with the Employee entity. -
	// "mappedBy = 'petStore'" means this field is mapped by the "petStore" field in
	// the Employee class. - CascadeType.ALL ensures that all operations (persist,
	// merge, remove, refresh, detach) are cascaded to Employees. - orphanRemoval =
	// true means if an Employee is removed from this set, it is deleted from the
	// database.

	// @formatter:off
	@EqualsAndHashCode.Exclude // Excludes this field from equals() and hashCode() methods to prevent infinite loops
	@ToString.Exclude // Excludes this field from the toString() method to avoid circular references
	// @formatter:on	
	private Set<Employee> employees = new HashSet<>(); // Initializes the Set to prevent NullPointerException issues

}
