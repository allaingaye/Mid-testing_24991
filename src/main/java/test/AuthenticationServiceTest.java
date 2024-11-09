package test;

import org.junit.jupiter.api.Test;

import model.User;
import service.AuthenticationService;

import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

public class AuthenticationServiceTest {

    // Helper method to simulate Scanner input
    private String simulateUserInput(String input) {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    @Test
    void testAuthenticateUser_Success() {
        AuthenticationService authenticationService = new AuthenticationService();

        // Simulating the user input using Scanner
        String testUsername = "testUser";
        String testPassword = "testPassword123";

        // Create a mock user (you could replace this with a mock or an actual database)
        User mockUser = new User("1", "Test", "User", null, "1234567890", testPassword, null, testUsername, null, null, null);
        // Assume userDao.saveUser(mockUser) is called to save mockUser in the DB

        // Simulate user input for authentication
        String enteredUsername = simulateUserInput(testUsername);
        String enteredPassword = simulateUserInput(testPassword);

        // When: authenticating the user with Scanner input
        User authenticatedUser = authenticationService.authenticateUser(enteredUsername, enteredPassword);

        // Then: the user should be authenticated successfully
        assertNotNull(authenticatedUser);
        assertEquals(testUsername, authenticatedUser.getUserName());
    }

    @Test
    void testAuthenticateUser_Failure_InvalidPassword() {
        AuthenticationService authenticationService = new AuthenticationService();

        // Simulating the user input using Scanner
        String testUsername = "testUser";
        String wrongPassword = "wrongPassword";

        // Create a mock user (you could replace this with a mock or an actual database)
        User mockUser = new User("1", "Test", "User", null, "1234567890", "correctPassword", null, testUsername, null, null, null);
        // Assume userDao.saveUser(mockUser) is called to save mockUser in the DB

        // Simulate user input for authentication
        String enteredUsername = simulateUserInput(testUsername);
        String enteredPassword = simulateUserInput(wrongPassword);

        // When: authenticating the user with incorrect password
        User authenticatedUser = authenticationService.authenticateUser(enteredUsername, enteredPassword);

        // Then: authentication should fail
        assertNull(authenticatedUser);
    }

    @Test
    void testAuthenticateUser_Failure_UserNotFound() {
        AuthenticationService authenticationService = new AuthenticationService();

        // Simulating the user input using Scanner
        String nonExistingUsername = "nonExistentUser";
        String anyPassword = "anyPassword";

        // Simulate user input for authentication
        String enteredUsername = simulateUserInput(nonExistingUsername);
        String enteredPassword = simulateUserInput(anyPassword);

        // When: authenticating a non-existing user
        User authenticatedUser = authenticationService.authenticateUser(enteredUsername, enteredPassword);

        // Then: authentication should fail
        assertNull(authenticatedUser);
    }
}
