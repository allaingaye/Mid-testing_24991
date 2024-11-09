package model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
public class Membership {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "membership_id", nullable = false, unique = true)
	private UUID membershipId;


    @ManyToOne
    @JoinColumn(name = "membership_type_id", nullable = false)
    private MembershipType membershipType;

    @ManyToOne
    @JoinColumn(name = "reader_id", nullable = false)
    private User reader;  // Relationship to User

    @Column(name = "membership_code", nullable = false)
    private String membershipCode;

    @Column(name = "registration_date", nullable = false)
    private Date registrationDate;

    @Column(name = "expiring_date", nullable = false)
    private Date expiringDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "membership_status", nullable = false)
    private Status membershipStatus = Status.APPROVED;  // Default value to avoid NULL issues

    // Constructors
    public Membership() {
        // Ensure a default value for membershipStatus in the no-args constructor
        this.membershipStatus = Status.APPROVED;
    }

    public Membership(MembershipType membershipType, User reader, String membershipCode,
                      Date registrationDate, Date expiringDate, Status membershipStatus, Integer fine) {
        this.membershipType = membershipType;
        this.reader = reader;
        this.membershipCode = membershipCode;
        this.registrationDate = registrationDate;
        this.expiringDate = expiringDate;
        this.membershipStatus = (membershipStatus != null) ? membershipStatus : Status.APPROVED;
    }

    // Getters and Setters
    public UUID getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(UUID membershipId) {
        this.membershipId = membershipId;
    }

    public MembershipType getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(MembershipType membershipType) {
        this.membershipType = membershipType;
    }

    public User getReader() {
        return reader;
    }

    public void setReader(User reader) {
        this.reader = reader;
    }

    public String getMembershipCode() {
        return membershipCode;
    }

    public void setMembershipCode(String membershipCode) {
        this.membershipCode = membershipCode;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getExpiringDate() {
        return expiringDate;
    }

    public void setExpiringDate(Date expiringDate) {
        this.expiringDate = expiringDate;
    }

    public Status getMembershipStatus() {
        return membershipStatus;
    }

    public void setMembershipStatus(Status membershipStatus) {
        this.membershipStatus = membershipStatus;
    }

}
