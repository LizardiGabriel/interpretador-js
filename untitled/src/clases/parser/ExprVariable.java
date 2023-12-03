package clases.parser;
import clases.paquetito.Token;

public class ExprVariable extends Expression {
    final Token name;

    public ExprVariable(Token name) {
        this.name = name;
        //System.out.println("ExprVariable: " + name);
    }
}