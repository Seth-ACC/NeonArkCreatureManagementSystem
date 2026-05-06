package org.example.neonarkcreaturemanagementsystem.cli.service;

import org.example.neonarkcreaturemanagementsystem.cli.api.CreatureApiClient;

public class CreatureCliService {

    private final CreatureApiClient apiClient;

    public CreatureCliService(CreatureApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public void listAllCreatures() {
        String response = apiClient.getAllCreatures();

        System.out.println();
        System.out.println("ALL CREATURES");
        System.out.println("--------------------------------------------------");
        System.out.println(response);
        System.out.println("--------------------------------------------------");
    }

    public void viewCreatureById(Long id) {
        String response = apiClient.getCreatureById(id);

        System.out.println();
        System.out.println("CREATURE DETAILS");
        System.out.println("--------------------------------------------------");
        System.out.println(response);
        System.out.println("--------------------------------------------------");
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
        System.out.println("--------------------------------------------------");
        System.out.println(response);
        System.out.println("--------------------------------------------------");
    }

    public void renameCreature(Long id, String newName) {

        String response = apiClient.renameCreature(id, newName);

        System.out.println();
        System.out.println("RENAME CREATURE");
        System.out.println("--------------------------------------------------");
        System.out.println(response);
        System.out.println("--------------------------------------------------");
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

        System.out.println();
        System.out.println("CREATURE OBSERVATIONS");
        System.out.println("--------------------------------------------------");
        System.out.println(response);
        System.out.println("--------------------------------------------------");
    }

    public void findCreaturesByFeedingTime(String time) {

        String response = apiClient.getFeedingsByTime(time);

        System.out.println();
        System.out.println("FEEDING LOOKUP");
        System.out.println("--------------------------------------------------");
        System.out.println(response);
        System.out.println("--------------------------------------------------");
    }

    public void viewAllSystemUsers() {

        String response = apiClient.getAllUsers();

        System.out.println();
        System.out.println("SYSTEM USERS");
        System.out.println("--------------------------------------------------");
        System.out.println(response);
        System.out.println("--------------------------------------------------");
    }
}