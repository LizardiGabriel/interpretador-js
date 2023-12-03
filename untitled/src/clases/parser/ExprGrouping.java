package clases.parser;


public class ExprGrouping extends Expression {
    final Expression expression;

    public ExprGrouping(Expression expression) {
        this.expression = expression;
        //System.out.println("ExprGrouping: " + expression);
    }
}
