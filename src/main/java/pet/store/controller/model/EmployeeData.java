package pet.store.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

/**
 * DTO (Data Transfer Object) for employee entity. Encapsulates pet store
 * details along with associated customers and employees.
 */
@Data
@NoArgsConstructor
public class EmployeeData {

	private Long employeeId;
	private String employeeFirstName;
	private String employeeLastName;
	private String employeePhone;
	private String employeeJobTitle;
	//private Set<PetStoreEmployee> petStore = new HashSet<>();
	private PetStoreEmployee petStore;
	
	
	//private PetStoreEmployee petStore; // Represents the pet store the employee
	// works at

	/**
	 * Constructs a employeeData object from a employee entity. Copies relevant
	 * attributes and initializes collections of customers and employees.
	 * 
	 * @param employee The employee entity to convert.
	 */
	public EmployeeData(Employee employee) {
		employeeId = employee.getEmployeeId();
		employeeFirstName = employee.getEmployeeFirstName();
		employeeLastName = employee.getEmployeeLastName();
		employeePhone = employee.getEmployeePhone();
		employeeJobTitle = employee.getEmployeeJobTitle();
		
			    // Check if the method returns a collection
	    if (employee.getPetStore() != null) { 
            petStore = new PetStoreEmployee(employee.getPetStore()); 
	    }
	}


		// Converts associated Customer entities to DTO representations
//		for (Customer customer : employee.getPetStore()) {
//			customers.add(new employeeCustomer(customer));
//		}

	

	/**
	 * DTO for Customer entity associated with a employee.
	 */
	/**
	 * DTO for Employee entity associated with a employee.
	 */
	@Data
	@NoArgsConstructor
	public static class PetStoreEmployee {
		private Long petStoreId;
		private String petStoreName;
		private String petStoreAddress;
		private String petStoreCity;
		private String petStoreState;
		private String petStorePhone;

		/**
		 * Constructs a employeeEmployee DTO from an Employee entity.
		 * 
		 * @param employee The Employee entity to convert.
		 * @return
		 */
		public PetStoreEmployee(PetStore petStore) {
			petStoreId = petStore.getPetStoreId();
			petStoreName = petStore.getPetStoreName();
			petStoreAddress = petStore.getPetStoreAddress();
			petStoreCity = petStore.getPetStoreCity();
			petStoreState = petStore.getPetStoreState();
			petStorePhone = petStore.getPetStorePhone();
		}
	}
}
