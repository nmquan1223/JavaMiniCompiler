package ast;

public class PrintNode implements Stmt {
    private Expr expression;

    public PrintNode(Expr expression) {
        this.expression = expression;
    }
    public Expr getExpression() {
        return expression;
    }
}
