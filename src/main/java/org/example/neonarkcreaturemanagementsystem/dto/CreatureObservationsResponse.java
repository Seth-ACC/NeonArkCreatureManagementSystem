package org.example.neonarkcreaturemanagementsystem.dto;

import java.util.List;

public class CreatureObservationsResponse {

    private Long creatureId;
    private String creatureName;
    private List<ObservationItem> observations;

    public CreatureObservationsResponse(Long creatureId, String creatureName, List<ObservationItem> observations) {
        this.creatureId = creatureId;
        this.creatureName = creatureName;
        this.observations = observations;
    }

    public Long getCreatureId() { return creatureId; }
    public String getCreatureName() { return creatureName; }
    public List<ObservationItem> getObservations() { return observations; }

    public static class ObservationItem {
        private Long id;
        private String authorName;
        private String note;
        private String observedAt;

        public ObservationItem(Long id, String authorName, String note, String observedAt) {
            this.id = id;
            this.authorName = authorName;
            this.note = note;
            this.observedAt = observedAt;
        }

        public Long getId() { return id; }
        public String getAuthorName() { return authorName; }
        public String getNote() { return note; }
        public String getObservedAt() { return observedAt; }
    }
}