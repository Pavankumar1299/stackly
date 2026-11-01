package org.example.lbm.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    private int bookId;

    @NotBlank(message = "Book title must not be empty")
    private String title;

    @NotBlank(message = "Book author must not be empty")
    private String author;

    @NotBlank(message = "Publisher must not be blank")
    private String publisher;

    private boolean available;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    private BigDecimal price;

    @Min(value = 2000, message = "Published year must be after 2000 or later")
    private int publishedYear;

    public boolean getAvailable() {
        return this.available;
    }
}
