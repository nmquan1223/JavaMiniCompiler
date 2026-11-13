public class Instruction {
    private OpCode opCode;
    private Object operand;

    public Instruction(OpCode opCode, Object operand) {
        this.operand = operand;
        this.opCode = opCode;
    }
    public OpCode getOpCode() {
        return opCode;
    }
    public Object getOperand() {
        return operand;
    }
}
