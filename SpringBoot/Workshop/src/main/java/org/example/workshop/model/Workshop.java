package org.example.workshop.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Workshop {

    private Long id;

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 80, message = "Title must contain 3 to 80 characters")
    private String title;

    @NotBlank(message = "Category is required")
    private String Category;

    @NotNull(message = "Fee is required")
    @DecimalMin(value = "0.00", message = "Fee cannot be negative")
    private BigDecimal fee;

    @NotNull(message = "Available seats sre required")
    @Min(value = 0, message = "Available seats cannot be negative")
    private Integer availableSeats;

    @NotBlank(message = "Organizer email is required")
    @Email(message = "Enter a valid organizer email")
    private String organizerEmail;

}
