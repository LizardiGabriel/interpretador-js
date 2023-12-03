package clases.parser;


public class StmtPrint extends Statement {
    final Expression expression;

    public StmtPrint(Expression expression) {

        this.expression = expression;
        //System.out.println("StmtPrint: " + expression);
    }
}
