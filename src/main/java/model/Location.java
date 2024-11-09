package model;

import java.util.List;
import java.util.UUID;
import jakarta.persistence.*;

@Entity
public class Location {

    @Id
    @Column(name = "location_id", nullable = false, unique = true)
    private UUID locationId = UUID.randomUUID();  // Primary key

    @Column(name = "location_code", nullable = false)
    private String locationCode;

    @Column(name = "location_name", nullable = false)
    private String locationName;

    @Enumerated(EnumType.STRING)
    @Column(name = "location_type", nullable = false)
    private LocationType locationType;

    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "location_id")
    private Location parentLocation;  // Self-referencing parent location

    @OneToMany(mappedBy = "village", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> users;  // Users associated with this location (village)

    @OneToMany(mappedBy = "parentLocation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Location> childLocations;  // Child locations

    // Constructors
    public Location() {}

    public Location(String locationCode, String locationName, LocationType locationType, Location parentLocation) {
        this.locationCode = locationCode;
        this.locationName = locationName;
        this.locationType = locationType;
        this.parentLocation = parentLocation;
    }

    // Getters and Setters
    public UUID getLocationId() {
        return locationId;
    }

    public void setLocationId(UUID locationId) {
        this.locationId = locationId;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public LocationType getLocationType() {
        return locationType;
    }

    public void setLocationType(LocationType locationType) {
        this.locationType = locationType;
    }

    public Location getParentLocation() {
        return parentLocation;
    }

    public void setParentLocation(Location parentLocation) {
        this.parentLocation = parentLocation;
    }

    public List<Location> getChildLocations() {
        return childLocations;
    }

    public void setChildLocations(List<Location> childLocations) {
        this.childLocations = childLocations;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
