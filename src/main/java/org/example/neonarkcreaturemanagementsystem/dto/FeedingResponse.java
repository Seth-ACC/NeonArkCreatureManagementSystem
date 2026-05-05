package org.example.neonarkcreaturemanagementsystem.dto;

public class FeedingResponse {

    private Long creatureId;
    private String creatureName;
    private String feedingTime;

    public FeedingResponse(Long creatureId, String creatureName, String feedingTime) {
        this.creatureId = creatureId;
        this.creatureName = creatureName;
        this.feedingTime = feedingTime;
    }

    public Long getCreatureId() { return creatureId; }
    public String getCreatureName() { return creatureName; }
    public String getFeedingTime() { return feedingTime; }
}