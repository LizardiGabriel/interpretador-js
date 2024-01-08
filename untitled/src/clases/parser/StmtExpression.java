package clases.parser;


import clases.paquetito.TablaSimbolos;

public class StmtExpression extends Statement {
    final Expression expression;

    public StmtExpression(Expression expression) {

        this.expression = expression;
        //System.out.println("StmtExpression: " + expression);
    }
    @Override
    public String toString() {
        return "\n--> StmtExpression: " + expression.toString();
    }

    @Override
    public Object resolver(TablaSimbolos tablita) {
        return expression.resolver(tablita);
    }


}
