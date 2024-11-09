package controller;

import model.Membership;
import model.MembershipType;
import model.User;
import util.HibernateUtil;


import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class MembershipDAO {

    // Get the Hibernate session (assuming HibernateUtil class exists to manage sessions)
    private Session session;

    public MembershipDAO() {
        session = HibernateUtil.getSession().openSession();
    }

    // Get Membership by UUID
    public Membership getMembershipById(UUID membershipId) {
        return session.get(Membership.class, membershipId); // Fetch Membership by UUID
    }

    // Get MembershipType by UUID
    public MembershipType getMembershipTypeById(UUID membershipTypeId) {
        return session.get(MembershipType.class, membershipTypeId); // Fetch MembershipType by UUID
    }

    // Create a new Membership
    public void createMembership(Membership membership) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(membership); // Save Membership to the database
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // Update Membership
    public void updateMembership(Membership membership) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(membership); // Update the existing Membership in the database
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // Get User by ID (assuming you also need to fetch User in your project)
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
