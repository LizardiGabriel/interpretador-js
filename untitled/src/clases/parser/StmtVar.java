package clases.parser;
import clases.paquetito.Token;


public class StmtVar extends Statement {
    final Token name;
    final Expression initializer;

    public StmtVar(Token name, Expression initializer) {
        this.name = name;
        this.initializer = initializer;
        //System.out.println("StmtVar: " + name + " " + initializer);
    }

    @Override
    public String toString() {
        return "\n--> StmtVar: " + name.getLexema() + " = " + (initializer != null ? initializer.toString() : "null");
    }
}
