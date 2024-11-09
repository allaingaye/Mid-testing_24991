package controller;

import model.Shelf;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;
import java.util.UUID;

public class ShelfDao {

    // Create a shelf
    public void createShelf(Shelf shelf) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(shelf);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Retrieve all shelves
    public List<Shelf> getAllShelves() {
        try (Session session = HibernateUtil.getSession().openSession()) {
            return session.createQuery("FROM Shelf", Shelf.class).list();
        }
    }

    // Retrieve a shelf by ID
    public Shelf getShelfById(UUID shelfId) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            return session.get(Shelf.class, shelfId);
        }
    }
}
