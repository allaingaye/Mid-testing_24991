package model;

import java.util.Date;
import java.util.UUID;
import jakarta.persistence.*;

@Entity
public class Borrower {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "reservation_id", nullable = false, unique = true)
    private UUID reservationId;  // Primary key

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;  // Reference to Book entity

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reader_id", nullable = false)
    private User reader;  // Reference to User entity

    @Temporal(TemporalType.DATE)
    @Column(name = "due_date")
    private Date dueDate;

    @Column(name = "late_fees")
    private Integer lateFees;

    @Temporal(TemporalType.DATE)
    @Column(name = "reserve_date")
    private Date reserveDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "return_date")
    private Date returnDate;
    
    @Column(name = "fine")
    private Integer fine;

    // Constructors
    public Borrower() {}

    public Borrower(Book book, User reader, Date dueDate, Integer lateFees, Date reserveDate, Date returnDate, Integer fine) {
        this.book = book;
        this.reader = reader;
        this.dueDate = dueDate;
        this.lateFees = lateFees;
        this.reserveDate = reserveDate;
        this.returnDate = returnDate;
        this.fine = fine;
    }

    // Getters and Setters
    public UUID getReservationId() {
        return reservationId;
    }

    public void setReservationId(UUID reservationId) {
        this.reservationId = reservationId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getReader() {
        return reader;
    }

    public void setReader(User reader) {
        this.reader = reader;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getLateFees() {
        return lateFees;
    }

    public void setLateFees(Integer lateFees) {
        this.lateFees = lateFees;
    }

    public Date getReserveDate() {
        return reserveDate;
    }

    public void setReserveDate(Date reserveDate) {
        this.reserveDate = reserveDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

	public Integer getFine() {
		return fine;
	}

	public void setFine(Integer fine) {
		this.fine = fine;
	}
    
    
}
