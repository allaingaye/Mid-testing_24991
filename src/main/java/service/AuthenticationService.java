package service;

import java.util.HashMap;
import java.util.Map;

import model.User;

public class AuthenticationService {

    // In-memory mock database, replace with actual database in production
    private static Map<String, User> userDatabase = new HashMap<>();

    static {
        // Adding a test user to the mock database
        userDatabase.put("testUser", new User("1", "Test", "User", null, "1234567890", "correctPassword", null, "testUser", null, null, null));
    }

    // Method to authenticate the user
    public User authenticateUser(String username, String password) {
        // Retrieve the user from the mock database
        User user = userDatabase.get(username);
        
        // Check if user exists and password matches
        if (user != null && user.getPassword().equals(password)) {
            return user;  // Authentication successful
        }
        
        // Authentication failed
        return null;
    }
}
