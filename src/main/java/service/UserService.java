package service;

import model.User;
import model.Location;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

public class UserService {

    // Method to save a User
    public void saveUser(User user) {
        Session session = HibernateUtil.getSession().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.saveOrUpdate(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    // Method to find a User by ID
    public User findUserById(String userId) {
        Session session = HibernateUtil.getSession().openSession();
        User user = null;
        try {
            user = session.get(User.class, userId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return user;
    }

    public String getUserProvinceById(String userName) {
        Session session = HibernateUtil.getSession().openSession();
        String provinceName = null;
        try {
            // Query to get the user by username
            User user = session.createQuery("FROM User WHERE userName = :userName", User.class)
                               .setParameter("userName", userName)
                               .uniqueResult();

            // If the user is found, fetch their village and traverse to the province
            if (user != null && user.getVillage() != null && user.getVillage().getParentLocation() != null) {
                // Going up the hierarchy: village -> cell -> sector -> district -> province
                Location province = user.getVillage().getParentLocation().getParentLocation().getParentLocation().getParentLocation();
                provinceName = (province != null) ? province.getLocationName() : "No province found";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return provinceName;
    }
}
