package service;

import controller.UserDao;
import model.*;

import java.util.UUID;

public class CreateAccountService {

    private final UserDao userDao;

    public CreateAccountService() {
        this.userDao = new UserDao();
    }

    // Method to create a user account with all inputs passed as parameters
    public void createUserAccount(
            String firstName,
            String lastName,
            String phoneNumber,
            Gender gender,
            Role role,
            String password,
            String userName,
            UUID villageId) {

        Location village = userDao.getLocationById(villageId);

        if (village == null) {
            System.out.println("Village location with ID " + villageId + " not found.");
            return;
        }

        // Create the User object
        User user = new User();

        // Save the user using UserDao
        userDao.createUser(user);

        System.out.println("User account created successfully with username: " + userName);
    }
}
