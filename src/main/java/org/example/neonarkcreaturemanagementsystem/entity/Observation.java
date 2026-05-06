package org.example.neonarkcreaturemanagementsystem.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "observations")
public class Observation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creature_id", nullable = false)
    private Long creatureId;

    @Column(nullable = false)
    private String note;

    @Column(name = "observed_at", nullable = false)
    private LocalDateTime observedAt;

    @Column(name = "user_id")
    private Long userId;

    // getters and setters

    public Long getId() { return id; }

    public Long getCreatureId() { return creatureId; }
    public void setCreatureId(Long creatureId) { this.creatureId = creatureId; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public LocalDateTime getObservedAt() { return observedAt; }
    public void setObservedAt(LocalDateTime observedAt) { this.observedAt = observedAt; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}