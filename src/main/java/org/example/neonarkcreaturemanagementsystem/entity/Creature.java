package org.example.neonarkcreaturemanagementsystem.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "creatures")
public class Creature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String species;

    @Column(name = "danger_level")
    private Integer dangerLevel;

    private String condition;
    private String status;

    private String notes;

    @Column(name = "habitat_id")
    private Long habitatId;

    // Constructors
    public Creature() {}

    // Getters & Setters

    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSpecies() { return species; }
    public void setSpecies(String species) { this.species = species; }

    public Integer getDangerLevel() { return dangerLevel; }
    public void setDangerLevel(Integer dangerLevel) { this.dangerLevel = dangerLevel; }

    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public Long getHabitatId() { return habitatId; }
    public void setHabitatId(Long habitatId) { this.habitatId = habitatId; }
}