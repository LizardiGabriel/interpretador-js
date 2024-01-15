package clases.parser;
import clases.paquetito.TablaSimbolos;
import clases.paquetito.Token;

import static java.lang.System.exit;

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
                if(izquierdita instanceof Boolean && derechita instanceof Boolean) {
                    return (Boolean)izquierdita && (Boolean) derechita;
                }
                System.out.println("\tError: Los operandos no son booleanos.");
                exit(0);
            case OR:
                if(izquierdita instanceof Boolean && derechita instanceof Boolean) {
                    return (Boolean)izquierdita || (Boolean) derechita;
                }
                System.out.println("\tError: Los operandos no son booleanos.");
                exit(0);
            case EQUAL_EQUAL:
                if(izquierdita instanceof String && derechita instanceof String) {
                    return izquierdita.equals(derechita);
                }
                if(izquierdita instanceof Integer && derechita instanceof Integer){
                    return izquierdita == derechita;
                }
                if(izquierdita instanceof Double && derechita instanceof Double){
                    return izquierdita == derechita;
                }
                if(izquierdita instanceof Double && derechita instanceof Integer){
                    return (Double)izquierdita == ((Number) derechita).doubleValue();
                }
                if(izquierdita instanceof Integer && derechita instanceof Double){
                    return ((Number) izquierdita).doubleValue() == (Double) derechita;
                }
                System.out.println("\tError: No es posible comparar un " + izquierdita.getClass().getSimpleName() + " con " + derechita.getClass().getSimpleName());
                exit(0);
            case BANG_EQUAL:
                if(izquierdita instanceof String && derechita instanceof String) {
                    return !izquierdita.equals(derechita);
                }
                if(izquierdita instanceof Integer && derechita instanceof Integer){
                    return izquierdita != derechita;
                }
                if(izquierdita instanceof Double && derechita instanceof Double){
                    return izquierdita != derechita;
                }
                if(izquierdita instanceof Double && derechita instanceof Integer){
                    return (Double)izquierdita != ((Number) derechita).doubleValue();
                }
                if(izquierdita instanceof Integer && derechita instanceof Double){
                    return ((Number) izquierdita).doubleValue() != (Double) derechita;
                }
                System.out.println("\tError: No es posible comparar un " + izquierdita.getClass().getSimpleName() + " con " + derechita.getClass().getSimpleName());
                exit(0);
            case GREATER:
                if(izquierdita instanceof String || derechita instanceof String) {
                    System.out.println("\tError: No es posible realizar: " +izquierdita +" " + operator.getTipo() + " " + derechita);
                }
                if(izquierdita instanceof Integer && derechita instanceof Integer){
                    return (Integer)izquierdita > (Integer) derechita;
                }
                if(izquierdita instanceof Double && derechita instanceof Double){
                    return (Double)izquierdita > (Double) derechita;
                }
                if(izquierdita instanceof Double && derechita instanceof Integer){
                    return (Double)izquierdita > ((Number) derechita).doubleValue();
                }
                if(izquierdita instanceof Integer && derechita instanceof Double){
                    return ((Number) izquierdita).doubleValue() > (Double) derechita;
                }
                System.out.println("\tError: No es posible comparar un " + izquierdita.getClass().getSimpleName() + " con " + derechita.getClass().getSimpleName());
                exit(0);
            case GREATER_EQUAL:
                if(izquierdita instanceof String || derechita instanceof String) {
                    System.out.println("\tError: No es posible realizar: " +izquierdita +" " + operator.getTipo() + " " + derechita);
                }
                if(izquierdita instanceof Integer && derechita instanceof Integer){
                    return (Integer)izquierdita >= (Integer) derechita;
                }
                if(izquierdita instanceof Double && derechita instanceof Double){
                    return (Double)izquierdita >= (Double) derechita;
                }
                if(izquierdita instanceof Double && derechita instanceof Integer){
                    return (Double)izquierdita >= ((Number) derechita).doubleValue();
                }
                if(izquierdita instanceof Integer && derechita instanceof Double){
                    return ((Number) izquierdita).doubleValue() >= (Double) derechita;
                }
                System.out.println("\tNo es posible comparar un " + izquierdita.getClass().getSimpleName() + " con " + derechita.getClass().getSimpleName());
                exit(0);
            case LESS:
                if(izquierdita instanceof String || derechita instanceof String) {
                    System.out.println("\tError: No es posible realizar: " +izquierdita +" " + operator.getTipo() + " " + derechita);
                }
                if(izquierdita instanceof Integer && derechita instanceof Integer){
                    return (Integer)izquierdita < (Integer) derechita;
                }
                if(izquierdita instanceof Double && derechita instanceof Double){
                    return (Double)izquierdita < (Double) derechita;
                }
                if(izquierdita instanceof Double && derechita instanceof Integer){
                    return (Double)izquierdita < ((Number) derechita).doubleValue();
                }
                if(izquierdita instanceof Integer && derechita instanceof Double){
                    return ((Number) izquierdita).doubleValue() < (Double) derechita;
                }
                System.out.println("\tNo es posible comparar un " + izquierdita.getClass().getSimpleName() + " con " + derechita.getClass().getSimpleName());
                exit(0);
            case LESS_EQUAL:
                if(izquierdita instanceof String || derechita instanceof String) {
                    System.out.println("\tError: No es posible realizar: " +izquierdita +" " + operator.getTipo() + " " + derechita);
                }
                if(izquierdita instanceof Integer && derechita instanceof Integer){
                    return (Integer)izquierdita <= (Integer) derechita;
                }
                if(izquierdita instanceof Double && derechita instanceof Double){
                    return (Double)izquierdita <= (Double) derechita;
                }
                if(izquierdita instanceof Double && derechita instanceof Integer){
                    return (Double)izquierdita <= ((Number) derechita).doubleValue();
                }
                if(izquierdita instanceof Integer && derechita instanceof Double){
                    return ((Number) izquierdita).doubleValue() <= (Double) derechita;
                }
                System.out.println("\tNo es posible comparar un " + izquierdita.getClass().getSimpleName() + " con " + derechita.getClass().getSimpleName());
                exit(0);
            default:
                throw new RuntimeException("Operador desconocido: " + operator.getLexema());
        }
    }
}

