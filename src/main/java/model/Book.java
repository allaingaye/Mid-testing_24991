package model;

import java.util.Date;

import jakarta.persistence.*;


@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id", nullable = false, unique = true)
    private Long bookId;  // Primary key

    private String title;
    private String isbnCode;
    private Integer edition;

    @Temporal(TemporalType.DATE)
    private Date publicationYear;

    private String publisherName;

    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus;

    @ManyToOne
    @JoinColumn(name = "shelf_id", nullable = false)  // Ensure correct join column
    private Shelf shelf;  // Changed to 'shelf' for consistency

    // Constructors
    public Book() {}

    public Book(String title, String isbnCode, Integer edition, Date publicationYear, 
                String publisherName, BookStatus bookStatus, Shelf shelf) {
        this.title = title;
        this.isbnCode = isbnCode;
        this.edition = edition;
        this.publicationYear = publicationYear;
        this.publisherName = publisherName;
        this.bookStatus = bookStatus;
        this.shelf = shelf;
    }
 // Getters and Setters

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIsbnCode() {
		return isbnCode;
	}

	public void setIsbnCode(String isbnCode) {
		this.isbnCode = isbnCode;
	}

	public Integer getEdition() {
		return edition;
	}

	public void setEdition(Integer edition) {
		this.edition = edition;
	}

	public Date getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(Date publicationYear) {
		this.publicationYear = publicationYear;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public BookStatus getBookStatus() {
		return bookStatus;
	}

	public void setBookStatus(BookStatus bookStatus) {
		this.bookStatus = bookStatus;
	}

	public Shelf getShelf() {
		return shelf;
	}

	public void setShelf(Shelf shelf) {
		this.shelf = shelf;
	}

    
    
}
