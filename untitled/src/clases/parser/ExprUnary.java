package clases.parser;
import clases.paquetito.Token;

public class ExprUnary extends Expression{
    final Token operator;
    final Expression right;

    public ExprUnary(Token operator, Expression right) {
        this.operator = operator;
        this.right = right;
        //System.out.printf("ExprUnary:" + operator + " " + right);
    }
}
