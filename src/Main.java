import java.nio.file.Path;
import java.nio.file.Files;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1. Read path
        Path path = Path.of("program.mini");
        // 2. Read content
        String content = Files.readString(path);
        ArrayList<Token> tokens = new Lexer(content).tokenize();

        Parser parser = new Parser(tokens); //Initialize Parser
        ArrayList<Stmt> stmt = parser.parseProgram(); //To get ArrayList<Stmt>

        BytecodeGenerator statement = new BytecodeGenerator();
        statement.generateProgram(stmt);
        ArrayList<Instruction> byteCode = statement.getInstructions();

        for (int i=0; i<byteCode.size(); i++) {
            Instruction instruction = byteCode.get(i);
            OpCode opCode = instruction.getOpCode();
            Object operand = instruction.getOperand();
            System.out.println(opCode + " " + operand);
        }
    }
}
