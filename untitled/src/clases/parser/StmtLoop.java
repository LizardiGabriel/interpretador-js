package clases.parser;


import clases.paquetito.TablaSimbolos;

public class StmtLoop extends Statement {
    final Expression condition;
    final Statement body;

    public StmtLoop(Expression condition, Statement body) {
        this.condition = condition;
        this.body = body;
        //System.out.println("StmtLoop: " + condition + " " + body);
    }

    @Override
    public String toString() {
        return "\n--> StmtLoop: [condition: " + String.valueOf(condition) + ", body: " + body.toString() + "]";
    }

    @Override
    public Object resolver(TablaSimbolos tablita) {
        while ((boolean)condition.resolver(tablita)) {
            body.resolver(tablita);
        }
        return null;
    }


}
