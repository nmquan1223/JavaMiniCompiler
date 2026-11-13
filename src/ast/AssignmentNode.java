package ast;

public class AssignmentNode implements Stmt {
    private String name;
    private Expr value;

    public AssignmentNode(String name, Expr value) {
        this.name = name;
        this.value = value;
    }
    public String getName() {
        return name;
    }
    public Expr getValue() {
        return value;
    }
}
