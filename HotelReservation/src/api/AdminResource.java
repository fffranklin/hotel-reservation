package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.List;
import java.util.Set;

/**
 * @author Fernanda
 */
public class AdminResource {

    private static AdminResource instance;
    private final CustomerService customerService;
    private final ReservationService reservationService;

    private AdminResource(CustomerService customerService, ReservationService reservationService) {
        this.customerService = customerService;
        this.reservationService = reservationService;
    }

    public static AdminResource getInstance() {
        if (instance == null) {
            instance = new AdminResource(CustomerService.getInstance(), ReservationService.getInstance());
        }
        return instance;
    }

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void addRoom(List<IRoom> rooms){
        for (IRoom room : rooms) {
            reservationService.addRoom(room);
        }
    }

    public Set<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    public void displayAllRooms(){
        Set<IRoom> allRooms = reservationService.getAllRooms();

        if (allRooms.isEmpty()) {
            System.out.println("No rooms found");
        } else {
            for (IRoom room : allRooms) {
                System.out.println(room);
            }
        }
    }

    public void displayAllCustomers(){
        Set<Customer> allCustomers = getAllCustomers();

        if (allCustomers.isEmpty()) {
            System.out.println("No customers found");
        } else {
            for (Customer customer : allCustomers) {
                System.out.println(customer);
            }
        }
    }

    public void displayAllReservations() {
        reservationService.printAllReservations();
    }
}
