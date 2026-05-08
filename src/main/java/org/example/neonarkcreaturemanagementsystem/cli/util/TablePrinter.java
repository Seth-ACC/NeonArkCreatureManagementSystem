package org.example.neonarkcreaturemanagementsystem.cli.util;

import org.example.neonarkcreaturemanagementsystem.cli.dto.CreatureSummaryDto;
import org.example.neonarkcreaturemanagementsystem.cli.dto.CreatureDetailDto;
import org.example.neonarkcreaturemanagementsystem.cli.dto.CreatureObservationsDto;
import org.example.neonarkcreaturemanagementsystem.cli.dto.FeedingLookupDto;
import org.example.neonarkcreaturemanagementsystem.cli.dto.AdminUserDto;
import java.util.List;

import java.util.List;

public class TablePrinter {

    public static void printCreatureSummaryTable(List<CreatureSummaryDto> creatures) {

        System.out.println("-----------------------------------------------------------------------------------------------------");
        System.out.printf(
                "%-5s %-18s %-18s %-22s %-8s %-14s %-10s%n",
                "ID", "Name", "Species", "Habitat", "Danger", "Condition", "Status"
        );
        System.out.println("-----------------------------------------------------------------------------------------------------");

        for (CreatureSummaryDto creature : creatures) {
            System.out.printf(
                    "%-5d %-18s %-18s %-22s %-8d %-14s %-10s%n",
                    creature.id,
                    creature.name,
                    creature.species,
                    creature.habitatName,
                    creature.dangerLevel,
                    creature.condition,
                    creature.status
            );
        }

        System.out.println("-----------------------------------------------------------------------------------------------------");
    }

    public static void printCreatureDetail(CreatureDetailDto creature) {

        System.out.println("--------------------------------------------------");
        System.out.printf("%-18s %s%n", "ID:", creature.id);
        System.out.printf("%-18s %s%n", "Name:", creature.name);
        System.out.printf("%-18s %s%n", "Species:", creature.species);
        System.out.printf("%-18s %d%n", "Danger Level:", creature.dangerLevel);
        System.out.printf("%-18s %s%n", "Condition:", creature.condition);
        System.out.printf("%-18s %s%n", "Status:", creature.status);
        System.out.printf("%-18s %s%n", "Habitat:", creature.habitatName);
        System.out.printf("%-18s %s%n", "Notes:", creature.notes);
        System.out.println("--------------------------------------------------");
    }

    public static void printObservationsTable(CreatureObservationsDto response) {

        System.out.println("Creature ID:   " + response.creatureId);
        System.out.println("Creature Name: " + response.creatureName);
        System.out.println();

        if (response.observations == null || response.observations.isEmpty()) {
            System.out.println("------------------------------------------------------------");
            System.out.println("No observations found for this creature.");
            System.out.println("------------------------------------------------------------");
            return;
        }

        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.printf("%-5s %-20s %-25s %-45s%n", "ID", "Author", "Observed At", "Note");
        System.out.println("----------------------------------------------------------------------------------------------------");

        for (CreatureObservationsDto.ObservationItem obs : response.observations) {
            System.out.printf(
                    "%-5d %-20s %-25s %-45s%n",
                    obs.id,
                    obs.authorName,
                    obs.observedAt,
                    obs.note
            );
        }

        System.out.println("----------------------------------------------------------------------------------------------------");
    }

    public static void printFeedingLookupTable(FeedingLookupDto response) {

        System.out.println(response.message);
        System.out.println();

        if (response.results == null || response.results.isEmpty()) {

            System.out.println("------------------------------------------------------------");
            System.out.println("No feeding schedules found.");
            System.out.println("------------------------------------------------------------");

            return;
        }

        System.out.println("------------------------------------------------------------");
        System.out.printf("%-12s %-25s %-15s%n",
                "Creature ID",
                "Creature Name",
                "Feeding Time");
        System.out.println("------------------------------------------------------------");

        for (FeedingLookupDto.FeedingItem item : response.results) {

            System.out.printf("%-12d %-25s %-15s%n",
                    item.creatureId,
                    item.creatureName,
                    item.feedingTime);
        }

        System.out.println("------------------------------------------------------------");
    }

    public static void printAdminUsersTable(List<AdminUserDto> users) {

        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.printf("%-25s %-35s %-15s %-15s%n", "Full Name", "Email", "Phone", "Role");
        System.out.println("-------------------------------------------------------------------------------------------");

        for (AdminUserDto user : users) {
            System.out.printf(
                    "%-25s %-35s %-15s %-15s%n",
                    user.fullName,
                    user.email,
                    user.phone,
                    user.role
            );
        }

        System.out.println("-------------------------------------------------------------------------------------------");
    }
}