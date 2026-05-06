package org.example.neonarkcreaturemanagementsystem.controller;

import org.example.neonarkcreaturemanagementsystem.dto.CreatureSummaryResponse;
import org.example.neonarkcreaturemanagementsystem.dto.RenameCreatureRequest;
import org.example.neonarkcreaturemanagementsystem.entity.Creature;
import org.example.neonarkcreaturemanagementsystem.service.CreatureService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/creatures")
public class CreatureController {

    private final CreatureService creatureService;

    public CreatureController(CreatureService creatureService) {
        this.creatureService = creatureService;
    }

    @GetMapping
    public ResponseEntity<List<CreatureSummaryResponse>> getAllCreatures() {
        return ResponseEntity.ok(creatureService.getAllCreatures());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCreatureById(@PathVariable Long id) {
        return creatureService.getCreatureById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Creature createCreature(@RequestBody Creature creature) {
        return creatureService.createCreature(creature);
    }

    @PutMapping("/{id}/name")
    public ResponseEntity<?> renameCreature(
            @PathVariable Long id,
            @RequestBody RenameCreatureRequest request
    ) {
        return creatureService.renameCreature(id, request.getName());
    }

    @GetMapping("/{id}/observations")
    public ResponseEntity<?> getCreatureObservations(@PathVariable Long id) {
        return creatureService.getCreatureObservations(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCreature(@PathVariable Long id) {
        return creatureService.softDeleteCreature(id);
    }
}