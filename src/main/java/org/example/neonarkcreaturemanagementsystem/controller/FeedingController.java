package org.example.neonarkcreaturemanagementsystem.controller;

import org.example.neonarkcreaturemanagementsystem.service.FeedingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feedings")
public class FeedingController {

    private final FeedingService feedingService;

    public FeedingController(FeedingService feedingService) {
        this.feedingService = feedingService;
    }

    @GetMapping
    public ResponseEntity<?> getFeedingsByTime(@RequestParam(required = false) String time) {
        return feedingService.getFeedingsByTime(time);
    }
}