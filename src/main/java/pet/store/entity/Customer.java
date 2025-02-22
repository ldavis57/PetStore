package pet.store.entity; // Defines the package for the entity class

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity // Marks this class as a JPA entity, meaning it will be mapped to a database table
@Data // Lombok annotation to generate boilerplate code like getters, setters, and toString()
public class Customer { 

    @Id // Specifies the primary key for the entity
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generates the primary key using database identity column
    private Long customerId; // Unique identifier for each customer
    
    private String customerFirstName; // Stores the customer's first name
    private String customerLastName;  // Stores the customer's last name
    private String customerEmail;     // Stores the customer's email address
    
    @EqualsAndHashCode.Exclude // Excludes this field from equals() and hashCode() methods (prevents infinite loops in bidirectional relationships)
    @ToString.Exclude // Excludes this field from the generated toString() method (prevents infinite loops)
    @ManyToMany(mappedBy = "customers", cascade = CascadeType.PERSIST) 
    // Defines a many-to-many relationship with the PetStore entity.
    // The "mappedBy" attribute indicates that PetStore owns the relationship.
    // CascadeType.PERSIST ensures that when a Customer is persisted, its associated PetStores are also persisted.
    private Set<PetStore> petStores = new HashSet<>(); // Initializes the collection to prevent null references

}
