package ast;

public class BinaryOpNode implements Expr {
    private Expr left;
    private Expr right;
    private String operator;

    public BinaryOpNode(Expr left, Expr right, String operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    public Expr getLeft() {
        return left;
    }

    public Expr getRight() {
        return right;
    }

    public String getOperator() {
        return operator;
    }
}
