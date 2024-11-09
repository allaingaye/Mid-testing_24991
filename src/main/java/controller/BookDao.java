package controller;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.Book;
import util.HibernateUtil;

public class BookDao {

    public void addBook(Book book) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(book);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Book> getAllBooks() {
        try (Session session = HibernateUtil.getSession().openSession()) {
            return session.createQuery("FROM Book", Book.class).list();
        }
    }
}
