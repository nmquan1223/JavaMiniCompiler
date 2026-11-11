import java.nio.file.Path;
import java.nio.file.Files;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1. Read path
        Path path = Path.of("program.mini");
        // 2. Read content
        String content = Files.readString(path);

        // Identify "NUMBER" token
        int i = 0;
        while (i < content.length()) {
            // kiểm tra kí tự hiện tại
            if (Character.isDigit(content.charAt(i))) {
                String number = "";
                while (i < content.length() && Character.isDigit(content.charAt(i))) {
                    number += content.charAt(i);
                    i++;
                }
                System.out.println("Number: " + number);
            }
            else {
                i++;
            }
        }

        // Identify "IDENTIFIER" token

    }
}
