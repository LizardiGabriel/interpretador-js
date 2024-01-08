package clases.parser;
import clases.paquetito.TablaSimbolos;

public class ExprLiteral extends Expression {
    final Object value;

    public ExprLiteral(Object value) {
        this.value = value;
        //System.out.println("ExprLiteral: " + value);
    }
    @Override
    public String toString() {
        return "\n--> ExprLiteral: ..(" + String.valueOf(value) + ")..";
    }

    @Override
    public Object resolver(TablaSimbolos tablita){
        return value;
    }
}
