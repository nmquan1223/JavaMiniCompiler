import java.nio.file.Path;
import java.nio.file.Files;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1. Read path
        Path path = Path.of("program.mini");
        // 2. Read content
        String content = Files.readString(path);
        // Identify token
        int i = 0;
        while (i < content.length()) {
            // kiểm tra kí tự hiện tại
            char c = content.charAt(i); //Operators token
            switch (c) {
                case '+' : System.out.println("Plus"); i++; continue;
                case '-' : System.out.println("Minus"); i++; continue;
                case '*' : System.out.println("Multiply"); i++; continue;
                case '/' : System.out.println("Divide"); i++; continue;
                case '=' : System.out.println("Assign"); i++; continue;
                case '(' : System.out.println("LParen"); i++; continue;
                case ')' : System.out.println("RParen"); i++; continue;
                case ';' : System.out.println("Semicolon"); i++; continue;
                case '{' : System.out.println("LBrace"); i++; continue;
                case '}' : System.out.println("RBrace"); i++; continue;
            }
            if (Character.isDigit(content.charAt(i))) { //Number token
                String number = "";
                while (i < content.length() && Character.isDigit(content.charAt(i))) {
                    number += content.charAt(i);
                    i++;
                }
                System.out.println("Number: " + number);
            }
            else if (Character.isLetter(content.charAt(i))) { //Identifier token
                String identifier = "";
                while (i < content.length() && Character.isLetterOrDigit(content.charAt(i))) {
                    identifier += content.charAt(i);
                    i++;
                }
                switch (identifier) {
                    case "print" : System.out.println("PRINT"); continue;
                    case "if" : System.out.println("IF"); continue;
                    case "while" : System.out.println("WHILE"); continue;
                }
                System.out.println("Identifier: " + identifier);
            }
            else {
                i++;
            }
        }
    }
}
