package org.example;

public class Movie {

    private int movieId;
    private String movieName;
    private double ticketPrice;
    private double rating;

    public Movie(int movieId, String movieName, double ticketPrice, double rating) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.ticketPrice = ticketPrice;
        this.rating = rating;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "movieId=" + movieId +
                ", movieName='" + movieName + '\'' +
                ", ticketPrice=" + ticketPrice +
                ", rating=" + rating +
                '}';
    }
}