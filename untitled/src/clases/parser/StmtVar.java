package clases.parser;
import clases.paquetito.TablaSimbolos;
import clases.paquetito.Token;

import static java.lang.System.exit;


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

    @Override
    public Object resolver(TablaSimbolos tablita) {
        //System.out.println("++++++++++++++++++++++++++++++++++++++++++" + name.getLexema());
        Object value = initializer != null ? initializer.resolver(tablita) : null;
        if (tablita.existeIdentificador(name.getLexema())) {
            System.out.println("\terror: variable ya definida: " + name.getLexema() + ". ");
            exit(0);
            return null;
        }else{
            tablita.asignar(name.getLexema(), value);
            return value;

        }
    }
}
