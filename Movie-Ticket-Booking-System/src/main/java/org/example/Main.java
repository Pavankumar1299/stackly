package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MovieBookingService movieBookingService = new MovieBookingService();
        movieBookingService.loadMovies();

        boolean running = true;

        do {
            System.out.println("\n===Welcome to Movie Ticket System===");
            System.out.println("1. Movie list");
            System.out.println("2. Book a movie ticket");
            System.out.println("3. Check seats");
            System.out.println("4. Quit");
            System.out.print("\nPlease enter your choice: ");
            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {

                case 1 -> {
                    movieBookingService.movieList();
                }

                case 2 -> {
                    System.out.print("Please enter movie code: ");
                    int movieId =  sc.nextInt();
                    sc.nextLine();

                    if (movieBookingService.movieExists(movieId)) {
                        System.out.print("Hom many seats do you want to book: ");
                        int seats = sc.nextInt();
                        sc.nextLine();

                        System.out.println("Choose your seat(s)");
                        movieBookingService.displaySeats(movieId);

                        System.out.println("Please enter row and column for book a seat: ");

                        for (int i = 0; i < seats; i++) {
                            System.out.println("Seat #" + (i + 1));
                            System.out.print("Seat #: ");
                            int column = sc.nextInt();

                            System.out.print("Row #: ");
                            int row = sc.nextInt();

                            try {
                                movieBookingService.bookSeat(movieId, row, column);
                            } catch (MovieException e) {
                                System.out.println(e.getMessage());
                                i--;
                            }
                        }

                        System.out.println("Seats booked successfully!");
                        double amount = movieBookingService.movieBill(movieId, seats);
                        System.out.println("Total amount: " + amount);
                    } else {
                        System.out.println("Movie not found!");
                    }
                }

                case 3 -> {
                    System.out.print("Enter movie ID: ");
                    int movieId =  sc.nextInt();
                    sc.nextLine();

                    if (movieBookingService.movieExists(movieId)) {
                        movieBookingService.displaySeats(movieId);
                    } else {
                        System.out.println("Movie does not exist!");
                    }
                }

                case 4 -> {
                    System.out.println("Thank you for using our system");
                    running = false;
                }
            }
        } while (running);
    }
}