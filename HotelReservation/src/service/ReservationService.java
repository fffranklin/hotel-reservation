package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

/**
 * @author Fernanda Franklin
 */
public class ReservationService {

    private static ReservationService instance;
    private final Set<IRoom> rooms;
    private final Map<String, List<Reservation>> reservations;

    private ReservationService() {
        rooms = new HashSet<>();
        reservations = new HashMap<>();
    }

    public static ReservationService getInstance() {
        if (instance == null) {
            instance = new ReservationService();
        }
        return instance;
    }

    public void addRoom(IRoom room){
        try{
            rooms.add(room);
        } catch (Exception e){
            e.getLocalizedMessage();
        }
    }

    public IRoom getARoom(String roomId){
        for (IRoom room : rooms) {
            if (room.getRoomNumber().equals(roomId)) {
                return room;
            }
        }
        return null;
    }

    public Set<IRoom> getAllRooms(){
        return rooms;
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);

        if (!reservations.containsKey(customer.getEmail())){
            reservations.put(customer.getEmail(), new ArrayList<>());
        }
        reservations.get(customer.getEmail()).add(reservation);

        return reservation;
    }

    public Set<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        Set<IRoom> availableRooms = new HashSet<>();
        for (IRoom room : rooms) {
            if(isRoomAvalaible(room, checkInDate, checkOutDate)){
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    private boolean isRoomAvalaible(IRoom room, Date checkInDate, Date checkOutDate) {
        List<Reservation> allReservations = getAllReservations();
        if (!allReservations.isEmpty()) {
            for (Reservation reservation : allReservations) {
                if (reservation.getRoom().equals(room) &&
                        !(checkOutDate.before(reservation.getCheckInDate()) || checkInDate.after(reservation.getCheckOutDate()))) {
                    return false;
                }
            }
        }
        return true;
    }

    private List<Reservation> getAllReservations() {
        List<Reservation> allReservations = new ArrayList<>();
        for (List<Reservation> reservations : reservations.values()) {
            allReservations.addAll(reservations);
        }
        return allReservations;
    }

    public List<Reservation> getCustomerReservations(Customer customer) {
        return reservations.get(customer.getEmail());
    }

    public void printAllReservations(){
        if (reservations.isEmpty()){
            System.out.println("No reservations found");
        } else {
            List<Reservation> allReservations = getAllReservations();
            for (Reservation reservation : allReservations) {
                System.out.println(reservation);
            }
        }
    }
}
