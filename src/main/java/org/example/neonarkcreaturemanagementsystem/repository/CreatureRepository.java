package org.example.neonarkcreaturemanagementsystem.repository;

import org.example.neonarkcreaturemanagementsystem.entity.Creature;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CreatureRepository extends JpaRepository<Creature, Long> {
    boolean existsByHabitatIdAndNameIgnoreCase(Long habitatId, String name);
}



// No extra methods needed for basic "read" functionality

// Core CRUD methods you get for free:
// save(entity)        -> insert or update a creature
// findById(id)        -> get one creature by primary key
// findAll()           -> get all creatures
// deleteById(id)      -> delete by primary key
// delete(entity)      -> delete by passing the entity itself

// Paging and sorting methods are also included automatically.