package org.example.neonarkcreaturemanagementsystem.service;

import org.example.neonarkcreaturemanagementsystem.dto.FeedingResponse;
import org.example.neonarkcreaturemanagementsystem.entity.FeedingSchedule;
import org.example.neonarkcreaturemanagementsystem.repository.CreatureRepository;
import org.example.neonarkcreaturemanagementsystem.repository.FeedingScheduleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FeedingService {

    private final FeedingScheduleRepository feedingScheduleRepository;
    private final CreatureRepository creatureRepository;

    public FeedingService(
            FeedingScheduleRepository feedingScheduleRepository,
            CreatureRepository creatureRepository
    ) {
        this.feedingScheduleRepository = feedingScheduleRepository;
        this.creatureRepository = creatureRepository;
    }

    public ResponseEntity<?> getFeedingsByTime(String time) {
        if (time == null || time.isBlank()) {
            return ResponseEntity.badRequest().body("Time query parameter is required. Use HH:MM format.");
        }

        LocalTime feedingTime;

        try {
            feedingTime = LocalTime.parse(time);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body("Invalid time format. Use HH:MM format.");
        }

        List<FeedingSchedule> schedules = feedingScheduleRepository.findByFeedingTime(feedingTime);
        List<FeedingResponse> results = new ArrayList<>();

        for (FeedingSchedule schedule : schedules) {
            creatureRepository.findById(schedule.getCreatureId())
                    .ifPresent(creature -> results.add(
                            new FeedingResponse(
                                    creature.getId(),
                                    creature.getName(),
                                    schedule.getFeedingTime().toString()
                            )
                    ));
        }

        if (results.isEmpty()) {
            return ResponseEntity.ok(Map.of(
                    "message", "No creatures need feeding at this time.",
                    "results", results
            ));
        }

        return ResponseEntity.ok(Map.of(
                "message", "Creatures requiring feeding found.",
                "results", results
        ));
    }
}