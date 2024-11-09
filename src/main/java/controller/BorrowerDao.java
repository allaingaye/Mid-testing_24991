package controller;

import model.Borrower;
import model.User;
import util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.UUID;

public class BorrowerDao {

    // Get the Hibernate session (assuming HibernateUtil class exists to manage sessions)
    private Session session;

    public BorrowerDao() {
        session = HibernateUtil.getSession().openSession();
    }

    // Get Borrower by UUID
    public Borrower getBorrowerById(UUID borrowerId) {
        return session.get(Borrower.class, borrowerId); // Fetch Borrower by UUID
    }

    // Create a new Borrower
    public void createBorrower(Borrower borrower) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(borrower); // Save Borrower to the database
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // Update Borrower
    public void updateBorrower(Borrower borrower) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(borrower); // Update the existing Borrower in the database
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // Delete a Borrower by UUID
    public void deleteBorrower(UUID borrowerId) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Borrower borrower = session.get(Borrower.class, borrowerId); // Get the borrower to delete
            if (borrower != null) {
                session.delete(borrower); // Delete Borrower from the database
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // Get all Borrowers
    public List<Borrower> getAllBorrowers() {
        return session.createQuery("FROM Borrower", Borrower.class).list(); // Fetch all Borrowers
    }

    // Get User by UUID (if necessary for Borrower-related logic)
    public User getUserById(UUID userId) {
        return session.get(User.class, userId); // Fetch User by UUID
    }

    // Add a new User (if needed in Borrower-related context)
    public void createUser(User user) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(user); // Save User to the database
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // Close the session (call this method when you're done)
    public void closeSession() {
        if (session != null && session.isOpen()) {
            session.close();
        }
    }
}
