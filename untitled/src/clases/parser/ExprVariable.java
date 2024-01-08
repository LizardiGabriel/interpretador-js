package clases.parser;
import clases.paquetito.Token;
import clases.paquetito.TablaSimbolos;


public class ExprVariable extends Expression {
    final Token name;

    public ExprVariable(Token name) {
        this.name = name;
        //System.out.println("ExprVariable: " + name);
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
            throw new RuntimeException("Variable no definida '" + name.getLexema() + "'.");
        }
    }

}