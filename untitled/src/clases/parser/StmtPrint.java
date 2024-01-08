package clases.parser;
import clases.paquetito.TablaSimbolos;

public class StmtPrint extends Statement {
    final Expression expression;

    public StmtPrint(Expression expression) {

        this.expression = expression;
        //System.out.println("StmtPrint: " + expression);
    }
    @Override
    public String toString() {
        return "\n--> StmtPrint: " + expression.toString();
    }

    @Override
    public Object resolver(TablaSimbolos tablita) {
        System.out.println(expression.resolver(tablita));
        return null;
    }


}
