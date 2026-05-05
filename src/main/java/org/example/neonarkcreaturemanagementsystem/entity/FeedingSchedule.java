package org.example.neonarkcreaturemanagementsystem.entity;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "feeding_schedules")
public class FeedingSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creature_id", nullable = false)
    private Long creatureId;

    @Column(name = "feeding_time", nullable = false)
    private LocalTime feedingTime;

    public Long getId() { return id; }

    public Long getCreatureId() { return creatureId; }
    public void setCreatureId(Long creatureId) { this.creatureId = creatureId; }

    public LocalTime getFeedingTime() { return feedingTime; }
    public void setFeedingTime(LocalTime feedingTime) { this.feedingTime = feedingTime; }
}