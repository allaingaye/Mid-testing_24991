package test;

import controller.MembershipDAO;
import model.Membership;
import model.MembershipType;
import model.User;
import service.MembershipService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Scanner;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MembershipServiceTest {

    private MembershipDAO membershipDAO;
    private MembershipService membershipService;

    @BeforeEach
    void setUp() {
        // Initialize the real MembershipDAO
        membershipDAO = new MembershipDAO();
        membershipService = new MembershipService(membershipDAO);
    }

    @Test
    void testRegisterMembership_WithScanner() {
        // Create a scanner to read input from the user
        Scanner scanner = new Scanner(System.in);

        // Prompt and read inputs for membership registration
        System.out.print("Enter membership type ID: ");
        UUID membershipTypeId = UUID.fromString(scanner.nextLine());

        System.out.print("Enter user ID: ");
        UUID userId = UUID.fromString(scanner.nextLine());

        System.out.print("Enter membership code: ");
        String membershipCode = scanner.nextLine();

        System.out.print("Enter registration date (timestamp in milliseconds): ");
        Date registrationDate = new Date(Long.parseLong(scanner.nextLine()));

        System.out.print("Enter expiring date (timestamp in milliseconds): ");
        Date expiringDate = new Date(Long.parseLong(scanner.nextLine()));

        // Fetch the actual MembershipType and User from the database
        MembershipType membershipType = membershipDAO.getMembershipTypeById(membershipTypeId);
        User user = membershipDAO.getUserById(userId);

        // Verify that the MembershipType and User exist
        assertNotNull(membershipType, "Membership Type not found in the database.");
        assertNotNull(user, "User not found in the database.");

        // Call the method to register the membership
        membershipService.registerMembership(membershipTypeId, userId, membershipCode, registrationDate, expiringDate);

        // Verify that the membership was saved by fetching it from the database
        Membership savedMembership = membershipDAO.getMembershipById(UUID.randomUUID());  // Replace with actual ID after saving
        assertNotNull(savedMembership, "Membership was not saved to the database.");

        System.out.println("Membership registered successfully.");
    }

    @Test
    void testRegisterMembership_InvalidMembershipType() {
        // Create a scanner to read input from the user
        Scanner scanner = new Scanner(System.in);

        // Prompt and read input for invalid membership type
        System.out.print("Enter invalid membership type ID: ");
        UUID invalidMembershipTypeId = UUID.fromString(scanner.nextLine());

        System.out.print("Enter user ID: ");
        UUID userId = UUID.fromString(scanner.nextLine());

        // Test invalid membership type registration
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            membershipService.registerMembership(invalidMembershipTypeId, userId, "M123", new Date(), new Date());
        });

        assertEquals("Invalid Membership Type ID", exception.getMessage());
    }

    @Test
    void testRegisterMembership_InvalidUser() {
        // Create a scanner to read input from the user
        Scanner scanner = new Scanner(System.in);

        // Prompt and read input for valid membership type and invalid user ID
        System.out.print("Enter valid membership type ID: ");
        UUID membershipTypeId = UUID.fromString(scanner.nextLine());

        System.out.print("Enter invalid user ID: ");
        UUID invalidUserId = UUID.fromString(scanner.nextLine());

        // Test invalid user registration
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            membershipService.registerMembership(membershipTypeId, invalidUserId, "M123", new Date(), new Date());
        });

        assertEquals("Invalid User ID", exception.getMessage());
    }
}
