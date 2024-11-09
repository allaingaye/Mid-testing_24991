package model;

import java.util.List;
import jakarta.persistence.*;


@Entity
@Table(name = "membership_type") // Explicit table naming
public class MembershipType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "membership_type_id", nullable = false, unique = true)
    private Long id;

    @Column(name = "membership_name", nullable = false)
    private String membershipName;

    @Column(name = "max_books", nullable = false)
    private Integer maxBooks;

    @Column(name = "price", nullable = false)
    private Integer price;

    @OneToMany(
        mappedBy = "membershipType", 
        cascade = CascadeType.ALL, 
        fetch = FetchType.LAZY, 
        orphanRemoval = true
    )
    private List<Membership> memberships;

    // Constructors
    public MembershipType() {}

    public MembershipType(String membershipName, Integer maxBooks, Integer price) {
        this.membershipName = membershipName;
        this.maxBooks = maxBooks;
        this.price = price;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMembershipName() {
        return membershipName;
    }

    public void setMembershipName(String membershipName) {
        this.membershipName = membershipName;
    }

    public Integer getMaxBooks() {
        return maxBooks;
    }

    public void setMaxBooks(Integer maxBooks) {
        this.maxBooks = maxBooks;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public List<Membership> getMemberships() {
        return memberships;
    }

    public void setMemberships(List<Membership> memberships) {
        this.memberships = memberships;
    }
}
