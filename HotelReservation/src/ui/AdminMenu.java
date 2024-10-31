package ui;

import api.AdminResource;
import api.HotelResource;
import model.IRoom;
import model.Room;
import model.RoomType;
import test.AddTestData;
import util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {

    private static List<IRoom> rooms;

    public AdminMenu(){
        rooms = new ArrayList<>();
    }

    public void showHotelApplicationAdminMenu(Scanner scanner, AdminResource adminResource, HotelResource hotelResource) {
        boolean runningAdminMenu = true;

        while (runningAdminMenu) {
            showAdminMenu();
            int choice = Validator.validateIfInputIsANumber(scanner.nextLine());

            switch (choice) {
                case 1:
                    adminResource.displayAllCustomers();
                    break;
                case 2:
                    adminResource.displayAllRooms();
                    break;
                case 3:
                    adminResource.displayAllReservations();
                    break;
                case 4:
                    showAddRoomMenu(scanner, adminResource, hotelResource);
                    break;
                case 5:
                    addTestData(adminResource, hotelResource);
                    break;
                case 6:
                    runningAdminMenu = false;
                    MainMenu.showHotelApplicationMainMenu(scanner, adminResource, hotelResource);
                    break;
                default:
                    System.out.println( "Invalid input. Please enter a number between 1 and 5!");
            }
        }
    }

    public static void showAdminMenu() {
        System.out.println( """
                
                Admin Menu
                --------------------------------------------
                1. See all Customers
                2. See all Rooms
                3. See all Reservations
                4. Add a Room
                5. Add Test Data
                6. Back to Main Menu
                --------------------------------------------
                Please select a number for the menu option:""");
    }

    public static void showAddRoomMenu(Scanner scanner, AdminResource adminResource, HotelResource hotelResource) {
        String roomNumber;
        double roomPrice;
        RoomType roomType;
        boolean isFreeDefault = true;
        boolean runningAddRoomMenu = true;

        while (runningAddRoomMenu) {
            roomNumber = getInputRoomNumber(scanner, hotelResource);
            roomPrice = getInputRoomPrice(scanner);
            roomType = getInputRoomType(scanner);

            Room room = new Room(roomNumber, roomPrice, roomType, isFreeDefault);
            rooms.add(room);

            System.out.println("Room " + roomNumber + " has been successfully added!");
            System.out.println("Would you like to add a new room? (y/n): ");

            if (!isAddAnotherRoom(scanner)) {
                runningAddRoomMenu = false;
            }
        }
        adminResource.addRoom(rooms);
    }

    private static String getInputRoomNumber(Scanner scanner, HotelResource hotelResource){
        String roomNumber = null;
        boolean runningGetRoomNumberMenu = true;

        while (runningGetRoomNumberMenu) {
            System.out.println("Enter Room Number:");
            int numberEntered = Validator.validateIfInputIsANumber(scanner.nextLine());
            if (numberEntered <= 0) {
                System.out.println("Enter a valid Room Number!");
            } else {
                if (isRoomAlreadyExists(String.valueOf(numberEntered), hotelResource)) {
                    System.out.println("Room already exists!");
                } else {
                    roomNumber = String.valueOf(numberEntered);
                    runningGetRoomNumberMenu = false;
                }
            }
        }
        return roomNumber;
    }

    private static double getInputRoomPrice(Scanner scanner){
        double roomPrice = 0;
        boolean runningGetRoomPriceMenu = true;

        while (runningGetRoomPriceMenu) {
            System.out.println("Enter Price per night:");
            double priceEntered = Validator.validateIfInputIsDouble(scanner.nextLine());
            if (priceEntered <= 0) {
                System.out.println("Enter a valid value for Price!");
            } else{
                roomPrice = priceEntered;
                runningGetRoomPriceMenu = false;
            }
        }
        return roomPrice;
    }

    private static RoomType getInputRoomType(Scanner scanner){
        int roomType = 1;
        boolean runningGetRoomTypeMenu = true;

        while (runningGetRoomTypeMenu) {
            System.out.println("Enter room type: 1 for single bed, 2 for double bed");
            roomType = Validator.validateIfInputIsANumber(scanner.nextLine());
            if (roomType == 1 || roomType == 2) {
                runningGetRoomTypeMenu = false;
            } else{
                System.out.println("Invalid Type. Please enter number  1 or number 2!");
            }
        }
        return switch (roomType) {
            case 1 -> RoomType.SINGLE;
            case 2 -> RoomType.DOUBLE;
            default -> null;
        };
    }

    private static boolean isAddAnotherRoom(Scanner scanner) {
        boolean runningGetYesOrNoMenu = true;
        boolean YesOrNo = false;

        while (runningGetYesOrNoMenu) {
            String yesOrNo = scanner.nextLine();
            if (yesOrNo.equalsIgnoreCase("n")) {
                runningGetYesOrNoMenu = false;
            } else if (yesOrNo.equalsIgnoreCase("y")) {
                System.out.println("Adding a new room... ");
                YesOrNo = true;
                runningGetYesOrNoMenu = false;
            } else {
                System.out.println("Please enter Y (Yes) or N (No)");
            }
        }
        return YesOrNo;
    }

    private static boolean isRoomAlreadyExists(String roomNumber, HotelResource hotelResource) {
        for (IRoom room : rooms)
            if (room.getRoomNumber().equals(roomNumber)) {
                return true;
            }
        return hotelResource.getRoom(String.valueOf(roomNumber)) != null;
    }

    private static void addTestData(AdminResource adminResource, HotelResource hotelResource) {
        System.out.println("Adding Cccounts...");
        AddTestData.createAccounts(hotelResource);

        System.out.println("Adding Rooms...");
        AddTestData.addRooms(adminResource);

        System.out.println("Adding Reservations...");
        AddTestData.addReservations(hotelResource);

        System.out.println("Test Data successfully added!");
    }
}
