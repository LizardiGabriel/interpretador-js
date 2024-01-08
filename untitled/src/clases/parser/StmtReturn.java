package clases.parser;


import clases.paquetito.TablaSimbolos;

public class StmtReturn extends Statement {
    final Expression value;

    public StmtReturn(Expression value) {
        this.value = value;
        //System.out.println("cons-StmtReturn: " + value);

    }
    @Override
    public String toString() {
        return "\n--> StmtReturn: " + String.valueOf(value);
    }

    @Override
    public Object resolver(TablaSimbolos tablita) {
        //System.out.println("0_0====> resolver stmtreturn: " + value.resolver(tablita).toString());
        return value.resolver(tablita);
    }
}
