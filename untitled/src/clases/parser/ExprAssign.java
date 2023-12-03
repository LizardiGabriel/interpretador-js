package clases.parser;
import clases.paquetito.Token;


public class ExprAssign extends Expression{
    final Token operator;
    final Expression value;
    final Expression name;

    public ExprAssign(Expression value, Token operator, Expression name) {
        this.name = name;
        this.value = value;
        this.operator = operator;
        //System.out.println("ExprAssign: " + operator + " " + name + " " + value);
    }
}
