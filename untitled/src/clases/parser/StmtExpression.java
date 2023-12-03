package clases.parser;


public class StmtExpression extends Statement {
    final Expression expression;

    public StmtExpression(Expression expression) {

        this.expression = expression;
        //System.out.println("StmtExpression: " + expression);
    }
}
