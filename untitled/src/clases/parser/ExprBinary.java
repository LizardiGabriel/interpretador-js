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
                return (double)izquierdita + (double)derechita;
            case MINUS:
                return (double)izquierdita - (double)derechita;
            case STAR:
                return (double)izquierdita * (double)derechita;
            case SLASH:
                return (double)izquierdita / (double)derechita;

            default:
                throw new RuntimeException("Operador desconocido: " + operator.getLexema());
        }
    }

}
