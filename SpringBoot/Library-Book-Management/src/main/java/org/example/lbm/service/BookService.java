package org.example.lbm.service;

import org.example.lbm.exception.BookManagementException;
import org.example.lbm.model.Book;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Validated
public class BookService {

    private final Map<Integer, Book> books = new HashMap<>();

    public int nextId = 1006;

    public BookService() {
        books.put(1001, new Book(1001, "Advance Python", "Guido Wan Rossum", "pydevs", true, BigDecimal.valueOf(199), 2002));
        books.put(1002, new Book(1002, "Basic Programming", "Jon Snow", "Crio.do", true, BigDecimal.valueOf(99), 2013));
        books.put(1003, new Book(1003, "Advance Plugins", "Lily Dev", "JetBrains", false, BigDecimal.valueOf(299), 2007));
        books.put(1004, new Book(1004, "Hone DSA", "Adam", "Coders", true, BigDecimal.valueOf(179), 2002));
        books.put(1005, new Book(1005, "Java for Genius ", "pk", "JetBrains", false, BigDecimal.valueOf(399), 2023));
    }

    public Book addBook(Book book) {

        int id = nextId++;

        Book newBook = new Book(
                id,
                book.getTitle(),
                book.getAuthor(),
                book.getPublisher(),
                book.getAvailable(),
                book.getPrice(),
                book.getPublishedYear()
        );

        books.put(id, newBook);

        return newBook;
    }

    public List<Book> getBooks() {
        return new ArrayList<>(books.values());
    }

    public Book bookExists(int bookId) {
        Book book = books.get(bookId);
        if (book == null) {
            throw new BookManagementException("Book id does not exist: " + bookId);
        }
        return book;
    }

    public Book getBookById(int bookId) {
        return bookExists(bookId);
    }

    public Book updateBook(int bookId, Book updatedBook) {
        Book existingBook = bookExists(bookId);

        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setPublisher(updatedBook.getPublisher());
        existingBook.setPublishedYear(updatedBook.getPublishedYear());
        existingBook.setPrice(updatedBook.getPrice());

        return existingBook;
    }

    public Book changeBookAvailability(int bookId, boolean available) {
        Book book = bookExists(bookId);

        book.setAvailable(available);

        return book;
    }

    public void deleteBook(int bookId) {
        bookExists(bookId);

        books.remove(bookId);
    }

    public List<Book> searchBooks(String title, String author, Integer year, Boolean availability) {
         return books.values()
                .stream()

                .filter(book -> title == null ||
                        book.getTitle().toLowerCase().contains(title.trim().toLowerCase()))

                .filter(book -> author == null ||
                        book.getAuthor().toLowerCase().contains(author.trim().toLowerCase()))

                .filter(book -> year == null ||
                        book.getPublishedYear() == year)

                .filter(book -> availability == null ||
                        book.getAvailable() == availability)
                .toList();
    }

}
