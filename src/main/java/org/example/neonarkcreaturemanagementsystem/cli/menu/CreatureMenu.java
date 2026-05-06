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
                    System.out.print("Enter creature name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter species: ");
                    String species = scanner.nextLine();

                    System.out.print("Enter danger level (1-5): ");
                    int dangerLevel = Integer.parseInt(scanner.nextLine());

                    System.out.print("Enter condition (STABLE, WATCH, CRITICAL, QUARANTINE): ");
                    String condition = scanner.nextLine().toUpperCase();

                    System.out.print("Enter notes: ");
                    String notes = scanner.nextLine();

                    System.out.print("Enter status (ACTIVE or REMOVED): ");
                    String status = scanner.nextLine().toUpperCase();

                    System.out.print("Enter habitat ID: ");
                    long habitatId = Long.parseLong(scanner.nextLine());

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

                    System.out.print("Enter new creature name: ");
                    String newName = scanner.nextLine();

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

        System.out.println("====================================");
        System.out.println("     NEON ARK CREATURE SYSTEM");
        System.out.println("====================================");
        System.out.println("1. List All Creatures");
        System.out.println("2. View Creature By ID");
        System.out.println("3. Register New Creature");
        System.out.println("4. Rename Creature");
        System.out.println("5. Remove Creature");
        System.out.println("6. View Creature Observations");
        System.out.println("7. Feeding Schedule Lookup");
        System.out.println("8. View System Users");
        System.out.println("0. Exit");
        System.out.println("====================================");
    }
}