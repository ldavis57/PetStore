package pet.store.entity; // Defines the package where this class belongs

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Represents an Employee entity in the pet store system. This class is mapped
 * to a database table using JPA annotations.
 */
@Entity // Marks this class as a JPA entity, mapping it to a database table
@Data // Lombok annotation to automatically generate getter, setter, equals, hashCode,
		// and toString methods
public class Employee {

	@Id // Marks this field as the primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// Specifies that the primary key will be auto-generated by the database using
	// an identity column
	private Long employeeId; // Unique identifier for an employee
	private String employeeFirstName; // First name of the employee
	private String employeeLastName; // Last name of the employee
	private String employeePhone; // Phone number of the employee
	private String employeeJobTitle; // Job title of the employee

	// Excludes  field from equals() and hashCode() methods to prevent circular
	// dependencies
	@EqualsAndHashCode.Exclude 
	@ToString.Exclude // Excludes this field from the toString() method to prevent infinite loops
	@ManyToOne(cascade = CascadeType.ALL)
	// Defines a many-to-one relationship with the PetStore entity.
	// - Many employees belong to one pet store.
	// - CascadeType.ALL ensures that any changes to an Employee (persist, merge,
	// remove, etc.) also affect the associated PetStore.
	@JoinColumn(name = "pet_store_id")
	// Specifies the foreign key column in the Employee table that references the
	// PetStore entity
	private PetStore petStore; // Reference to the associated pet store
}
