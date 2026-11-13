package ast;

public class VariableNode implements Expr {
    private String name;

    public VariableNode(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
