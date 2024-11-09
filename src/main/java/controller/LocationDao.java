package controller;

import model.Location;
import model.User;
import util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.UUID;

public class LocationDao {

    // Get the Hibernate session (assuming HibernateUtil class exists to manage sessions)
    private Session session;

    public LocationDao() {
        session = HibernateUtil.getSession().openSession();
    }

    // Get Location by UUID
    public Location getLocationById(UUID locationId) {
        return session.get(Location.class, locationId); // Fetch Location by UUID
    }

    // Create a new Location
    public void createLocation(Location location) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(location); // Save Location to the database
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // Update Location
    public void updateLocation(Location location) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(location); // Update the existing Location in the database
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // Delete a Location by UUID
    public void deleteLocation(UUID locationId) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Location location = session.get(Location.class, locationId); // Get the location to delete
            if (location != null) {
                session.delete(location); // Delete Location from the database
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // Get all Locations
    public List<Location> getAllLocations() {
        return session.createQuery("FROM Location", Location.class).list(); // Fetch all Locations
    }

    // Get User by UUID (if necessary for Location-related logic)
    public User getUserById(UUID userId) {
        return session.get(User.class, userId); // Fetch User by UUID
    }

    // Add a new User (just in case you need this method)
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
