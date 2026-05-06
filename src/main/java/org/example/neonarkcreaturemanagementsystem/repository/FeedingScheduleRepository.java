package org.example.neonarkcreaturemanagementsystem.repository;

import org.example.neonarkcreaturemanagementsystem.entity.FeedingSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.List;

public interface FeedingScheduleRepository extends JpaRepository<FeedingSchedule, Long> {

    List<FeedingSchedule> findByFeedingTime(LocalTime feedingTime);

    boolean existsByCreatureId(Long creatureId);
}