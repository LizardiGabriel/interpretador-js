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

    public ExprCallFunction(Expression callee, List<Expression> arguments) {
        this.callee = callee;
        this.arguments = arguments;
    }
    @Override
    public String toString() {
        return "\n--> ExprCallFunction: " + callee.toString() + "Arguments: " + arguments.toString();
    }

    @Override
    public Object resolver(TablaSimbolos tablita) {

        if(!(callee instanceof ExprVariable)){
            throw new RuntimeException("identificador invalido para llamada a funcion 1");
        }
        Object aux = callee.resolver(tablita);
        String funcioncita = null;
        if(aux instanceof StmtFunction){
            funcioncita = ((StmtFunction) aux).getName();
        }else{
            System.out.println("no es instancia ");
        }

        if(!tablita.existeIdentificador(funcioncita)){
            throw new RuntimeException("funcion no definida '" + funcioncita + "'.");
        }
        Object valor = tablita.obtener(funcioncita);
        if(!(valor instanceof StmtFunction)){
            throw new RuntimeException("identificador invalido para llamada a funcion 2");
        }


        List<Object> argumentitos = new ArrayList<>();
        for(Expression argument : arguments){
            argumentitos.add( argument.resolver(tablita));
        }
        int n = 0;
        for (Token tokencito : ((StmtFunction) valor).params){
            tablita.asignar(tokencito.getLexema(), argumentitos.get(n));
            n++;
        }
        Object res =  ((StmtFunction) valor).body.resolver(tablita);
        return String.valueOf(res);

    }

}
