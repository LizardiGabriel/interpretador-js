package parser;
import paquetito.Token;


public class ExprAssign extends Expression{
    final Token operator;
    final Expression value;
    final Expression name;

    public ExprAssign(Expression value, Token operator, Expression name) {
        this.name = name;
        this.value = value;
        this.operator = operator;
    }
}
