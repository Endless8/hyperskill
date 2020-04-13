package encryptdecrypt;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String action = "enc";
        int key = 0;
        String text = "";
        int shiftKey = 0;
        String encr = "";
        String argsString = Arrays.toString(args);
        boolean data = false;
        boolean out = false;
        String outFile = "";
        Algorithm alg = new Shift();

        if (argsString.contains("-data")) {
            data = true;
        }

        if (argsString.contains("-out")) {
            out = true;
        }

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-mode":
                    action = args[i + 1];
                    break;
                case "-key":
                    key = Integer.parseInt(args[i + 1]);
                    break;
                case "-data":
                    text = args[i + 1];
                    break;
                case "-in":
                    if (!data) {
                        File file = new File(args[i + 1]);

                        try (Scanner scanner = new Scanner(file)) {
                            text = scanner.nextLine();
                        } catch (FileNotFoundException e) {
                            System.out.println("Error! The file doesn't exists.");
                        }

                    }
                    break;
                case "-out":
                    outFile += args[i + 1];
                    break;
                case "-alg":
                    if ("unicode".equals(args[i + 1])) {
                        alg = new Unicode();
                    }
                    break;
            }
        }

        if ("enc".equals(action)) {
            /*for (char ch : text.toCharArray()) {
                while (key > 255) {
                    key -= 255;
                }

                encr += (char) (ch + key);
            }*/
            encr = alg.encrypt(text, key, encr);
        } else if ("dec".equals(action)) {
            /*for (char ch : text.toCharArray()) {
                while (key > 255) {
                    key -= 255;
                }

                encr += (char) (ch - key);
            }*/
            encr = alg.decrypt(text, key, encr);
        }

        if (!out) {
            System.out.println(encr);
        } else {
            File file = new File(outFile);

            try (FileWriter writer = new FileWriter(file)) {
                writer.write(encr);
            } catch (IOException e) {
            }
        }
    }
}

interface Algorithm {

    String encrypt(String text, int key, String encr);

    String decrypt(String text, int key, String encr);
}

class Shift implements Algorithm {

    @Override
    public String encrypt(String text, int key, String encr) {
        int shiftKey = 0;

        for (char ch : text.toCharArray()) {
            if (ch == ' ') {
                encr += ' ';
                continue;
            }

            while (key > 26) {
                key -= 26;
            }

            if ((ch + key) > 122 && ch > 96) {
                shiftKey = key - (122 - ch);
                encr += (char) (96 + shiftKey);
            } else if ((ch + key) > 90 && ch < 91) {
                shiftKey = key - (90 - ch);
                encr += (char) (64 + shiftKey);
            } else {
                encr += (char) (ch + key);
            }
        }

        return encr;
    }

    @Override
    public String decrypt(String text, int key, String encr) {
        int shiftKey = 0;

        for (char ch : text.toCharArray()) {
            if (ch == ' ') {
                encr += ' ';
                continue;
            }

            while (key > 26) {
                key -= 26;
            }

            if ((ch - key) < 96 && ch > 96) {
                shiftKey = key - (ch - 96);
                encr += (char) (122 - shiftKey);
            } else if ((ch - key) < 64 && ch < 91) {
                shiftKey = key - (ch - 64);
                encr += (char) (90 - shiftKey);
            } else {
                encr += (char) (ch - key);
            }
        }

        return encr;
    }
}

class Unicode implements Algorithm {

    @Override
    public String encrypt(String text, int key, String encr) {
        for (char ch : text.toCharArray()) {
            while (key > 255) {
                key -= 255;
            }

            encr += (char) (ch + key);
        }

        return encr;
    }

    @Override
    public String decrypt(String text, int key, String encr) {
        for (char ch : text.toCharArray()) {
            while (key > 255) {
                key -= 255;
            }

            encr += (char) (ch - key);
        }

        return encr;
    }
}