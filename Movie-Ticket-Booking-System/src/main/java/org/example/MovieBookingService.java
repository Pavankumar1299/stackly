package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MovieBookingService {

    Scanner sc = new Scanner(System.in);

    Map<Integer, Movie> movies = new HashMap<>();
    Map<Integer, int[][]> bookings = new HashMap<>();

    public void loadMovies() {

        movies.put(111, new Movie(111, "Life of PI", 350, 3.5));
        movies.put(112, new Movie(112, "BRB", 400, 4.5));
        movies.put(113, new Movie(113, "Legend of Sang-Chi", 300, 4.0));
        movies.put(114, new Movie(114, "Let me Live or Love", 250, 3.5));
        movies.put(115, new Movie(115, "Your Name.", 250, 4.5));

        // Separate seat arrangement for every movie
        bookings.put(111, new int[10][7]);
        bookings.put(112, new int[10][7]);
        bookings.put(113, new int[10][7]);
        bookings.put(114, new int[10][7]);
        bookings.put(115, new int[10][7]);
    }

    public boolean movieExists(int movieId) {
        return movies.containsKey(movieId);
    }

    public void movieList() {

        System.out.println("Please find your movies along with ID");

        for (Movie movie : movies.values()) {
            System.out.println(movie);
        }
    }

    public void displaySeats(int movieId) {

        int[][] seats = bookings.get(movieId);

        System.out.print("Seats:\n\t");

        for (int i = 0; i < seats[0].length; i++) {
            System.out.print("\tS" + (i + 1));
        }

        System.out.println();

        for (int i = 0; i < seats.length; i++) {

            System.out.print("Row " + (i + 1) + "\t");

            for (int j = 0; j < seats[i].length; j++) {
                System.out.print("[" + seats[i][j] + "]\t");
            }

            System.out.println();
        }
    }

    public void bookSeat(int movieId, int row, int col) throws MovieException {

        int[][] seats = bookings.get(movieId);

        // User enters 1-based row and column
        if (row < 1 || row > seats.length ||
                col < 1 || col > seats[0].length) {

            throw new MovieException("Invalid row or column");
        }

        // Convert to zero-based array index
        row--;
        col--;

        if (seats[row][col] == 0) {

            seats[row][col] = 1;

            System.out.println(
                    "Seat " + (row + 1) + ", " + (col + 1)
                            + " has been booked."
            );

        } else {

            throw new MovieException("Seat is not available");
        }
    }

    public double movieBill(int movieId, int numberOfSeats) {

        double price = movies.get(movieId).getTicketPrice();

        return price * numberOfSeats;
    }
}