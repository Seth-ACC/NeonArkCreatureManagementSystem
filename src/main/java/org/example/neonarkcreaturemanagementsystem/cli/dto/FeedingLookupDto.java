package org.example.neonarkcreaturemanagementsystem.cli.dto;

import java.util.List;

public class FeedingLookupDto {

    public List<FeedingItem> results;
    public String message;

    public static class FeedingItem {
        public Long creatureId;
        public String creatureName;
        public String feedingTime;
    }
}