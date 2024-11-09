package controller;

import model.Room;
import model.Shelf;
import util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.UUID;

public class RoomDao {

    // Get the Hibernate session (assuming HibernateUtil class exists to manage sessions)
    private Session session;

    public RoomDao() {
        session = HibernateUtil.getSession().openSession();
    }

    // Get Room by UUID
    public Room getRoomById(UUID roomId) {
        return session.get(Room.class, roomId); // Fetch Room by UUID
    }

    // Create a new Room
    public void createRoom(Room room) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(room); // Save Room to the database
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // Update Room
    public void updateRoom(Room room) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(room); // Update the existing Room in the database
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // Delete a Room by UUID
    public void deleteRoom(UUID roomId) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Room room = session.get(Room.class, roomId); // Get the room to delete
            if (room != null) {
                session.delete(room); // Delete Room from the database
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // Get all Rooms
    public List<Room> getAllRooms() {
        return session.createQuery("FROM Room", Room.class).list(); // Fetch all Rooms
    }

    // Get all Shelves in a Room (if necessary for Room-related logic)
    public List<Shelf> getShelvesByRoomId(UUID roomId) {
        Room room = session.get(Room.class, roomId);
        return room != null ? room.getShelves() : null; // Return shelves in the Room
    }

    // Close the session (call this method when you're done)
    public void closeSession() {
        if (session != null && session.isOpen()) {
            session.close();
        }
    }
}
