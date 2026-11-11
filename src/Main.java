import java.nio.file.Path;
import java.nio.file.Files;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1. Read path
        Path path = Path.of("program.mini");
        // 2. Read content
        String content = Files.readString(path);
        ArrayList<Token> tokens = new Lexer(content).tokenize();

        for (int i=0; i<tokens.size(); i++) {
            Token token = tokens.get(i);
            System.out.println(token.getType() + ": " + token.getValue());
        }
    }
}
