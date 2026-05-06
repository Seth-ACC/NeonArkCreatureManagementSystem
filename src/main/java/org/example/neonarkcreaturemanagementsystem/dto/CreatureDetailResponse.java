package org.example.neonarkcreaturemanagementsystem.dto;

public class CreatureDetailResponse {

    private Long id;
    private String name;
    private String species;
    private Integer dangerLevel;
    private String condition;
    private String notes;
    private String status;
    private String habitatName;

    public CreatureDetailResponse(
            Long id,
            String name,
            String species,
            Integer dangerLevel,
            String condition,
            String notes,
            String status,
            String habitatName
    ) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.dangerLevel = dangerLevel;
        this.condition = condition;
        this.notes = notes;
        this.status = status;
        this.habitatName = habitatName;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getSpecies() { return species; }
    public Integer getDangerLevel() { return dangerLevel; }
    public String getCondition() { return condition; }
    public String getNotes() { return notes; }
    public String getStatus() { return status; }
    public String getHabitatName() { return habitatName; }
}