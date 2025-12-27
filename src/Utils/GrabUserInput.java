package Utils;

import Enums.Region;

import java.util.Scanner;

public class GrabUserInput {
    private final Scanner scanner;

    public GrabUserInput() {
        this.scanner = new Scanner(System.in);
    }

    public String grabInputForSummoner() {
        System.out.println("Please provide Summoner Name with TagLine, Example: Name#NA000");
        String input = scanner.nextLine();
        return input;
    }

    public Region promptForRegion() {
        Region[] regions = Region.values();

        for (int i = 0; i < regions.length; i++) {
            System.out.println((i + 1) + ") " + regions[i]);
        }

        while (true) {
            System.out.print("Select region (1-" + regions.length + "): ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 1 && choice <= regions.length) {
                    return regions[choice - 1];
                }
            } catch (NumberFormatException ignored) {}

            System.out.println("Invalid selection.");
        }
    }
}
