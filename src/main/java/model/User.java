package model;

import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "\"User\"") // Escaping reserved keyword in PostgreSQL
public class User extends Person {

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;
    

    @Column(name = "user_name", unique = true, nullable = false)
    private String userName;

    @ManyToOne
    @JoinColumn(name = "village_id", referencedColumnName = "location_id", nullable = false)
    private Location village;

    @OneToMany(mappedBy = "reader", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Membership> memberships;

    @OneToMany(mappedBy = "reader", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Borrower> borrowers;

    // Default constructor
    public User() {
        super();
    }

    // Constructor with inherited fields + User-specific fields
    public User(String personId, String firstName, String lastName, Gender gender, 
                String phoneNumber, String password, Role role, String userName, 
                Location village, List<Membership> memberships, List<Borrower> borrowers) {
        super(personId, firstName, lastName, gender, phoneNumber);  // Initialize Person fields
        this.password = password;
        this.role = role;
        this.userName = userName;
        this.village = village;
        this.memberships = memberships;
        this.borrowers = borrowers;
    }

    // Getters and Setters
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Location getVillage() {
        return village;
    }

    public void setVillage(Location village) {
        this.village = village;
    }

    public List<Membership> getMemberships() {
        return memberships;
    }

    public void setMemberships(List<Membership> memberships) {
        this.memberships = memberships;
    }

    public List<Borrower> getBorrowers() {
        return borrowers;
    }

    public void setBorrowers(List<Borrower> borrowers) {
        this.borrowers = borrowers;
    }
}
