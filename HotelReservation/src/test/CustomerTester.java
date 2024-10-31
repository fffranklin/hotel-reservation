package test;

import model.Customer;

public class CustomerTester {
    public static void main(String[] args) {
        Customer customerMagnolia = new Customer("Magnólia", "The Cat", "magnolia@gmail.com");

        System.out.println(customerMagnolia);

        Customer customerAntonio = new Customer("Antonio", "Antonio", "no email");

        System.out.println(customerAntonio);
    }
}

