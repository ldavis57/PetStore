package pet.store; // Defines the package where this class belongs

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point of the Pet Store application.
 * This class is responsible for bootstrapping the Spring Boot application.
 */
@SpringBootApplication 
// This annotation enables Spring Boot's auto-configuration, component scanning, and configuration support.
public class PetStoreApplication {

    /**
     * The main method serves as the entry point for the Spring Boot application.
     * It starts the embedded server and initializes the Spring application context.
     * 
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(PetStoreApplication.class, args);
        // This method launches the application by running the PetStoreApplication class.
    }
}
