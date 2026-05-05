package org.example.neonarkcreaturemanagementsystem.controller;

import org.example.neonarkcreaturemanagementsystem.entity.Creature;
import org.example.neonarkcreaturemanagementsystem.repository.CreatureRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

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

    @GetMapping("/{id}")
    public ResponseEntity<Creature> getCreatureById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Creature createCreature(@RequestBody Creature creature) {
        return repository.save(creature);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Creature> updateCreature(
            @PathVariable Long id,
            @RequestBody Creature updatedCreature
    ) {
        return repository.findById(id)
                .map(existingCreature -> {
                    existingCreature.setName(updatedCreature.getName());
                    existingCreature.setSpecies(updatedCreature.getSpecies());
                    existingCreature.setDangerLevel(updatedCreature.getDangerLevel());
                    existingCreature.setCondition(updatedCreature.getCondition());
                    existingCreature.setStatus(updatedCreature.getStatus());
                    existingCreature.setNotes(updatedCreature.getNotes());
                    existingCreature.setHabitatId(updatedCreature.getHabitatId());

                    Creature savedCreature = repository.save(existingCreature);
                    return ResponseEntity.ok(savedCreature);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCreature(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}