package clases.parser;


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
}
