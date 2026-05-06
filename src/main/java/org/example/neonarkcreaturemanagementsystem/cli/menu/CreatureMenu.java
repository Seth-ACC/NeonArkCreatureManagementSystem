package org.example.neonarkcreaturemanagementsystem.cli.menu;
import org.example.neonarkcreaturemanagementsystem.cli.service.CreatureCliService;

import java.util.Scanner;

public class CreatureMenu {

    private final Scanner scanner;
    private final CreatureCliService service;

    public CreatureMenu(CreatureCliService service) {
        this.scanner = new Scanner(System.in);
        this.service = service;
    }

    public void start() {

        boolean running = true;

        while (running) {

            printMenu();

            System.out.print("Select an option: ");
            String input = scanner.nextLine();

            switch (input) {

                case "1":
                    service.listAllCreatures();
                    break;

                case "2":
                    System.out.print("Enter creature ID: ");
                    Long id = Long.parseLong(scanner.nextLine());
                    service.viewCreatureById(id);
                    break;

                case "3":
                    String name = readRequiredText("Enter creature name: ");

                    String species = readRequiredText("Enter species: ");

                    int dangerLevel = readDangerLevel();

                    String condition = readCondition();

                    System.out.print("Enter notes: ");
                    String notes = scanner.nextLine();

                    String status = readStatus();

                    long habitatId = readPositiveLong("Enter habitat ID: ");

                    service.registerCreature(
                            name,
                            species,
                            dangerLevel,
                            condition,
                            notes,
                            status,
                            habitatId
                    );
                    break;

                case "4":

                    System.out.print("Enter creature ID: ");
                    Long renameId = Long.parseLong(scanner.nextLine());

                    String newName = readRequiredText("Enter new creature name: ");

                    System.out.print("Confirm rename? (Y/N): ");
                    String confirmRename = scanner.nextLine();

                    if (confirmRename.equalsIgnoreCase("Y")) {

                        service.renameCreature(renameId, newName);

                    } else {

                        System.out.println("Rename cancelled.");
                    }

                    break;

                case "5":

                    System.out.print("Enter creature ID: ");
                    Long removeId = Long.parseLong(scanner.nextLine());

                    System.out.print("Confirm removal? (Y/N): ");
                    String confirmRemove = scanner.nextLine();

                    if (confirmRemove.equalsIgnoreCase("Y")) {

                        service.removeCreature(removeId);

                    } else {

                        System.out.println("Removal cancelled.");
                    }

                    break;

                case "6":

                    System.out.print("Enter creature ID: ");
                    Long observationId = Long.parseLong(scanner.nextLine());

                    service.viewCreatureObservations(observationId);

                    break;

                case "7":

                    System.out.print("Enter feeding time (HH:MM): ");
                    String feedingTime = scanner.nextLine();

                    service.findCreaturesByFeedingTime(feedingTime);

                    break;

                case "8":
                    service.viewAllSystemUsers();
                    break;

                case "0":

                    System.out.print("Confirm exit? (Y/N): ");
                    String confirmExit = scanner.nextLine();

                    if (confirmExit.equalsIgnoreCase("Y")) {
                        running = false;
                        System.out.println("Exiting Neon Ark CLI...");
                    } else {
                        System.out.println("Exit cancelled.");
                    }

                    break;

                default:
                    System.out.println("Invalid selection.");
            }

            System.out.println();
        }
    }

    private void printMenu() {

        System.out.println("=====================================");
        System.out.println("       NEON ARK CLI SYSTEM");
        System.out.println("=====================================");
        System.out.println();
        System.out.println("1. List all creatures");
        System.out.println("2. View creature by ID");
        System.out.println("3. Register new creature");
        System.out.println("4. Rename creature");
        System.out.println("5. Remove creature");
        System.out.println("6. View creature observations/notes");
        System.out.println("7. Find creatures by feeding time");
        System.out.println();
        System.out.println("--- Admin Only ---");
        System.out.println("8. View all system users");
        System.out.println();
        System.out.println("0. Exit");
        System.out.println("-------------------------------------");
    }

    private int readDangerLevel() {
        while (true) {
            System.out.print("Enter danger level (1-5): ");

            try {
                int dangerLevel = Integer.parseInt(scanner.nextLine());

                if (dangerLevel >= 1 && dangerLevel <= 5) {
                    return dangerLevel;
                }

                System.out.println("Danger level must be between 1 and 5.");

            } catch (NumberFormatException e) {
                System.out.println("Danger level must be a whole number.");
            }
        }
    }

    private String readCondition() {
        while (true) {
            System.out.print("Enter condition (STABLE, WATCH, CRITICAL, QUARANTINE): ");
            String condition = scanner.nextLine().trim().toUpperCase();

            if (condition.equals("STABLE") ||
                    condition.equals("WATCH") ||
                    condition.equals("CRITICAL") ||
                    condition.equals("QUARANTINE")) {
                return condition;
            }

            System.out.println("Invalid condition. Allowed values: STABLE, WATCH, CRITICAL, QUARANTINE.");
        }
    }

    private String readStatus() {
        while (true) {
            System.out.print("Enter status (ACTIVE or REMOVED): ");
            String status = scanner.nextLine().trim().toUpperCase();

            if (status.equals("ACTIVE") || status.equals("REMOVED")) {
                return status;
            }

            System.out.println("Invalid status. Allowed values: ACTIVE, REMOVED.");
        }
    }

    private String readRequiredText(String prompt) {

        while (true) {

            System.out.print(prompt);

            String input = scanner.nextLine().trim();

            if (!input.isBlank()) {
                return input;
            }

            System.out.println("This field is required.");
        }
    }

    private long readPositiveLong(String prompt) {

        while (true) {

            System.out.print(prompt);

            try {

                long value = Long.parseLong(scanner.nextLine());

                if (value > 0) {
                    return value;
                }

                System.out.println("Value must be greater than 0.");

            } catch (NumberFormatException e) {

                System.out.println("Please enter a valid number.");
            }
        }
    }
}