package ui;

import api.AdminResource;
import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;
import util.Validator;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class MainMenu {

    public static void showHotelApplicationMainMenu(Scanner scanner, AdminResource adminResource, HotelResource hotelResource) {
        boolean running = true;
        AdminMenu adminMenu = new AdminMenu();

        while (running) {
            MainMenu.showMainMenu();
            int choice = Validator.validateIfInputIsANumber(scanner.nextLine());

            switch (choice) {
                case 1:
                    showReserveARoomMenu(scanner, hotelResource);
                    break;
                case 2:
                    showMyReservationsMenu(scanner, hotelResource);
                    break;
                case 3:
                    MainMenu.showCreateAnAccountMenu(scanner, hotelResource);
                    break;
                case 4:
                    adminMenu.showHotelApplicationAdminMenu(scanner, adminResource, hotelResource);
                    running = false;
                    break;
                case 5:
                    running = false;
                    System.out.println("Exiting the Hotel Application Application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid Input. Please enter a number between 1 and 5!");
            }
        }
    }

    private static void showMainMenu() {
        System.out.println( """
                
                Welcome to the Hotel Application
                --------------------------------------------
                1. Find and reserve a room
                2. See my reservations
                3. Create an account
                4. Admin
                5. Exit
                --------------------------------------------
                Please select a number for the menu option:""");
    }

    private static void showReserveARoomMenu(Scanner scanner, HotelResource hotelResource) {
        Date checkInDate;
        Date checkOutDate;
        Reservation  newReservation = null;

        LocalDate checkInLocalDate = getInputCheckInDate(scanner);
        checkInDate =  Date.from(checkInLocalDate.atTime(14,0).atZone(ZoneId.systemDefault()).toInstant());

        LocalDate checkOutLocalDate = getInputCheckOutDate(scanner, checkInLocalDate);
        checkOutDate = Date.from(checkOutLocalDate.atTime(12,0).atZone(ZoneId.systemDefault()).toInstant());

        Set<IRoom> availableRooms = hotelResource.findARoom(checkInDate, checkOutDate);

        if (availableRooms.isEmpty()) {
            checkInDate = getRecommendationDate(checkInDate);
            checkOutDate = getRecommendationDate(checkOutDate);

            availableRooms = hotelResource.findARoom(checkInDate, checkOutDate);

            if (!availableRooms.isEmpty()) {
                System.out.println();
                System.out.println("There are no available rooms for this reservation period!");
                System.out.println();
                System.out.println("We suggest you to book a room from this period: ");
                System.out.println("CHECK-IN --> " + checkInDate);
                System.out.println("CHECK-OUT --> " + checkOutDate);
                System.out.println();
            }
        }

        showAvailableRoomsMenu(availableRooms);

        if (!availableRooms.isEmpty()){
            System.out.println("Would you like to book a room? (Y/N): ");
            boolean yesOrNo = isInputYesOrNo(scanner);

            if (yesOrNo) {
                System.out.println("Do you have a account with us? (Y/N): ");
                boolean hasAccount = isInputYesOrNo(scanner);
                if (hasAccount) {
                    newReservation = showMakeAReservationMenu(scanner, availableRooms, checkInDate, checkOutDate, hotelResource);
                } else {
                    System.out.println("Please go back to the main menu and select option 3 to create an account.");
                }
            }
        }

        if (newReservation !=  null){
            System.out.println("Reservation Successfully done!");
            System.out.println("Customer: " + newReservation.getCustomer().getFirstName() + " " + newReservation.getCustomer().getLastName());
            System.out.println("Room: " + newReservation.getRoom().getRoomNumber() + " - " + newReservation.getRoom().getRoomType());
            System.out.println("Check-in Date: " + newReservation.getCheckInDate());
            System.out.println("Check-out Date: " + newReservation.getCheckOutDate());
        }

    }

    private static void showAvailableRoomsMenu(Set<IRoom> availableRooms) {
        if (availableRooms.isEmpty()) {
            System.out.println();
            System.out.println("There are no available rooms for this reservation period!");
        } else {
            System.out.println("There are " + availableRooms.size() + " available rooms for this reservation period:");
            for (IRoom room : availableRooms){
                System.out.println(room);
            }
        }
    }

    private static Reservation showMakeAReservationMenu(Scanner scanner, Set<IRoom> availableRooms, Date checkInDate, Date checkOutDate, HotelResource hotelResource) {
        String email;
        Customer customer;
        IRoom roomToReserve;
        Reservation reservation = null;

        boolean runningMakeAReservationMenu = true;

        while (runningMakeAReservationMenu) {
            System.out.println("Enter Email format 'name@domain.com': ");
            email = scanner.nextLine();

            if (Validator.isValidEmail(email)){
                customer = hotelResource.getCustomer(email);
                if (customer != null) {
                    roomToReserve = getRoomForReserveMenu(scanner, availableRooms);

                    if (roomToReserve != null) {
                        reservation = hotelResource.bookARoom(email, roomToReserve, checkInDate, checkOutDate);
                    }
                    runningMakeAReservationMenu = false;
                } else {
                    System.out.println("Customer email does not exists. Please back to main menu and create an account or inform existing customer email!");
                    runningMakeAReservationMenu = false;
                }
            } else {
                System.out.println("Invalid email format. Please enter a valid email address!");
            }
        }
        return reservation;
    }

    private static IRoom getRoomForReserveMenu(Scanner scanner, Set<IRoom> availableRooms){
        IRoom roomToReserve = null;
        boolean runningGetRoomNumberMenu = true;

        while (runningGetRoomNumberMenu) {
            System.out.println("What room number would you like to make a reservation?");
            int numberEntered = Validator.validateIfInputIsANumber(scanner.nextLine());
            if (numberEntered <= 0) {
                System.out.println("Enter a valid Room Number!");
            } else {
                roomToReserve = getRoomFromAvailableRooms(numberEntered, availableRooms);

                if (roomToReserve == null) {
                    System.out.println("Enter a number of a Room from the available rooms list!");
                } else{
                    runningGetRoomNumberMenu = false;
                }
            }
        }
        return roomToReserve;
    }

    private static void showMyReservationsMenu(Scanner scanner, HotelResource hotelResource) {
        boolean runningMyReservationsMenu = true;
        String email;
        List<Reservation> myReservations;

        while (runningMyReservationsMenu) {
            System.out.println("Enter your Email (name@domain.com): ");
            email = scanner.nextLine();

            if (Validator.isValidEmail(email)){
                myReservations = hotelResource.getCustomersReservations(email);

                if (myReservations != null) {
                    System.out.println("Here are the reservation(s) for the Customer " + email + ":");
                    for (Reservation reservation : myReservations) {
                        System.out.println(reservation.toString());
                    }
                } else {
                    System.out.println("No reservation(s) found the Customer " + email + "!");
                }
                runningMyReservationsMenu = false;
            } else{
                System.out.println("Invalid email format. Please enter a valid email address!");
            }
        }
    }

    public static void showCreateAnAccountMenu(Scanner scanner, HotelResource hotelResource) {
        String email;
        String firstName;
        String lastName;

        boolean runningCreateAnAccountMenu = true;

        while (runningCreateAnAccountMenu) {
            System.out.println("Enter email format 'name@domain.com'");
            email = scanner.nextLine();

            if (Validator.isValidEmail(email)){
                System.out.println("Enter first name");
                firstName = scanner.nextLine();
                System.out.println("Enter last name");
                lastName = scanner.nextLine();
                hotelResource.createACustomer(email, firstName, lastName);
                System.out.println("Welcome to the Hotel Reservation Application");
                runningCreateAnAccountMenu = false;
            } else {
                System.out.println("Invalid email format. Please enter a valid email address!");
            }
        }
    }

    private static LocalDate getInputCheckInDate(Scanner scanner){
        boolean runningCheckInMenu = true;
        LocalDate checkInDate = null;

        while (runningCheckInMenu) {
            System.out.println("Enter Check-In Date (YYYY-MM-DD)");
            String input = scanner.nextLine();

            try{
                checkInDate = LocalDate.parse(input, DateTimeFormatter.ISO_LOCAL_DATE);

                if (checkInDate.isBefore(LocalDate.now())){
                    System.out.println("Please enter a Check-In Date on or after today!");
                } else {
                    runningCheckInMenu = false;
                }

            } catch (DateTimeParseException e){
                System.out.println("Invalid Date format. Please use YYYY-MM-DD!");
            }
        }
        return checkInDate;
    }

    private static LocalDate getInputCheckOutDate(Scanner scanner, LocalDate checkInDate){
        boolean runningCheckOutMenu = true;
        LocalDate checkOutDate = LocalDate.now();

        while (runningCheckOutMenu) {
            System.out.println("Enter Check-Out Date (YYYY-MM-DD)");
            String input = scanner.nextLine();
            try{
                checkOutDate = LocalDate.parse(input, DateTimeFormatter.ISO_LOCAL_DATE);

                if (checkOutDate.isBefore(checkInDate) || checkOutDate.isEqual(checkInDate)){
                    System.out.println("Please enter a Check-Out Date after the Check-In Date "+ checkOutDate);
                } else {
                    runningCheckOutMenu = false;
                }
            } catch (DateTimeParseException e){
                System.out.println("Invalid Date format. Please use YYYY-MM-DD!");
            }
        }
        return checkOutDate;
    }

    private static Date getRecommendationDate(Date dateToChange){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateToChange);

        int daysToAdd = 7;
        calendar.add(Calendar.DAY_OF_MONTH, daysToAdd);

        return calendar.getTime();
    }

    private static boolean isInputYesOrNo(Scanner scanner) {
        boolean runningGetYesOrNoMenu = true;
        boolean YesOrNo = false;

        while (runningGetYesOrNoMenu) {
            String yesOrNo = scanner.nextLine();
            if (yesOrNo.equalsIgnoreCase("n")) {
                runningGetYesOrNoMenu = false;
            } else if (yesOrNo.equalsIgnoreCase("y")) {
                YesOrNo = true;
                runningGetYesOrNoMenu = false;
            } else {
                System.out.println("Please enter Y (Yes) or N (No)");
            }
        }
        return YesOrNo;
    }

    private static IRoom getRoomFromAvailableRooms(int roomNumber,  Set<IRoom> availableRooms) {
        for (IRoom room : availableRooms)
            if (room.getRoomNumber().equals(String.valueOf(roomNumber))) {
                return room;
            }
        return null;
    }
}



