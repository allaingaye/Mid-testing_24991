package service;

import controller.BorrowerDao;
import model.Book;
import model.Borrower;
import model.User;

import java.util.Date;
import java.util.UUID;

public class BorrowerService {

    private final BorrowerDao borrowerDao;

    public BorrowerService(BorrowerDao borrowerDao) {
        this.borrowerDao = borrowerDao;
    }

    // Method to borrow a book
    public void borrowBook1(UUID borrowerId, UUID userId, Book bookTitle, Date borrowDate, Date returnDate) {
        // Fetch the User and Borrower from the database
        User user = borrowerDao.getUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("Invalid User ID");
        }

        Borrower borrower = borrowerDao.getBorrowerById(borrowerId);
        if (borrower == null) {
            throw new IllegalArgumentException("Invalid Borrower ID");
        }

        // Create the Borrower entity for this borrowing session
        Borrower newBorrower = new Borrower();
        newBorrower.setReader(user);
        newBorrower.setBook(bookTitle);
        newBorrower.setDueDate(borrowDate);
        newBorrower.setReturnDate(returnDate);

        // Save the Borrower entity to the database
        borrowerDao.createBorrower(newBorrower);
    }

	public void borrowBook(UUID borrowerId, UUID invalidUserId, String bookTitle, Date borrowDate, Date returnDate) {
		// TODO Auto-generated method stub
		
	}
}
