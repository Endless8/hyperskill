import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String seq = scanner.nextLine();
        double counter = 0;
        double total = 0;

        for (char ch : seq.toCharArray()) {
            Character upperCh = Character.toUpperCase(ch);

            if ('C' == upperCh || 'G' == upperCh) {
                counter++;
            }

            total++;
        }

        System.out.println(counter / total * 100);
    }
}