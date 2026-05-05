package org.example.neonarkcreaturemanagementsystem.controller;

import org.example.neonarkcreaturemanagementsystem.entity.Creature;
import org.example.neonarkcreaturemanagementsystem.repository.CreatureRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/creatures")
public class CreatureController {

    private final CreatureRepository repository;

    public CreatureController(CreatureRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Creature> getAllCreatures() {
        return repository.findAll();
    }
}