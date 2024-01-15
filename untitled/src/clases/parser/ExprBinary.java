package clases.parser;
import clases.paquetito.Token;
import clases.paquetito.TablaSimbolos;

import static java.lang.System.exit;

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
            }
            if(izquierdita instanceof Integer && derechita instanceof Integer){
                return (Integer)izquierdita + (Integer) derechita;
            }
            if(izquierdita instanceof Double && derechita instanceof Double){
                return ((Number) izquierdita).doubleValue() + ((Number) derechita).doubleValue();
            }
            if(izquierdita instanceof Double && derechita instanceof Integer){
                return (Double)izquierdita + ((Number) derechita).doubleValue();
            }
            if(izquierdita instanceof Integer && derechita instanceof Double){
                return ((Number) izquierdita).doubleValue() + (Double) derechita;
            }
            System.out.println("\tError: No es posible sumar un " + izquierdita.getClass().getSimpleName() + " con " + derechita.getClass().getSimpleName());
            exit(0);
        case MINUS:
            if(izquierdita instanceof Integer && derechita instanceof Integer){
                return (Integer)izquierdita - (Integer) derechita;
            }
            if(izquierdita instanceof Double && derechita instanceof Double){
                return ((Number) izquierdita).doubleValue() - ((Number) derechita).doubleValue();
            }
            if(izquierdita instanceof Double && derechita instanceof Integer){
                return (Double)izquierdita - ((Number) derechita).doubleValue();
            }
            if(izquierdita instanceof Integer && derechita instanceof Double){
                return ((Number) izquierdita).doubleValue() - (Double) derechita;
            }
            System.out.println("\tError: No es posible restar un " + izquierdita.getClass().getSimpleName() + " con " + derechita.getClass().getSimpleName());
            exit(0);
        case STAR:
            if(izquierdita instanceof Integer && derechita instanceof Integer){
                return (Integer)izquierdita * (Integer) derechita;
            }
            if(izquierdita instanceof Double && derechita instanceof Double){
                return ((Number) izquierdita).doubleValue() * ((Number) derechita).doubleValue();
            }
            if(izquierdita instanceof Double && derechita instanceof Integer){
                return (Double)izquierdita * ((Number) derechita).doubleValue();
            }
            if(izquierdita instanceof Integer && derechita instanceof Double){
                return ((Number) izquierdita).doubleValue() * (Double) derechita;
            }
            System.out.println("\tError: No es posible multiplicar un " + izquierdita.getClass().getSimpleName() + " con " + derechita.getClass().getSimpleName());
            exit(0);
        case SLASH:
            if(izquierdita instanceof Integer && derechita instanceof Integer){
                if((Integer)derechita == 0){
                    System.out.println("\tError: No es dividir por 0");
                    exit(0);
                }
                return (Integer)izquierdita / (Integer) derechita;
            }
            if(izquierdita instanceof Double && derechita instanceof Double){
                if((Double)derechita == 0){
                    System.out.println("\tError: No es dividir por 0");
                    exit(0);
                }
                return (Double)izquierdita / (Double)derechita;
            }
            if(izquierdita instanceof Double && derechita instanceof Integer){
                if(((Number) derechita).doubleValue() == 0){
                    System.out.println("\tError: No es dividir por 0");
                    exit(0);
                }
                return (Double)izquierdita / ((Number) derechita).doubleValue();
            }
            if(izquierdita instanceof Integer && derechita instanceof Double){
                if((Double)derechita == 0){
                    System.out.println("\tError: No es dividir por 0");
                    exit(0);
                }
                return ((Number) izquierdita).doubleValue() / (Double) derechita;
            }
            System.out.println("\tError: No es posible dividir un " + izquierdita.getClass().getSimpleName() + " con " + derechita.getClass().getSimpleName());
            exit(0);
        default:
            throw new RuntimeException("Operador desconocido: " + operator.getLexema());
    }
}

}
