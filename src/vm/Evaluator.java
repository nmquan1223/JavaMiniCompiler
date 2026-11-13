package vm;

import ast.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Evaluator {
    private HashMap<String, Integer> variables;

    public Evaluator(HashMap<String, Integer> variables) { //Constructor
        this.variables = variables;
    }

    public int evaluate(Expr expr) {
        if (expr instanceof NumberNode) { //Handle ast.NumberNode
            NumberNode numberNode = (NumberNode) expr;
            return numberNode.getValue();
        }
        else if (expr instanceof BinaryOpNode) { //Handle ast.BinaryOpNode
            BinaryOpNode binaryOpNode = (BinaryOpNode) expr;
            int left = evaluate(binaryOpNode.getLeft());
            int right = evaluate(binaryOpNode.getRight());
            String operator = binaryOpNode.getOperator();
            switch (operator) {
                case "+" : return left + right;
                case "-" : return left - right;
                case "*" : return left * right;
                case "/" : if (right == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                else {
                    return left / right;
                }
                default: throw new IllegalArgumentException("Unknown operator: " + operator);
            }
        }
        else if (expr instanceof VariableNode) { //Handle ast.VariableNode
            VariableNode variableNode = (VariableNode) expr;
            String name = variableNode.getName();
            if (variables.containsKey(name)) {
                return variables.get(name);
            }
            else {
                throw new IllegalArgumentException("Undefined variable: " + name);
            }
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    public void execute(Stmt stmt) {
        if (stmt instanceof AssignmentNode) {
            AssignmentNode assignmentNode = (AssignmentNode) stmt;
            String name = assignmentNode.getName();
            Expr right = assignmentNode.getValue();
            int value = evaluate(right);
            variables.put(name, value);
        }
        else if (stmt instanceof PrintNode) {
            PrintNode printNode = (PrintNode) stmt;
            Expr expression = printNode.getExpression();
            int value = evaluate(expression);
            System.out.println(value);
        }
        else if (stmt instanceof IfNode) {
            IfNode ifNode = (IfNode) stmt;
            Expr condition = ifNode.getCondition();
            int value = evaluate(condition);
            if (value != 0) {
                executeProgram(ifNode.getBody());
            }
        }
        else if (stmt instanceof WhileNode) {
            WhileNode whileNode = (WhileNode) stmt;
            Expr condition = whileNode.getCondition();
            while (evaluate(condition) != 0) {
                executeProgram(whileNode.getBody());
            }
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    public void executeProgram(ArrayList<Stmt> statements) {
        for (int i=0; i<statements.size(); i++) {
            execute(statements.get(i));
        }
    }
}
