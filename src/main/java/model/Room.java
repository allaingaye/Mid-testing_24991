package model;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;


@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "room_id", nullable = false, unique = true)
    private UUID roomId;  // Primary key

    private String roomCode;

    @OneToMany(mappedBy = "room")  // Corrected to match the field name in Shelf
    private List<Shelf> shelves;  // Renamed for better clarity

    // Constructors
    public Room() {}

    public Room(String roomCode, List<Shelf> shelves) {
        this.roomCode = roomCode;
        this.shelves = shelves;
    }

    // Getters and Setters
    public UUID getRoomId() {
        return roomId;
    }

    public void setRoomId(UUID roomId) {
        this.roomId = roomId;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public List<Shelf> getShelves() {
        return shelves;
    }

    public void setShelves(List<Shelf> shelves) {
        this.shelves = shelves;
    }
}
