package clases.parser;


public class StmtPrint extends Statement {
    final Expression expression;

    public StmtPrint(Expression expression) {

        this.expression = expression;
        //System.out.println("StmtPrint: " + expression);
    }
    @Override
    public String toString() {
        return "\n--> StmtPrint: " + expression.toString();
    }
}
