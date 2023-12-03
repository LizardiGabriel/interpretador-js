package clases.parser;

public class ExprLiteral extends Expression {
    final Object value;

    public ExprLiteral(Object value) {
        this.value = value;
        //System.out.println("ExprLiteral: " + value);
    }
}
