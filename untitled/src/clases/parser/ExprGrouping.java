package clases.parser;


import clases.paquetito.TablaSimbolos;


public class ExprGrouping extends Expression {
    final Expression expression;

    public ExprGrouping(Expression expression) {
        this.expression = expression;
        //System.out.println("ExprGrouping: " + expression);
    }
    @Override
    public String toString() {
        return "\n--> ExprGrouping: " + expression.toString();
    }

    @Override
    public Object resolver(TablaSimbolos tablita) {
        return expression.resolver(tablita);
    }
}
