package clases.parser;


public class StmtLoop extends Statement {
    final Expression condition;
    final Statement body;

    public StmtLoop(Expression condition, Statement body) {
        this.condition = condition;
        this.body = body;
        //System.out.println("StmtLoop: " + condition + " " + body);
    }
}
