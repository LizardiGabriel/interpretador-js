package clases.parser;
import clases.paquetito.TablaSimbolos;
import clases.paquetito.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ExprCallFunction extends Expression{
    final Expression callee;
    final List<Expression> arguments;
    // final Token paren;

    public ExprCallFunction(Expression callee, /*Token paren,*/ List<Expression> arguments) {
        this.callee = callee;
        this.arguments = arguments;
        // this.paren = paren;
        //System.out.println("ExprCallFunction: " + callee + "Arguments: " + arguments);
    }
    @Override
    public String toString() {
        return "\n--> ExprCallFunction: " + callee.toString() + "Arguments: " + arguments.toString();
    }

    /**
     * Resuelve una llamada a función.
     *
     * @param tablita
     * @return El valor de retorno de la función.
     * @throws RuntimeException si la función no está definida o si el nombre no es una instancia de ExprVariable.
    * */
    @Override
    public Object resolver(TablaSimbolos tablita) {
        if(!(callee instanceof ExprVariable)){
            throw new RuntimeException("identificador invalido para llamada a funcion");
        }
        String funcioncita = callee.resolver(tablita).toString();
        if(!tablita.existeIdentificador(funcioncita)){
            throw new RuntimeException("funcion no definida '" + funcioncita + "'.");
        }
        Object valor = tablita.obtener(funcioncita);
        if(!(valor instanceof Function)){
            throw new RuntimeException("identificador invalido para llamada a funcion");
        }
        List<Object> argumentitos = new ArrayList<>();
        for(Expression argument : arguments){
            argumentitos.add(argument.resolver(tablita));
        }
        return ((Function) valor).apply(argumentitos);

    }
}
