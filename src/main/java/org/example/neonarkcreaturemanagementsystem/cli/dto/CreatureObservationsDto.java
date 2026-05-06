package org.example.neonarkcreaturemanagementsystem.cli.dto;

import java.util.List;

public class CreatureObservationsDto {

    public Long creatureId;
    public String creatureName;
    public List<ObservationItem> observations;

    public static class ObservationItem {
        public Long id;
        public String authorName;
        public String note;
        public String observedAt;
    }
}