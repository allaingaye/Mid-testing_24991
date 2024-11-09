package controller;

import model.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class UserDao {

    // Create a user account
    public void createUser(User user) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create user", e);
        }
    }

    // Retrieve a user by username for authentication
    public User getUserByUsername(String username) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            return session.createQuery("FROM User WHERE userName = :username", User.class)
                    .setParameter("username", username).uniqueResult();
        }
    }

    // Authenticate user by verifying username and password
    public User authenticateUser(String userName, String password) {
        User user = getUserByUsername(userName);
        if (user != null && verifyPassword(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    // Handle user login: validate credentials and set session attributes
    public boolean handleLogin(HttpServletRequest req, String userName, String password) {
        User user = authenticateUser(userName, password);
        if (user != null) {
            HttpSession session = req.getSession();
            session.setAttribute("loggedInUser", user); // Store user in session
            return true;
        }
        return false;
    }

    // Handle logout: invalidate the session
    public void handleLogout(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    // Verify if the provided password matches the stored hash
    public boolean verifyPassword(String plainPassword, String hashedPassword) {
        return hashedPassword.equals(hashPassword(plainPassword));
    }

    // Hash password using SHA-256
    public String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Fetch location by ID (UUID)
    public Location getLocationById(UUID locationId) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            return session.get(Location.class, locationId);
        }
    }

    // Fetch all villages (LocationType = 'VILLAGE')
    public List<Location> getAllVillages() {
        try (Session session = HibernateUtil.getSession().openSession()) {
            return session.createQuery("FROM Location WHERE locationType = 'VILLAGE'", Location.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Create a new Location
    public void createLocation(Location location) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(location);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create location", e);
        }
    }

    // Fetch province name by phone number
    public String getProvinceByPhoneNumber(String phoneNumber) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            String hql = "SELECT l.locationName FROM User u JOIN u.village v JOIN v.parentLocation l "
                    + "WHERE u.phoneNumber = :phone AND l.locationType = :type";
            return session.createQuery(hql, String.class)
                    .setParameter("phone", phoneNumber)
                    .setParameter("type", LocationType.PROVINCE)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Return null if there's an error
        }
    }


 // Check if the user can borrow more books based on their membership
    public boolean canBorrowMoreBooks(User user) {
        if (user.getMemberships().isEmpty()) {
            return false; // No memberships found
        }
        // Assuming the user has only one active membership
        Membership activeMembership = user.getMemberships().get(0); 
        int borrowedBooks = user.getBorrowers().size(); // Current number of borrowed books
        int maxBooks = activeMembership.getMembershipType().getMaxBooks(); // Max allowed books

        return borrowedBooks < maxBooks; // Return true if can borrow more
    }

    // Assign a book to a shelf
    public void assignBookToShelf(Long bookId, UUID shelfId) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            Transaction transaction = session.beginTransaction();
            Book book = session.get(Book.class, bookId);
            Shelf shelf = session.get(Shelf.class, shelfId);

            if (book != null && shelf != null) {
                book.setShelf(shelf);
                session.update(book);
                transaction.commit();
            }
        }
    }

    // Assign a shelf to a room
    public void assignShelfToRoom(UUID shelfId, UUID roomId) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            Transaction transaction = session.beginTransaction();
            Shelf shelf = session.get(Shelf.class, shelfId);
            Room room = session.get(Room.class, roomId);

            if (shelf != null && room != null) {
                shelf.setRoom(room);
                session.update(shelf);
                transaction.commit();
            }
        }
    }

    // Count books in a specific room
    public long countBooksInRoom(UUID roomId) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            return session
                    .createQuery("SELECT COUNT(b) FROM Book b JOIN b.shelf s WHERE s.room.roomId = :roomId", Long.class)
                    .setParameter("roomId", roomId).uniqueResult();
        }
    }

 // Calculate late return fee
    public int calculateLateFee(Date dueDate, Date returnDate) {
        long difference = returnDate.getTime() - dueDate.getTime();
        long daysLate = difference / (1000 * 60 * 60 * 24); // Convert milliseconds to days
        int feePerDay = 5; // Define the fee per day for late returns
        return daysLate > 0 ? (int) daysLate * feePerDay : 0; // Return total fee or 0 if not late
    }

    // Approve a membership request
    public void approveMembership(UUID membershipId) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            Transaction transaction = session.beginTransaction();
            Membership membership = session.get(Membership.class, membershipId);

            if (membership != null && membership.getMembershipStatus() != Status.APPROVED) {
                membership.setMembershipStatus(Status.APPROVED);
                session.update(membership);
                transaction.commit();
            }
        }
    }

    // Fetch all books from the database
    public List<Book> getAllBooks() {
        try (Session session = HibernateUtil.getSession().openSession()) {
            return session.createQuery("FROM Book", Book.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Fetch all rooms from the database
    public List<Room> getAllRooms() {
        try (Session session = HibernateUtil.getSession().openSession()) {
            return session.createQuery("FROM Room", Room.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Fetch all shelves from the database
    public List<Shelf> getAllShelves() {
        try (Session session = HibernateUtil.getSession().openSession()) {
            return session.createQuery("FROM Shelf", Shelf.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Fetch memberships by status
    public List<Membership> getMembershipsByStatus(Status status) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            return session.createQuery("FROM Membership WHERE membershipStatus = :status", Membership.class)
                    .setParameter("status", status).list();
        }
    }

    // Create new membership type
    public void createMembershipType(MembershipType membershipType) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(membershipType);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Fetch all membership types
    public List<MembershipType> getAllMembershipTypes() {
        try (Session session = HibernateUtil.getSession().openSession()) {
            return session.createQuery("FROM MembershipType", MembershipType.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Initialize membership types
    public void initializeMembershipTypes() {
        try (Session session = HibernateUtil.getSession().openSession()) {
            Transaction transaction = session.beginTransaction();

            // Check if membership types already exist to prevent duplicates
            if (session.createQuery("FROM MembershipType", MembershipType.class).list().isEmpty()) {
                MembershipType gold = new MembershipType("Gold", 5, 50);   // 5 books, 50 Rwf per day
                MembershipType silver = new MembershipType("Silver", 3, 30); // 3 books, 30 Rwf per day
                MembershipType striver = new MembershipType("Striver", 2, 10); // 2 books, 10 Rwf per day

                // Persist membership types
                session.persist(gold);
                session.persist(silver);
                session.persist(striver);
                System.out.println("Membership types initialized: Gold, Silver, Striver");
            }

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize membership types", e);
        }
    }

    // Create membership (non-static)
    public void createMembership(Membership membership) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(membership);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create membership", e);
        }
    }

    // Method to retrieve MembershipType by ID
    public MembershipType getMembershipTypeById(Long id) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            return session.get(MembershipType.class, id);
        }
    }

    // Method to retrieve a book by ID
    public Book getBookById(Long id) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            return session.get(Book.class, id);
        }
    }

    // Method to update a book
    public void updateBook(Book book) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(book);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update book", e);
        }
    }

    // Method to create a borrower
    public void createBorrower(Borrower borrower) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(borrower);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create borrower", e);
        }
    }
    public void createRoom(Room room) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(room);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create room", e);
        }
    }

    public void createShelf(Shelf shelf) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(shelf);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create shelf", e);
        }
    }
    public Membership getMembershipById(Long membershipId) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            return session.get(Membership.class, membershipId);
        }
    }

    public void updateMembership(Membership membership) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(membership);
            transaction.commit();
        }
    }
    
 // Fetch all users from the database
    public List<User> getAllUsers() {
        try (Session session = HibernateUtil.getSession().openSession()) {
            return session.createQuery("FROM User", User.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
 // Fetch a Borrower by its reservation ID
    public Borrower getBorrowerById(UUID reservationId) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            return session.get(Borrower.class, reservationId);
        }
    }

    // Update a Borrower
    public void updateBorrower(Borrower borrower) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(borrower);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update borrower", e);
        }
    }
    public List<Borrower> getActiveBorrowers(String userId) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            List<Borrower> borrowers = session.createQuery(
                "SELECT b FROM Borrower b JOIN FETCH b.book WHERE b.reader.personId = :userId", 
                Borrower.class)
                .setParameter("userId", userId)
                .list();

            return borrowers;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    

}
