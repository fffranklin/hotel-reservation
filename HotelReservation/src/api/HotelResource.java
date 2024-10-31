package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Fernanda Franklin
 */
public class HotelResource {
    private static HotelResource instance;

    private final CustomerService customerService;
    private final ReservationService reservationService;

    private HotelResource(CustomerService customerService, ReservationService reservationService) {
        this.customerService = customerService;
        this.reservationService = reservationService;
    }

    public static HotelResource getInstance() {
        if (instance == null){
            instance = new HotelResource(CustomerService.getInstance(), ReservationService.getInstance());
        }
        return instance;
    }

    public Customer getCustomer(String email){
        return customerService.getCustomer(email);
    }

    public void createACustomer(String email, String firstName, String lastName){
        customerService.addCustomer(email, firstName, lastName);
    }

    public IRoom getRoom(String roomNumber){
        return reservationService.getARoom(roomNumber);
    }

    public Set<IRoom> getAllRooms(){
        return reservationService.getAllRooms();
    }

    public Reservation bookARoom(String CustomerEmail, IRoom room, Date checkInDate, Date checkOutDate){
        Customer customer = customerService.getCustomer(CustomerEmail);

        return reservationService.reserveARoom(customer, room, checkInDate, checkOutDate);
    }

    public List<Reservation> getCustomersReservations(String customerEmail){
        Customer customer = customerService.getCustomer(customerEmail);

        if (customer == null){
            return null;
        }

        return reservationService.getCustomerReservations(customer);
    }

    public Set<IRoom> findARoom(Date checkInDate, Date checkOutDate){
        return reservationService.findRooms(checkInDate, checkOutDate);
    }
}
