package model;

import java.util.List;
import java.util.UUID;
import jakarta.persistence.*;


@Entity
public class Shelf {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "shelf_id", nullable = false, unique = true)
    private UUID shelfId;  // Primary key

    private String bookCategory;
    private Integer availableStock;
    private Integer borrowedNumber;
    private Integer initialStock;

    @OneToMany(mappedBy = "shelf")  // 'shelf' matches the field name in Book
    private List<Book> books;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    // Constructors
    public Shelf() {}

    public Shelf(String bookCategory, Integer availableStock, Integer borrowedNumber, 
                 Integer initialStock, List<Book> books, Room room) {
        this.bookCategory = bookCategory;
        this.availableStock = availableStock;
        this.borrowedNumber = borrowedNumber;
        this.initialStock = initialStock;
        this.books = books;
        this.room = room;
    }
    
 // Getters and Setters

	public UUID getShelfId() {
		return shelfId;
	}

	public void setShelfId(UUID shelfId) {
		this.shelfId = shelfId;
	}

	public String getBookCategory() {
		return bookCategory;
	}

	public void setBookCategory(String bookCategory) {
		this.bookCategory = bookCategory;
	}

	public Integer getAvailableStock() {
		return availableStock;
	}

	public void setAvailableStock(Integer availableStock) {
		this.availableStock = availableStock;
	}

	public Integer getBorrowedNumber() {
		return borrowedNumber;
	}

	public void setBorrowedNumber(Integer borrowedNumber) {
		this.borrowedNumber = borrowedNumber;
	}

	public Integer getInitialStock() {
		return initialStock;
	}

	public void setInitialStock(Integer initialStock) {
		this.initialStock = initialStock;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

    
    
}
