package test;

import model.Location;
import model.LocationType;
import model.Role;
import model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.LocationService;
import service.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserServiceTest {

    private static UserService userService;
    private static LocationService locationService;

    @BeforeAll
    public static void setup() {
        userService = new UserService();
        locationService = new LocationService();
    }

    @Test
    public void testInsertMultipleUsers() {
        // Step 1: Create a Village location for users
        Location province = locationService.createLocation("PR03", "Northern Province", LocationType.PROVINCE, null);
        Location district = locationService.createLocation("DT03", "Musanze District", LocationType.DISTRICT, province);
        Location sector = locationService.createLocation("SC02", "Kinigi Sector", LocationType.SECTOR, district);
        Location cell = locationService.createLocation("CL02", "Nyabirehe Cell", LocationType.CELL, sector);
        Location village = locationService.createLocation("VG02", "Kabare Village", LocationType.VILLAGE, cell);

        // Step 2: Insert multiple users automatically
        addUser("Alice", "Kamali", "password123", Role.LIBRARIAN, "alice123", village);
        addUser("Bob", "Munyaneza", "password456", Role.STUDENT, "bob456", village);
        addUser("Clara", "Nizeyimana", "password789", Role.TEACHER, "clara789", village);

        // Step 3: Assert that users are inserted and IDs are generated
        User user1 = userService.findUserById("alice123");
        User user2 = userService.findUserById("bob456");
        User user3 = userService.findUserById("clara789");

        assertNotNull(user1.getUserName(), "User 1 ID should not be null");
        assertNotNull(user2.getUserName(), "User 2 ID should not be null");
        assertNotNull(user3.getUserName(), "User 3 ID should not be null");

        // Step 4: Check the users by ID
        assertEquals("Alice", user1.getFirstName());
        assertEquals("Bob", user2.getFirstName());
        assertEquals("Clara", user3.getFirstName());
    }

    // Helper method to add a user automatically
    private void addUser(String firstName, String lastName, String password, Role role, String userName, Location village) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setRole(role);
        user.setUserName(userName);
        user.setVillage(village);

        userService.saveUser(user);
    }

    @AfterAll
    public static void tearDown() {
        // No need to call closeSession, session management is handled within the service methods.
    }
}
