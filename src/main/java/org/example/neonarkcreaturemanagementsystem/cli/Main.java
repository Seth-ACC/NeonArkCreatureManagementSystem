package org.example.neonarkcreaturemanagementsystem.cli;

import org.example.neonarkcreaturemanagementsystem.cli.api.CreatureApiClient;
import org.example.neonarkcreaturemanagementsystem.cli.menu.CreatureMenu;
import org.example.neonarkcreaturemanagementsystem.cli.service.CreatureCliService;

public class Main {

    public static void main(String[] args) {
        CreatureApiClient apiClient = new CreatureApiClient();
        CreatureCliService service = new CreatureCliService(apiClient);
        CreatureMenu menu = new CreatureMenu(service);

        menu.start();
    }
}