package com.microservices.room_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Room {
    @Id
    @GeneratedValue
    private int id;

    private String roomNumber;

    private String isOccupied;

    // Default constructor
    public Room() {}

    // Parameterized constructor
    public Room(int id, String roomNumber, String isOccupied) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.isOccupied = isOccupied;
    }

    // Getter and setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getIsOccupied() {
        return isOccupied;
    }

    public void setIsOccupied(String isOccupied) {
        this.isOccupied = isOccupied;
    }

    @Override
    public String toString() {
        return "Room [id=" + id + ", roomNumber=" + roomNumber + ", isOccupied=" + isOccupied + "]";
    }
}
