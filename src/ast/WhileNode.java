package ast;

import java.util.ArrayList;

public class WhileNode implements Stmt {
    private Expr condition;
    private ArrayList<Stmt> body;

    public WhileNode(Expr condition, ArrayList<Stmt> body) {
        this.condition = condition;
        this.body = body;
    }
    public Expr getCondition() {
        return condition;
    }
    public ArrayList<Stmt> getBody() {
        return body;
    }
}
