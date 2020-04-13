package flashcards;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String choose = "";
        Map<String, String> cards = new TreeMap<>();

        while (!"exit".equals(choose)) {
            System.out.println("Input the action (add, remove, import, export, ask, exit):");
            choose = scanner.nextLine();

            switch(choose) {
                case "exit":
                    System.out.println("Bye bye!");
                    break;
                case "add":
                    addCard(scanner, cards);
                    break;
                case "remove":
                    removeCard(scanner, cards);
                    break;
                case "import":
                    importCards(scanner, cards);
                    break;
                case "export":
                    exportCards(scanner, cards);
                    break;
                case "ask":
                    ask(scanner, cards);
                    break;
                default:
                    break;
            }
        }
    }

    private static void addCard(Scanner scanner, Map<String, String> cards) {
        System.out.println("The card:");
        String cardName = scanner.nextLine();

        if (cards.containsKey(cardName)) {
            System.out.println("The card \"" + cardName + "\" already exists.");
        } else {
            System.out.println("The definition of the card:");
            String cardDef = scanner.nextLine();

            if (cards.containsValue(cardDef)) {
                System.out.println("The definition \"" + cardDef + "\" already exists.");
            } else {
                cards.put(cardName, cardDef);
                System.out.println("The pair (\"" + cardName + "\":\"" + cardDef + "\") has " +
                        "been added.");
            }
        }
    }

    private static void removeCard(Scanner scanner, Map<String, String> cards) {
        System.out.println("The card:");
        String cardName = scanner.nextLine();

        if (cards.containsKey(cardName)) {
            cards.remove(cardName);
            System.out.println("The card has been removed.");
        } else {
            System.out.println("Can't remove \"" + cardName + "\": there is no such card.");
        }
    }

    private static void importCards(Scanner scanner, Map<String, String> cards) {
        System.out.println("File name:");
        String fileName = scanner.nextLine();
        File file = new File(fileName);
        int counter = 0;

        if (file.exists()) {
            try (Scanner fileScanner = new Scanner(file)) {
                while (fileScanner.hasNext()) {
                    String cardName = fileScanner.nextLine();

                    if (fileScanner.hasNext()) {
                        cards.put(cardName, fileScanner.nextLine());
                        counter++;
                    }
                }
            } catch (FileNotFoundException e){
                System.out.printf("An exception occurs %s", e.getMessage());
            }

            System.out.println(counter + " cards have been loaded.");
        } else {
            System.out.println("File not found.");
        }
    }

    private static void exportCards(Scanner scanner, Map<String, String> cards) {
        System.out.println("File name:");
        String fileName = scanner.nextLine();
        File file = new File(fileName);
        int counter = 0;

        try (FileWriter writer = new FileWriter(file)) {
            for (String cardName : cards.keySet()) {
                writer.write(cardName + "\n");
                writer.write(cards.get(cardName) + "\n");
                counter++;
            }
        } catch (IOException e){
            System.out.printf("An exception occurs %s", e.getMessage());
        }

        System.out.println(counter + " cards have been saved.");
    }

    private static void ask(Scanner scanner, Map<String, String> cards) {
        System.out.println("How many times to ask?");
        int times = Integer.parseInt(scanner.nextLine());
        int counter = 0;

        while (counter < times) {
            for (String cardName : cards.keySet()) {
                System.out.println("Print the definition of \"" + cardName + "\":");
                String answer = scanner.nextLine();

                if (answer.equals(cards.get(cardName))) {
                    System.out.println("Correct answer.");
                } else {
                    if (cards.containsValue(answer)) {
                        String cardCheck = "";

                        for (String cardNameCheck : cards.keySet()) {
                            if (cards.get(cardNameCheck).equals(answer)) {
                                cardCheck = cardNameCheck;
                                break;
                            }
                        }

                        System.out.println("Wrong answer. The correct one is \"" + cards.get(cardName)
                                + "\", you've just written the definition of \"" + cardCheck + "\".");
                    } else {
                        System.out.println("Wrong answer. The correct one is \"" + cards.get(cardName) + "\".");
                    }
                }

                counter++;

                if (counter >= times) {
                    break;
                }
            }
        }
    }
}