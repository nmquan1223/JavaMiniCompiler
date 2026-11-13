import java.nio.file.Path;
import java.nio.file.Files;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        Path path = Path.of("program.mini");

        String content = Files.readString(path);
        ArrayList<Token> tokens = new Lexer(content).tokenize();

        Parser parser = new Parser(tokens);
        ArrayList<Stmt> statements = parser.parseProgram();

        BytecodeGenerator generator = new BytecodeGenerator();
        generator.generateProgram(statements);
        ArrayList<Instruction> byteCode = generator.getInstructions();

        VirtualMachine virtualMachine = new VirtualMachine(byteCode);
        virtualMachine.run();
    }
}
