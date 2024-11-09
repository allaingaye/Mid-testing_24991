package util;

import model.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSession() {
        if (sessionFactory == null) {
            try {
                StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                        .applySetting("hibernate.connection.driver_class", "org.postgresql.Driver")
                        .applySetting("hibernate.connection.url", "jdbc:postgresql://localhost:5432/auca_library_dba")
                        .applySetting("hibernate.connection.username", "postgres")
                        .applySetting("hibernate.connection.password", "2020pass@")
                        .applySetting("hibernate.hbm2ddl.auto", "create")
                        .applySetting("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
                        .applySetting("hibernate.show_sql", "true")
                        .build();

                Metadata metadata = new MetadataSources(registry)
                        .addAnnotatedClass(Book.class)
                        .addAnnotatedClass(Borrower.class)
                        .addAnnotatedClass(Location.class)
                        .addAnnotatedClass(Membership.class)
                        .addAnnotatedClass(MembershipType.class)
                        .addAnnotatedClass(Room.class)
                        .addAnnotatedClass(Shelf.class)
                        .addAnnotatedClass(User.class)
                        .buildMetadata();

                sessionFactory = metadata.buildSessionFactory();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to build SessionFactory", e);
            }
        }
        return sessionFactory;
    }
}
