package org.example.neonarkcreaturemanagementsystem.controller;

import org.example.neonarkcreaturemanagementsystem.entity.Creature;
import org.example.neonarkcreaturemanagementsystem.repository.CreatureRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.example.neonarkcreaturemanagementsystem.dto.RenameCreatureRequest;
import org.example.neonarkcreaturemanagementsystem.dto.CreatureObservationsResponse;
import org.example.neonarkcreaturemanagementsystem.entity.Observation;
import org.example.neonarkcreaturemanagementsystem.repository.ObservationRepository;
import java.util.stream.Collectors;

import java.util.List;

@RestController
@RequestMapping("/api/creatures")
public class CreatureController {

    private final CreatureRepository repository;
    private final ObservationRepository observationRepository;

    public CreatureController(
            CreatureRepository repository,
            ObservationRepository observationRepository
    ) {
        this.repository = repository;
        this.observationRepository = observationRepository;
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

    @PutMapping("/{id}/name")
    public ResponseEntity<?> renameCreature(
            @PathVariable Long id,
            @RequestBody RenameCreatureRequest request
    ) {
        if (request.getName() == null || request.getName().isBlank()) {
            return ResponseEntity.badRequest().body("New creature name cannot be blank.");
        }

        String newName = request.getName().trim();

        if (newName.length() > 100) {
            return ResponseEntity.badRequest().body("New creature name cannot be longer than 100 characters.");
        }

        return repository.findById(id)
                .map(creature -> {
                    String oldName = creature.getName();

                    boolean duplicateExists =
                            repository.existsByHabitatIdAndNameIgnoreCase(creature.getHabitatId(), newName)
                                    && !oldName.equalsIgnoreCase(newName);

                    if (duplicateExists) {
                        return ResponseEntity.status(HttpStatus.CONFLICT)
                                .body("A creature with that name already exists in this habitat.");
                    }

                    creature.setName(newName);
                    Creature savedCreature = repository.save(creature);

                    return ResponseEntity.ok(java.util.Map.of(
                            "id", savedCreature.getId(),
                            "oldName", oldName,
                            "newName", savedCreature.getName()
                    ));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/observations")
    public ResponseEntity<?> getCreatureObservations(@PathVariable Long id) {
        return repository.findById(id)
                .map(creature -> {
                    List<Observation> observations = observationRepository.findByCreatureId(id);

                    List<CreatureObservationsResponse.ObservationItem> observationItems =
                            observations.stream()
                                    .map(observation -> new CreatureObservationsResponse.ObservationItem(
                                            observation.getId(),
                                            observation.getAuthorName(),
                                            observation.getNote(),
                                            observation.getObservedAt().toString()
                                    ))
                                    .collect(Collectors.toList());

                    CreatureObservationsResponse response =
                            new CreatureObservationsResponse(
                                    creature.getId(),
                                    creature.getName(),
                                    observationItems
                            );

                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Creature> deleteCreature(@PathVariable Long id) {
        return repository.findById(id)
                .map(creature -> {
                    creature.setStatus("REMOVED");
                    Creature savedCreature = repository.save(creature);
                    return ResponseEntity.ok(savedCreature);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}