import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;

class Main {
    public static void main(String[] args) throws Exception {
        try (Reader reader = new BufferedReader(new InputStreamReader(System.in))) {
            char check;
            int wordCount = 0;
            while (true) {
                check = (char) reader.read();

                if (check != -1) {
                    if (check == ' ') {
                        while (reader.read() == ' ') {
                        }

                        wordCount++;
                    }
                } else {
                    break;
                }
            }

            System.out.println(wordCount);
        }
    }

}