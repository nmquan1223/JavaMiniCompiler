package vm;

import bytecode.Instruction;
import bytecode.OpCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class VirtualMachine {
    private ArrayList<Instruction> instructions = new ArrayList<>();
    private Stack<Integer> stack;
    private HashMap<String, Integer> variables = new HashMap<>();
    private int pc; //current instructions index

    public VirtualMachine(ArrayList<Instruction> instructions) {
        this.instructions = instructions;
        this.stack = new Stack<>();
        this.variables = new HashMap<>();
        this.pc = 0;
    }
    public void run() {
        while (pc < instructions.size()) {
            Instruction instruction = instructions.get(pc); // fetch
            pc++;
            if (instruction.getOpCode() == OpCode.HALT) {
                return;
            }
            else if (instruction.getOpCode() == OpCode.PUSH) {
                Object operand = instruction.getOperand();
                if (!(operand instanceof Integer)) {
                    throw new IllegalStateException();
                }
                int value = (Integer) operand;
                stack.push(value);
            }
            else if (instruction.getOpCode() == OpCode.STORE) {
                Object operand = instruction.getOperand();
                if (!(operand instanceof String)) {
                    throw new IllegalStateException();
                }
                if (stack.isEmpty()) {
                    throw new IllegalStateException();
                }
                int value = stack.pop();
                String name = (String) operand;
                variables.put(name, value);
            }
            else if (instruction.getOpCode() == OpCode.LOAD) {
                Object operand = instruction.getOperand();
                if (!(operand instanceof String)) {
                    throw new IllegalStateException();
                }
                String name = (String) operand;
                if (!(variables.containsKey(name))) {
                    throw new IllegalStateException(name + " has not been defined");
                }
                int value = variables.get(name);
                stack.push(value);
            }
            else if (instruction.getOpCode() == OpCode.ADD) {
                if (stack.size() < 2) {
                    throw new IllegalStateException();
                }
                int right = stack.pop();
                int left = stack.pop();
                stack.push(left+right);
            }
            else if (instruction.getOpCode() == OpCode.SUB) {
                if (stack.size() < 2) {
                    throw new IllegalStateException();
                }
                int right = stack.pop();
                int left = stack.pop();
                stack.push(left-right);
            }
            else if (instruction.getOpCode() == OpCode.MUL) {
                if (stack.size() < 2) {
                    throw new IllegalStateException();
                }
                int right = stack.pop();
                int left = stack.pop();
                stack.push(left*right);
            }
            else if (instruction.getOpCode() == OpCode.DIV) {
                if (stack.size() < 2) {
                    throw new IllegalStateException();
                }
                int right = stack.pop();
                int left = stack.pop();
                if (right == 0) {
                    throw new ArithmeticException("division by zero");
                }
                stack.push(left/right);
            }
            else if (instruction.getOpCode() == OpCode.PRINT) {
                if (stack.isEmpty()) {
                    throw new IllegalStateException();
                }
                int value = stack.pop();
                System.out.println(value);
            }
            else if (instruction.getOpCode() == OpCode.JUMP) {
                Object operand = instruction.getOperand();
                if (!(operand instanceof Integer)) {
                    throw new IllegalStateException();
                }
                int target = (Integer) operand;
                if (target<0 || target >= instructions.size()) {
                    throw new IllegalStateException();
                }
                pc = target;
            }
            else if (instruction.getOpCode() == OpCode.JUMP_IF_FALSE) {
                Object operand = instruction.getOperand();
                if (!(operand instanceof Integer)) {
                    throw new IllegalStateException();
                }
                int target = (Integer) operand;
                if (target<0 || target >= instructions.size()) {
                    throw new IllegalStateException();
                }
                if (stack.isEmpty()) {
                    throw new IllegalStateException();
                }
                int cond = stack.pop();
                if (cond == 0) {
                    pc = target;
                }
            }
            else {
                throw new UnsupportedOperationException();
            }
        }
        throw new IllegalStateException();
    }
}
