package clases.parser;


import clases.paquetito.TablaSimbolos;

public class StmtIf extends Statement {
    final Expression condition;
    final Statement thenBranch;
    final Statement elseBranch;

    public StmtIf(Expression condition, Statement thenBranch, Statement elseBranch) {
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
        //System.out.println("StmtIf: " + condition + " " + thenBranch + " " + elseBranch);
    }

    @Override
    public String toString() {
        return "\n--> StmtIf: {" + String.valueOf(condition) + "\n}\nthen{" + String.valueOf(thenBranch) + "\n}else{" + String.valueOf(elseBranch) + "}<--endStmtIf";
    }

    @Override
    public Object resolver(TablaSimbolos tablita) {
        if ((boolean)condition.resolver(tablita)) {
            return thenBranch.resolver(tablita);
        } else {
            return elseBranch != null ? elseBranch.resolver(tablita) : null;
        }
    }




}
