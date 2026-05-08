package org.example.neonarkcreaturemanagementsystem.dto;

public class CreatureSummaryResponse {

    private Long id;
    private String name;
    private String species;
    private String habitatName;
    private Integer dangerLevel;
    private String condition;
    private String status;

    public CreatureSummaryResponse(
            Long id,
            String name,
            String species,
            String habitatName,
            Integer dangerLevel,
            String condition,
            String status
    ) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.habitatName = habitatName;
        this.dangerLevel = dangerLevel;
        this.condition = condition;
        this.status = status;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getSpecies() { return species; }
    public String getHabitatName() { return habitatName; }
    public Integer getDangerLevel() { return dangerLevel; }
    public String getCondition() { return condition; }
    public String getStatus() { return status; }
}