package clases.parser;
import clases.paquetito.Token;
import clases.paquetito.TablaSimbolos;

public class ExprBinary extends Expression{
    final Expression left;
    final Token operator;
    final Expression right;

    public ExprBinary(Expression left, Token operator, Expression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
        }
    @Override
    public String toString() {

        return "\n--> ExprBinary: {left: [" + String.valueOf(left) + "] **(" + operator.getLexema() + ")** right[" + String.valueOf(right)+"]}FinExprBin";
    }
    /**
     * Se resuelve la expresion binaria
     * @param tablita
     * @return el resultado de la expr binaria
     * @throws RuntimeException si el operador no es conocido
     * */
    @Override
    public Object resolver(TablaSimbolos tablita){
    Object izquierdita = left.resolver(tablita);
    Object derechita = right.resolver(tablita);
    switch (operator.getTipo()) {
        case PLUS:
            // Si uno de los operandos es una cadena, concatena los operandos como cadenas
            if (izquierdita instanceof String || derechita instanceof String) {
                return izquierdita.toString() + derechita.toString();
            } else {
                // De lo contrario, suma los operandos como números
                return Double.valueOf(izquierdita.toString()) + Double.valueOf(derechita.toString());
            }
        case MINUS:
            return Double.valueOf(izquierdita.toString()) - Double.valueOf(derechita.toString());
        case STAR:
            return Double.valueOf(izquierdita.toString()) * Double.valueOf(derechita.toString());
        case SLASH:
            return Double.valueOf(izquierdita.toString()) / Double.valueOf(derechita.toString());
        case LESS_EQUAL:
            return Double.valueOf(izquierdita.toString()) <= Double.valueOf(derechita.toString());
        case GREATER_EQUAL:
            return Double.valueOf(izquierdita.toString()) >= Double.valueOf(derechita.toString());
        case LESS:
            return Double.valueOf(izquierdita.toString()) < Double.valueOf(derechita.toString());
        case GREATER:
            return Double.valueOf(izquierdita.toString()) > Double.valueOf(derechita.toString());
        default:
            throw new RuntimeException("Operador desconocido: " + operator.getLexema());
    }
}

}
