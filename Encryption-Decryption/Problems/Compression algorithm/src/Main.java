import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String dna = scanner.nextLine();
        String encr = "";
        int counter = 1;

        if (dna.length() > 1) {
            for (int i = 0; i < dna.length() - 1; i++) {
                if (dna.charAt(i) == dna.charAt(i + 1)) {
                    counter++;

                    if (i == dna.length() - 2) {
                        encr += (dna.charAt(i) + "" + counter);
                    }
                } else {
                    encr += (dna.charAt(i) + "" + counter);
                    counter = 1;

                    if (i == dna.length() - 2) {
                        encr += (dna.charAt(i + 1) + "" + counter);
                    }
                }
            }
        } else {
            encr += dna.charAt(0) + "" + counter;
        }

        System.out.println(encr);
    }
}