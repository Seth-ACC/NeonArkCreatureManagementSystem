package org.example.neonarkcreaturemanagementsystem.repository;

import org.example.neonarkcreaturemanagementsystem.entity.Observation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ObservationRepository extends JpaRepository<Observation, Long> {

    List<Observation> findByCreatureId(Long creatureId);

}