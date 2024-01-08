package clases.parser;


import clases.paquetito.TablaSimbolos;

public abstract class Expression {
    public abstract String toString();
    public abstract Object resolver(TablaSimbolos tablita);


}
