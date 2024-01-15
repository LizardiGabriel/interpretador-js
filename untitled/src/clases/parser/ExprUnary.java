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
                if (derechita instanceof Double) {
                    throw new RuntimeException("No se puede negar un double");
                }
                if(derechita instanceof Integer){
                    throw new RuntimeException("No se puede negar un int");
                }
                if (derechita instanceof String) {
                    throw new RuntimeException("No se puede negar un string");
                }
                return !(boolean)derechita;
            case MINUS:
                if (derechita instanceof Boolean) {
                    throw new RuntimeException("No se puede restar un boolean");
                }
                if (derechita instanceof String) {
                    throw new RuntimeException("No se puede restar un string");
                }
                if(derechita instanceof Integer){
                    return -(int)derechita;
                }
                if (derechita instanceof Double) {
                    return -(double)derechita;
                }
                throw new RuntimeException("No se puede restar un " + derechita.getClass().getSimpleName());
            default:
                throw new RuntimeException("Operador desconocido: " + operator.getLexema());
        }
    }
}
