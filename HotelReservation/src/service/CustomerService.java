package service;

import model.Customer;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Fernanda Franklin
 */
public class CustomerService {

    private static CustomerService instance;
    private final Set<Customer> customers;

    private CustomerService() {
        customers = new HashSet<>();
    }

    public static CustomerService getInstance() {
        if (instance == null) {
            instance = new CustomerService();
        }
        return instance;
    }

    public void addCustomer (String email, String firstName, String lastName) {
        Customer newCustomer = new Customer(firstName, lastName, email);

        try{
            customers.add(newCustomer);
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public Customer getCustomer (String customerEmail) {
        for (Customer customer : customers) {
            if (customer.getEmail().equals(customerEmail)) {
                return customer;
            }
        }
        return null;
    }

    public Set<Customer> getAllCustomers() {
        return customers;
    }
}

