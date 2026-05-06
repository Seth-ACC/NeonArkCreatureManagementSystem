package org.example.neonarkcreaturemanagementsystem.cli.api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CreatureApiClient {

    private static final String BASE_URL = "http://localhost:8080/api";

    private final HttpClient client;

    public CreatureApiClient() {
        this.client = HttpClient.newHttpClient();
    }

    public String getAllCreatures() {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/creatures"))
                .GET()
                .build();

        try {
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();

        } catch (IOException | InterruptedException e) {
            return "Error connecting to backend API.";
        }
    }

    public String getCreatureById(Long id) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/creatures/" + id))
                .GET()
                .build();

        try {
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 404) {
                return "Creature not found.";
            }

            return response.body();

        } catch (IOException | InterruptedException e) {
            return "Error connecting to backend API.";
        }
    }

    public String createCreature(String jsonBody) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/creatures"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        try {
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            return "Status: " + response.statusCode() + "\n" + response.body();

        } catch (IOException | InterruptedException e) {
            return "Error connecting to backend API.";
        }
    }

    public String renameCreature(Long id, String newName) {

        String jsonBody = """
            {
              "name": "%s"
            }
            """.formatted(newName);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/creatures/" + id + "/name"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        try {
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 404) {
                return "Creature not found.";
            }

            return "Status: " + response.statusCode() + "\n" + response.body();

        } catch (IOException | InterruptedException e) {
            return "Error connecting to backend API.";
        }
    }

    public String removeCreature(Long id) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/creatures/" + id))
                .DELETE()
                .build();

        try {
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 404) {
                return "Creature not found.";
            }

            return "Status: " + response.statusCode() + "\n" + response.body();

        } catch (IOException | InterruptedException e) {
            return "Error connecting to backend API.";
        }
    }

    public String getCreatureObservations(Long id) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/creatures/" + id + "/observations"))
                .GET()
                .build();

        try {
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 404) {
                return "Creature not found.";
            }

            return "Status: " + response.statusCode() + "\n" + response.body();

        } catch (IOException | InterruptedException e) {
            return "Error connecting to backend API.";
        }
    }

    public String getFeedingsByTime(String time) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/feedings?time=" + time))
                .GET()
                .build();

        try {
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            return "Status: " + response.statusCode() + "\n" + response.body();

        } catch (IOException | InterruptedException e) {
            return "Error connecting to backend API.";
        }
    }

    public String getAllUsers() {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/admin/users"))
                .GET()
                .build();

        try {
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            return "Status: " + response.statusCode() + "\n" + response.body();

        } catch (IOException | InterruptedException e) {
            return "Error connecting to backend API.";
        }
    }
}