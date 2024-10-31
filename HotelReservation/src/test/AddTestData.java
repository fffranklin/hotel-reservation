package test;

import api.AdminResource;
import api.HotelResource;
import model.IRoom;
import model.Room;
import model.RoomType;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddTestData {

    public static void createAccounts(HotelResource hotelResource) {
        hotelResource.createACustomer("magnolia@email.com", "Magnolia", "a Gata");
        hotelResource.createACustomer("antonio@email.com", "Antonio", "o Gato");
        hotelResource.createACustomer("cyndi123@email.com", "Cyndi", "Lauper");
        hotelResource.createACustomer("elzasoares@email.com", "Elza", "Soares");
        hotelResource.createACustomer("ritinha@email.com", "Rita", "Lee");
        hotelResource.createACustomer("elis@email.com", "Elis", "Regina");
    }

    public static void addRooms(AdminResource adminResource) {
        List<IRoom> rooms = new ArrayList<>();
        IRoom room;

        for (int i = 100; i < 106; i++){
            room = new Room(String.valueOf(i), 150.90, RoomType.DOUBLE, true);
            rooms.add(room);
        }

        for (int i = 200; i < 206; i++){
            room = new Room(String.valueOf(i), 130.80, RoomType.SINGLE, true);
            rooms.add(room);
        }
        adminResource.addRoom(rooms);
    }

    public static void addReservations(HotelResource hotelResource) {
        IRoom[] availableRooms = hotelResource.getAllRooms().toArray(new IRoom[0]);

        hotelResource.bookARoom("magnolia@email.com", availableRooms[0], getCheckInDate("2024-11-05"), getCheckOutDate("2024-11-10"));
        hotelResource.bookARoom("magnolia@email.com", availableRooms[5], getCheckInDate("2024-11-05"), getCheckOutDate("2024-11-10"));

        hotelResource.bookARoom("antonio@email.com", availableRooms[2], getCheckInDate("2024-01-02"), getCheckOutDate("2024-01-10"));
        hotelResource.bookARoom("antonio@email.com", availableRooms[6], getCheckInDate("2024-01-11"), getCheckOutDate("2024-01-15"));
        hotelResource.bookARoom("antonio@email.com", availableRooms[7], getCheckInDate("2024-01-16"), getCheckOutDate("2024-01-20"));

        hotelResource.bookARoom("ritinha@email.com", availableRooms[3], getCheckInDate("2024-12-18"), getCheckOutDate("2024-12-23"));
    }

    private static Date getCheckInDate(String checkInDate){
        LocalDate checkInLocalDate = LocalDate.parse(checkInDate, DateTimeFormatter.ISO_LOCAL_DATE);

        return Date.from(checkInLocalDate.atTime(14,0).atZone(ZoneId.systemDefault()).toInstant());
    }

    private static Date getCheckOutDate(String checkOutDate){
        LocalDate checkOutLocalDate = LocalDate.parse(checkOutDate, DateTimeFormatter.ISO_LOCAL_DATE);

        return Date.from(checkOutLocalDate.atTime(12,0).atZone(ZoneId.systemDefault()).toInstant());
    }
}