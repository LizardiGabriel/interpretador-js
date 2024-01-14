package clases.parser;
import clases.paquetito.Token;
import clases.paquetito.TablaSimbolos;

import static java.lang.System.exit;


public class ExprVariable extends Expression {
    final Token name;

    public ExprVariable(Token name) {
        this.name = name;
        //System.out.println("ExprVariable: " + name);
    }
    public String getNombre(){
        return name.getLexema();
    }
    @Override
    public String toString() {
        return "\n--> ExprVariable: --(" + name.getLexema()+")--";
    }
    @Override
    public Object resolver(TablaSimbolos tablita){
        if(tablita.existeIdentificador(name.getLexema())){
            return tablita.obtener(name.getLexema());
        }else{
            System.out.println("\terror: variable no definida papoi: " + name.getLexema() + ". ");
            System.out.println("fin de la ejecucion");
            exit(0);
            return null;
        }
    }

}