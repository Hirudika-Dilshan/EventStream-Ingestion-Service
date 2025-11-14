package com.eventstream.ingestion_service.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "event_logs") // Database table එකේ නම
public class EventLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID එක auto-increment වෙන්න
    private Long id;

    @Column(nullable = false) // මේ column එක null වෙන්න බෑ
    private String eventType;

    @Column(nullable = false) // මේ column එක null වෙන්න බෑ
    private String userId;

    @Column(nullable = false)
    private Instant timestamp;

    private String ipAddress;

    private String device;

    // JPA (database library) එකට 'no-argument constructor' එකක් ඕනේ
    public EventLog() {
    }

    // අපිට object එකක් හදන්න ලේසි වෙන්න constructor එකක්
    public EventLog(String eventType, String userId, Instant timestamp, String ipAddress, String device) {
        this.eventType = eventType;
        this.userId = userId;
        this.timestamp = timestamp;
        this.ipAddress = ipAddress;
        this.device = device;
    }

    // --- Getters and Setters ---
    // JPA වලට මේ 'getter' සහ 'setter' methods ඕනේ
    // data read/write කරන්න.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }
}