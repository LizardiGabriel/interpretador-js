package clases.parser;
import clases.paquetito.Token;
import clases.paquetito.TablaSimbolos;


public class ExprAssign extends Expression{
    final Token operator;
    final Expression value;
    final Expression name;
/**
 *
 * @param value
 * @param operator
 * @param name
* */
    public ExprAssign(Expression name, Token operator, Expression value) {
        this.name = name;
        this.value = value;
        this.operator = operator;
        //System.out.println("ExprAssign: " + operator + " " + name + " " + value);
    }
    @Override
    public String toString() {
        return "\n--> ExprAssign: {\n\t"  + "name: {" + name.toString() +"}\n\toperator: _("+operator.getLexema()+ ")_\n\tvalue: [" + value.toString() + "]\t}<--fin exprAssign\n";
    }

    /**
     * Resuelve una expresión de asignación.
     *
     * @param tablita La tabla de símbolos donde se almacenan las variables.
     * @return El valor asignado a la variable.
     * @throws RuntimeException si la variable no está definida o si el nombre no es una instancia de ExprVariable.
     */
    @Override
    public Object resolver(TablaSimbolos tablita){
        if(name instanceof ExprVariable){
            ExprVariable var = (ExprVariable) name;
            if(tablita.existeIdentificador(var.getNombre())){
                Object valor = value.resolver(tablita);
                tablita.asignar(var.getNombre(), valor);
                return valor;
            }else{
                throw new RuntimeException("Variable no definida '" + var.getNombre() + "'.");
            }
        }else{
            throw new RuntimeException("Error en la asignacion de variables");
        }
    }


}
