package clases.parser;
import clases.paquetito.Token;
import clases.paquetito.TablaSimbolos;

public class ExprUnary extends Expression{
    final Token operator;
    final Expression right;

    public ExprUnary(Token operator, Expression right) {
        this.operator = operator;
        this.right = right;
        //System.out.printf("ExprUnary:" + operator + " " + right);
    }
    @Override
    public String toString() {
        return "\n--> ExprUnary: " + operator.getLexema() + " " + right.toString();
    }

    @Override
    public Object resolver(TablaSimbolos tablita) {
        Object derechita = right.resolver(tablita);
        switch (operator.getTipo()) {
            case BANG:
                return !(boolean)derechita;
            case MINUS:
                return -(double)derechita;
            default:
                throw new RuntimeException("Operador desconocido: " + operator.getLexema());
        }
    }
}
