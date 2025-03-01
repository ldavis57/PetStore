package pet.store.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

/**
 * DTO (Data Transfer Object) for PetStore entity. Encapsulates pet store
 * details along with associated customers and employees.
 */
@Data
@NoArgsConstructor
public class PetStoreData {

	private Long petStoreId;
	private String petStoreName;
	private String petStoreAddress;
	private String petStoreCity;
	private String petStoreState;
	private String petStoreZip;
	private String petStorePhone;
	private Set<PetStoreCustomer> customers = new HashSet<>();
	private Set<PetStoreEmployee> employees = new HashSet<>();

	/**
	 * Constructs a PetStoreData object from a PetStore entity. Copies relevant
	 * attributes and initializes collections of customers and employees.
	 * 
	 * @param petStore The PetStore entity to convert.
	 */
	public PetStoreData(PetStore petStore) {
		petStoreId = petStore.getPetStoreId();
		petStoreName = petStore.getPetStoreName();
		petStoreAddress = petStore.getPetStoreAddress();
		petStoreCity = petStore.getPetStoreCity();
		petStoreZip = petStore.getPetStoreZip();
		petStorePhone = petStore.getPetStorePhone();

		// Converts associated Customer entities to DTO representations
		for (Customer customer : petStore.getCustomers()) {
			customers.add(new PetStoreCustomer(customer));
		}

		// Converts associated Employee entities to DTO representations
		for (Employee employee : petStore.getEmployees()) {
			employees.add(new PetStoreEmployee(employee));
		}
	}

	/**
	 * DTO for Customer entity associated with a PetStore.
	 */
	@Data
	@NoArgsConstructor
	public static class PetStoreCustomer {
		private Long customerId;
		private String customerFirstName;
		private String customerLastName;
		private String customerEmail;

		/**
		 * Constructs a PetStoreCustomer DTO from a Customer entity.
		 * 
		 * @param customer The Customer entity to convert.
		 */
		public PetStoreCustomer(Customer customer) {
			customerId = customer.getCustomerId();
			customerFirstName = customer.getCustomerFirstName();
			customerLastName = customer.getCustomerLastName();
			customerEmail = customer.getCustomerEmail();
		}
	}

	/**
	 * DTO for Employee entity associated with a PetStore.
	 */
	@Data
	@NoArgsConstructor
	public static class PetStoreEmployee {
		private Long employeeId;
		private String employeeFirstName;
		private String employeeLastName;
		private String employeePhone;
		private String employeeJobTitLe;

		/**
		 * Constructs a PetStoreEmployee DTO from an Employee entity.
		 * 
		 * @param employee The Employee entity to convert.
		 */
		public PetStoreEmployee(Employee employee) {
			employeeId = employee.getEmployeeId();
			employeeFirstName = employee.getEmployeeFirstName();
			employeeLastName = employee.getEmployeeLastName();
			employeePhone = employee.getEmployeePhone();
			employeeJobTitLe = employee.getEmployeeJobTitle();
		}
	}
}
