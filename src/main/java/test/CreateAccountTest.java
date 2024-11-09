package test;

import controller.UserDao;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class CreateAccountTest {

    private UserDao userDao;

    @BeforeEach
    void setUp() {
        userDao = new UserDao();
    }

    @Test
    void testCreateUserAccountWithScanner() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();

        System.out.print("Enter gender (MALE/FEMALE): ");
        Gender gender = Gender.valueOf(scanner.nextLine().toUpperCase());

        System.out.print("Enter role (STUDENT/TEACHER/...): ");
        Role role = Role.valueOf(scanner.nextLine().toUpperCase());

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter username: ");
        String userName = scanner.nextLine();

        System.out.print("Enter village UUID: ");
        UUID villageId = UUID.fromString(scanner.nextLine());
        Location village = userDao.getLocationById(villageId); // Assuming the location exists in DB

        // Create User object
        User user = new User(UUID.randomUUID().toString(), firstName, lastName, gender, phoneNumber, password, role, userName, village, null, null);

        // Save user using UserDao
        userDao.createUser(user);

        // Verify user was created and persisted
        User retrievedUser = userDao.getUserByUsername(userName);
        assertNotNull(retrievedUser);
        assertEquals(userName, retrievedUser.getUserName());
        assertEquals(phoneNumber, retrievedUser.getPhoneNumber());

        System.out.println("User account created successfully with username: " + userName);
    }
}
