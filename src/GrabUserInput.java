import java.util.Scanner;

public class GrabUserInput {
    private final Scanner scanner;

    public GrabUserInput() {
        this.scanner = new Scanner(System.in);
    }

    public String grabInput() {
        System.out.println("Please provide Summoner Name with TagLine, Example: Name#NA000");
        String input = scanner.nextLine();
        return input;
    }
}
