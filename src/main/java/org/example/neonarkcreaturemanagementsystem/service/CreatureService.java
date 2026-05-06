package org.example.neonarkcreaturemanagementsystem.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.neonarkcreaturemanagementsystem.dto.CreatureObservationsResponse;
import org.example.neonarkcreaturemanagementsystem.entity.Creature;
import org.example.neonarkcreaturemanagementsystem.repository.CreatureRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.example.neonarkcreaturemanagementsystem.dto.CreatureSummaryResponse;
import org.example.neonarkcreaturemanagementsystem.dto.CreatureDetailResponse;
import org.example.neonarkcreaturemanagementsystem.repository.FeedingScheduleRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreatureService {

    private final CreatureRepository repository;
    private final FeedingScheduleRepository feedingScheduleRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public CreatureService(
            CreatureRepository repository,
            FeedingScheduleRepository feedingScheduleRepository
    ) {
        this.repository = repository;
        this.feedingScheduleRepository = feedingScheduleRepository;
    }

    public List<CreatureSummaryResponse> getAllCreatures() {

        List<Object[]> results = entityManager.createNativeQuery("""
        SELECT c.id,
               c.name,
               h.biome AS habitat_name,
               c.status
        FROM creatures c
        JOIN habitats h ON c.habitat_id = h.id
        ORDER BY c.id
    """).getResultList();

        return results.stream()
                .map(row -> new CreatureSummaryResponse(
                        ((Number) row[0]).longValue(),
                        (String) row[1],
                        (String) row[2],
                        (String) row[3]
                ))
                .toList();
    }

    public ResponseEntity<?> getCreatureById(Long id) {

        List<Object[]> results = entityManager.createNativeQuery("""
        SELECT c.id,
               c.name,
               c.species,
               c.danger_level,
               c.condition,
               c.notes,
               c.status,
               h.biome AS habitat_name
        FROM creatures c
        JOIN habitats h ON c.habitat_id = h.id
        WHERE c.id = :id
    """)
                .setParameter("id", id)
                .getResultList();

        if (results.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Object[] row = results.get(0);

        CreatureDetailResponse response = new CreatureDetailResponse(
                ((Number) row[0]).longValue(),
                (String) row[1],
                (String) row[2],
                ((Number) row[3]).intValue(),
                (String) row[4],
                (String) row[5],
                (String) row[6],
                (String) row[7]
        );

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> createCreature(Creature creature) {

        if (creature.getName() == null || creature.getName().isBlank()) {
            return ResponseEntity.badRequest().body("Creature name is required.");
        }

        if (creature.getSpecies() == null || creature.getSpecies().isBlank()) {
            return ResponseEntity.badRequest().body("Creature species is required.");
        }

        if (creature.getDangerLevel() < 1 || creature.getDangerLevel() > 5) {
            return ResponseEntity.badRequest().body("Danger level must be between 1 and 5.");
        }

        if (creature.getCondition() == null ||
                !(creature.getCondition().equals("STABLE") ||
                        creature.getCondition().equals("WATCH") ||
                        creature.getCondition().equals("CRITICAL") ||
                        creature.getCondition().equals("QUARANTINE"))) {
            return ResponseEntity.badRequest().body("Condition must be STABLE, WATCH, CRITICAL, or QUARANTINE.");
        }

        if (creature.getStatus() == null ||
                !(creature.getStatus().equals("ACTIVE") ||
                        creature.getStatus().equals("REMOVED"))) {
            return ResponseEntity.badRequest().body("Status must be ACTIVE or REMOVED.");
        }

        if (creature.getHabitatId() == null) {
            return ResponseEntity.badRequest().body("Habitat ID is required.");
        }

        try {
            Creature savedCreature = repository.save(creature);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCreature);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Creature could not be created. Check for duplicate name in the same habitat or invalid habitat ID.");
        }
    }

    public ResponseEntity<?> renameCreature(Long id, String requestedName) {
        if (requestedName == null || requestedName.isBlank()) {
            return ResponseEntity.badRequest().body("New creature name cannot be blank.");
        }

        String newName = requestedName.trim();

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

    public ResponseEntity<?> getCreatureObservations(Long id) {
        return repository.findById(id)
                .map(creature -> {
                    List<Object[]> results = entityManager.createNativeQuery("""
                        SELECT o.id, u.full_name, o.note, o.observed_at
                        FROM observations o
                        JOIN users u ON o.user_id = u.id
                        WHERE o.creature_id = :creatureId
                        ORDER BY o.observed_at
                    """)
                            .setParameter("creatureId", id)
                            .getResultList();

                    List<CreatureObservationsResponse.ObservationItem> observationItems =
                            results.stream()
                                    .map(row -> new CreatureObservationsResponse.ObservationItem(
                                            ((Number) row[0]).longValue(),
                                            (String) row[1],
                                            (String) row[2],
                                            row[3].toString()
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

    public ResponseEntity<?> softDeleteCreature(Long id) {
        return repository.findById(id)
                .map(creature -> {

                    if (feedingScheduleRepository.existsByCreatureId(id)) {
                        return ResponseEntity.status(HttpStatus.CONFLICT)
                                .body("Cannot remove creature because it has an active feeding schedule.");
                    }

                    creature.setStatus("REMOVED");
                    Creature savedCreature = repository.save(creature);

                    return ResponseEntity.ok(java.util.Map.of(
                            "message", "Creature status changed to REMOVED.",
                            "id", savedCreature.getId(),
                            "name", savedCreature.getName(),
                            "status", savedCreature.getStatus()
                    ));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}