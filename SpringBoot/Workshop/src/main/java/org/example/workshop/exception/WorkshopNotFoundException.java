package org.example.workshop.exception;

public class WorkshopNotFoundException extends RuntimeException {
    public WorkshopNotFoundException(String message) {
        super(message);
    }
}
