package org.example.neonarkcreaturemanagementsystem.cli.service;

import org.example.neonarkcreaturemanagementsystem.cli.api.CreatureApiClient;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.neonarkcreaturemanagementsystem.cli.dto.CreatureSummaryDto;
import org.example.neonarkcreaturemanagementsystem.cli.dto.CreatureDetailDto;
import org.example.neonarkcreaturemanagementsystem.cli.dto.CreatureObservationsDto;
import org.example.neonarkcreaturemanagementsystem.cli.dto.FeedingLookupDto;
import org.example.neonarkcreaturemanagementsystem.cli.dto.RenameCreatureResponse;
import org.example.neonarkcreaturemanagementsystem.cli.dto.CreateCreatureResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import org.example.neonarkcreaturemanagementsystem.cli.dto.AdminUserDto;
import java.util.List;
import org.example.neonarkcreaturemanagementsystem.cli.util.TablePrinter;

import java.util.List;

public class CreatureCliService {

    private final CreatureApiClient apiClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CreatureCliService(CreatureApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public void listAllCreatures() {

        String response = apiClient.getAllCreatures();

        try {
            List<CreatureSummaryDto> creatures =
                    objectMapper.readValue(response, new TypeReference<List<CreatureSummaryDto>>() {});

            System.out.println();
            System.out.println("ALL CREATURES");

            TablePrinter.printCreatureSummaryTable(creatures);

        } catch (Exception e) {
            System.out.println("Unable to display creatures.");
            System.out.println(response);
        }
    }

    public void viewCreatureById(Long id) {

        String response = apiClient.getCreatureById(id);

        if (response.equals("Creature not found.")) {

            System.out.println();
            System.out.println("CREATURE DETAILS");
            System.out.println("--------------------------------------------------");
            System.out.println(response);
            System.out.println("--------------------------------------------------");

            return;
        }

        try {

            CreatureDetailDto creature =
                    objectMapper.readValue(response, CreatureDetailDto.class);

            System.out.println();
            System.out.println("CREATURE DETAILS");

            TablePrinter.printCreatureDetail(creature);

        } catch (Exception e) {

            System.out.println("Unable to display creature details.");
            System.out.println(response);
        }
    }

    public void registerCreature(
            String name,
            String species,
            int dangerLevel,
            String condition,
            String notes,
            String status,
            long habitatId
    ) {

        String jsonBody = """
            {
              "name": "%s",
              "species": "%s",
              "dangerLevel": %d,
              "condition": "%s",
              "notes": "%s",
              "status": "%s",
              "habitatId": %d
            }
            """.formatted(name, species, dangerLevel, condition, notes, status, habitatId);

        String response = apiClient.createCreature(jsonBody);

        System.out.println();
        System.out.println("REGISTER NEW CREATURE");

        try {

            String jsonBodyResponse =
                    response.substring(response.indexOf("\n") + 1);

            CreateCreatureResponse createdCreature =
                    objectMapper.readValue(jsonBodyResponse, CreateCreatureResponse.class);

            System.out.println("--------------------------------------------------");
            System.out.println("Status: 201");
            System.out.println("Creature successfully created.");
            System.out.println();
            System.out.println("ID: " + createdCreature.id);
            System.out.println("Name: " + createdCreature.name);
            System.out.println("Species: " + createdCreature.species);
            System.out.println("Danger Level: " + createdCreature.dangerLevel);
            System.out.println("Condition: " + createdCreature.condition);
            System.out.println("Status: " + createdCreature.status);
            System.out.println("Habitat ID: " + createdCreature.habitatId);
            System.out.println("--------------------------------------------------");

        } catch (Exception e) {

            System.out.println("--------------------------------------------------");
            System.out.println(response);
            System.out.println("--------------------------------------------------");
        }
    }

    public void renameCreature(Long id, String newName) {

        String response = apiClient.renameCreature(id, newName);

        System.out.println();
        System.out.println("RENAME CREATURE");

        if (response.equals("Creature not found.")) {

            System.out.println("--------------------------------------------------");
            System.out.println(response);
            System.out.println("--------------------------------------------------");

            return;
        }

        try {

            String jsonBody = response.substring(response.indexOf("\n") + 1);

            RenameCreatureResponse renameResponse =
                    objectMapper.readValue(jsonBody, RenameCreatureResponse.class);

            System.out.println("--------------------------------------------------");
            System.out.println("Status: 200");
            System.out.println("Old Name: " + renameResponse.oldName);
            System.out.println("New Name: " + renameResponse.newName);
            System.out.println("--------------------------------------------------");

        } catch (Exception e) {

            System.out.println("--------------------------------------------------");
            System.out.println(response);
            System.out.println("--------------------------------------------------");
        }
    }

    public void removeCreature(Long id) {

        String response = apiClient.removeCreature(id);

        System.out.println();
        System.out.println("REMOVE CREATURE");
        System.out.println("--------------------------------------------------");
        System.out.println(response);
        System.out.println("--------------------------------------------------");
    }

    public void viewCreatureObservations(Long id) {

        String response = apiClient.getCreatureObservations(id);

        if (response.equals("Creature not found.")) {
            System.out.println();
            System.out.println("CREATURE OBSERVATIONS");
            System.out.println("--------------------------------------------------");
            System.out.println(response);
            System.out.println("--------------------------------------------------");
            return;
        }

        try {
            String jsonBody = response.substring(response.indexOf("\n") + 1);

            CreatureObservationsDto observations =
                    objectMapper.readValue(jsonBody, CreatureObservationsDto.class);

            System.out.println();
            System.out.println("CREATURE OBSERVATIONS");

            TablePrinter.printObservationsTable(observations);

        } catch (Exception e) {
            System.out.println("Unable to display creature observations.");
            System.out.println(response);
        }
    }

    public void findCreaturesByFeedingTime(String time) {

        String response = apiClient.getFeedingsByTime(time);

        try {

            String jsonBody = response.substring(response.indexOf("\n") + 1);

            FeedingLookupDto feedingResults =
                    objectMapper.readValue(jsonBody, FeedingLookupDto.class);

            System.out.println();
            System.out.println("FEEDING LOOKUP");

            TablePrinter.printFeedingLookupTable(feedingResults);

        } catch (Exception e) {

            System.out.println();
            System.out.println("FEEDING LOOKUP");
            System.out.println("--------------------------------------------------");
            System.out.println(response);
            System.out.println("--------------------------------------------------");
        }
    }

    public void viewAllSystemUsers() {

        String response = apiClient.getAllUsers();

        try {
            String jsonBody = response.substring(response.indexOf("\n") + 1);

            List<AdminUserDto> users =
                    objectMapper.readValue(jsonBody, new TypeReference<List<AdminUserDto>>() {});

            System.out.println();
            System.out.println("SYSTEM USERS");

            TablePrinter.printAdminUsersTable(users);

        } catch (Exception e) {
            System.out.println();
            System.out.println("SYSTEM USERS");
            System.out.println("--------------------------------------------------");
            System.out.println(response);
            System.out.println("--------------------------------------------------");
        }
    }
}