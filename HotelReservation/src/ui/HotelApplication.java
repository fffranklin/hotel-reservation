package ui;

import api.AdminResource;
import api.HotelResource;

import java.util.Scanner;

public class HotelApplication {

    private static Scanner scanner;
    private static HotelResource hotelResource;
    private static AdminResource adminResource;

    public static void main(String[] args) {
        initializeScanner();
        initializeResources();
        MainMenu.showHotelApplicationMainMenu(scanner, adminResource, hotelResource);
        scanner.close();
    }

    private static void initializeScanner() {
        scanner = new Scanner(System.in);
    }

    private static void initializeResources() {
        hotelResource = HotelResource.getInstance();
        adminResource = AdminResource.getInstance();
    }
}
