package test;

import controller.BorrowerDao;
import model.Borrower;
import model.User;
import service.BorrowerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Scanner;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BorrowerServiceTest {

    private BorrowerDao borrowerDao;
    private BorrowerService borrowerService;

    @BeforeEach
    void setUp() {
        // Initialize the real BorrowerDao
        borrowerDao = new BorrowerDao();
        borrowerService = new BorrowerService(borrowerDao);
    }

    @Test
    void testBorrowBook_WithScanner() {
        // Simulate Scanner input
        Scanner scanner = new Scanner(System.in);

        // Simulate user inputs
        System.out.print("Enter borrower ID: ");
        UUID borrowerId = UUID.fromString(scanner.nextLine());

        System.out.print("Enter user ID: ");
        UUID userId = UUID.fromString(scanner.nextLine());

        System.out.print("Enter book title: ");
        String bookTitle = scanner.nextLine();

        System.out.print("Enter borrow date (timestamp): ");
        Date borrowDate = new Date(Long.parseLong(scanner.nextLine()));

        System.out.print("Enter return date (timestamp): ");
        Date returnDate = new Date(Long.parseLong(scanner.nextLine()));

        // Fetch the actual Borrower and User from the database
        User user = borrowerDao.getUserById(userId);
        Borrower borrower = borrowerDao.getBorrowerById(borrowerId);

        assertNotNull(user, "User not found in the database.");
        assertNotNull(borrower, "Borrower not found in the database.");

        // Call the borrowBook method
        borrowerService.borrowBook(borrowerId, userId, bookTitle, borrowDate, returnDate);

        // Verify that the borrower was saved
        Borrower savedBorrower = borrowerDao.getBorrowerById(borrowerId); // Replace with actual ID after saving
        assertNotNull(savedBorrower, "Borrower was not saved to the database.");

        System.out.println("Book borrowed successfully.");
    }

    @Test
    void testBorrowBook_InvalidUser() {
        UUID borrowerId = UUID.randomUUID();
        UUID invalidUserId = UUID.randomUUID();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            borrowerService.borrowBook(borrowerId, invalidUserId, "Book Title", new Date(), new Date());
        });

        assertEquals("Invalid User ID", exception.getMessage());
    }

    @Test
    void testBorrowBook_InvalidBorrower() {
        UUID invalidBorrowerId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            borrowerService.borrowBook(invalidBorrowerId, userId, "Book Title", new Date(), new Date());
        });

        assertEquals("Invalid Borrower ID", exception.getMessage());
    }
}
