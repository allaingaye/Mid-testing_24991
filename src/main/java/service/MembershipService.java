package service;

import controller.MembershipDAO;
import model.Membership;
import model.MembershipType;
import model.User;
import org.hibernate.Transaction;

import java.util.Date;
import java.util.UUID;

public class MembershipService {

    private final MembershipDAO membershipDAO;

    public MembershipService(MembershipDAO membershipDAO) {
        this.membershipDAO = membershipDAO;
    }

    public void registerMembership(UUID membershipTypeId, UUID userId, String membershipCode, Date registrationDate, Date expiringDate) {
        // Fetch MembershipType and User from the database using the DAO
        MembershipType membershipType = membershipDAO.getMembershipTypeById(membershipTypeId);
        User reader = membershipDAO.getUserById(userId);

        // Ensure both membershipType and user exist before proceeding
        if (membershipType == null) {
            throw new IllegalArgumentException("Invalid Membership Type ID");
        }
        if (reader == null) {
            throw new IllegalArgumentException("Invalid User ID");
        }

        // Create a new Membership object
        Membership membership = new Membership(
            membershipType,
            reader,
            membershipCode,
            registrationDate,
            expiringDate,
            null,  // Default status
            null   // No fine by default
        );

        // Save the Membership using the DAO
        membershipDAO.createMembership(membership);
    }
}
