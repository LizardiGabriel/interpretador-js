package clases.parser;
import clases.paquetito.TablaSimbolos;
import clases.paquetito.Token;

public class ExprLogical extends Expression{
    final Expression left;
    final Token operator;
    final Expression right;

    public ExprLogical(Expression left, Token operator, Expression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
        //System.out.printf("ExprLogical:" + left + " " + operator + " " + right);
    }
    @Override
    public String toString() {
        return "\n--> ExprLogical: " + left.toString() + " " + operator.getLexema() + " " + right.toString();
    }

    @Override
    public Object resolver(TablaSimbolos tablita) {
        Object izquierdita = left.resolver(tablita);
        Object derechita = right.resolver(tablita);
        switch (operator.getTipo()) {
            case AND:
                return (boolean)izquierdita && (boolean)derechita;
            case OR:
                return (boolean)izquierdita || (boolean)derechita;
            case EQUAL_EQUAL:
                return izquierdita.equals(derechita);
            case BANG_EQUAL:
                return !izquierdita.equals(derechita);
            case GREATER:
                return (double)izquierdita > (double)derechita;
            case GREATER_EQUAL:
                return (double)izquierdita >= (double)derechita;
            case LESS:
                return (double)izquierdita < (double)derechita;
            case LESS_EQUAL:
                return (double)izquierdita <= (double)derechita;
            default:
                throw new RuntimeException("Operador desconocido: " + operator.getLexema());
        }
    }
}

