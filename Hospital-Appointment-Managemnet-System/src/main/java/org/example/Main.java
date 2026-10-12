package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

            Scanner sc = new Scanner(System.in);

            HospitalService hospitalService = new HospitalService();

            boolean running = true;

            do {
                System.out.println("\n=== Hospital Management System ===");
                System.out.println("1. Add Doctor");
                System.out.println("2. Add Patient");
                System.out.println("3. Book Appointment");
                System.out.println("4. Cancel Appointment");
                System.out.println("5. View Available Slots");
                System.out.println("6. View Doctor Schedule");
                System.out.println("7. View Patient History");
                System.out.println("8. Exit");

                System.out.print("\nSelect an option: ");
                int option = sc.nextInt();
                sc.nextLine();

                switch (option) {
                    case 1 -> {
                        hospitalService.addDoctor();
                    }

                    case 2 -> {
                        hospitalService.registerPatient();
                    }

                    case 3 -> {
                        hospitalService.bookAppointment();
                    }

                    case 4 -> {
                        hospitalService.cancelAppointment();
                    }

                    case 8 -> {
                        System.out.println("Thank you for using Hospital Management System");
                        running = false;
                    }

                    default -> {
                        System.out.println("Invalid option");
                    }
                }
            } while (running);

    }
}
