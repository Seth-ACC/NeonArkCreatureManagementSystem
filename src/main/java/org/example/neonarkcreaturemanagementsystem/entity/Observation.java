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

    @Column(name = "author_name", nullable = false)
    private String authorName;

    @Column(nullable = false)
    private String note;

    @Column(name = "observed_at", nullable = false)
    private LocalDateTime observedAt;

    // getters and setters

    public Long getId() { return id; }

    public Long getCreatureId() { return creatureId; }
    public void setCreatureId(Long creatureId) { this.creatureId = creatureId; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public LocalDateTime getObservedAt() { return observedAt; }
    public void setObservedAt(LocalDateTime observedAt) { this.observedAt = observedAt; }
}