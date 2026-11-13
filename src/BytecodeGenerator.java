import java.util.ArrayList;

public class BytecodeGenerator {
    private ArrayList<Instruction> instructions;

    public BytecodeGenerator() {
        this.instructions = new ArrayList<Instruction>();
    }
    public ArrayList<Instruction> getInstructions() {
        return instructions;
    }
    public void emit(OpCode opCode, Object operand) {
        instructions.add(new Instruction(opCode, operand));
    }
    public void generateExpressions(Expr expr) {
        if (expr instanceof NumberNode) {
            NumberNode numberNode = (NumberNode) expr;
            emit(OpCode.PUSH, numberNode.getValue());
        }
        else if (expr instanceof VariableNode) {
            VariableNode variableNode = (VariableNode) expr;
            emit(OpCode.LOAD, variableNode.getName());
        }
        else if (expr instanceof BinaryOpNode) {
            BinaryOpNode binaryOpNode = (BinaryOpNode) expr;
            Expr left = binaryOpNode.getLeft();
            Expr right = binaryOpNode.getRight();
            String operator = binaryOpNode.getOperator();
            generateExpressions(left);
            generateExpressions(right);
            switch (operator) {
                case "+" : emit(OpCode.ADD, null); break;
                case "-" : emit(OpCode.SUB, null); break;
                case "*" : emit(OpCode.MUL, null); break;
                case "/" : emit(OpCode.DIV, null); break;
            }
        }
    }
    public void generateStatement(Stmt stmt) {
        if (stmt instanceof AssignmentNode) {
            AssignmentNode assignmentNode = (AssignmentNode) stmt;
            String name = assignmentNode.getName();
            Expr right = assignmentNode.getValue();
            generateExpressions(right);
            emit(OpCode.STORE, name);
        }
        else if (stmt instanceof PrintNode) {
            PrintNode printNode = (PrintNode) stmt;
            Expr expressions = printNode.getExpression();
            generateExpressions(expressions);
            emit(OpCode.PRINT, null);
        }
        else if (stmt instanceof IfNode) {
            IfNode ifNode = (IfNode) stmt;
            Expr condition = ifNode.getCondition();
            generateExpressions(condition);
            int index = instructions.size();
            emit(OpCode.JUMP_IF_FALSE, null);
            ArrayList<Stmt> body = ifNode.getBody();
            for (int i=0; i<body.size(); i++) {
                generateStatement(body.get(i));
            }
            patchJump(index, instructions.size());
        }
        else if (stmt instanceof WhileNode) {
            WhileNode whileNode = (WhileNode) stmt;
            int loopStart = instructions.size();
            Expr condition = whileNode.getCondition();
            generateExpressions(condition);
            int jumpIndex = instructions.size();
            emit(OpCode.JUMP_IF_FALSE, null);
            ArrayList<Stmt> body = whileNode.getBody();
            for (int i=0; i<body.size(); i++) {
                generateStatement(body.get(i));
            }
            emit(OpCode.JUMP, loopStart);
            patchJump(jumpIndex, instructions.size());
        }
    }
    public void patchJump(int index, int target) {
        OpCode opCode = (instructions.get(index)).getOpCode();
        instructions.set(index, new Instruction(opCode, target));
    }
    public void generateProgram(ArrayList<Stmt> statement) {
        for (int i=0; i<statement.size(); i++) {
            generateStatement(statement.get(i));
        }
        emit(OpCode.HALT, null);
    }
}
