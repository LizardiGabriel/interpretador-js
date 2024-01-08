package clases.paquetito;
import java.util.HashMap;
import java.util.Map;

/*
* TablaSimbolos utiliza un HashMap para almacenar los valores de las variables.
* El HashMap almacena pares clave-valor.
* La clave es el identificador de la variable
* El valor es el valor de la variable.
* */
public class TablaSimbolos {
    private final Map<String, Object> values = new HashMap<>();
    /**
     * Verifica si existe un identificador en la tabla de simbolos
     * @param identificador
     * @return true si existe, false si no existe
     */
    public boolean existeIdentificador(String identificador){
        return values.containsKey(identificador);
    }
    /**
     * Obtiene el valor de un identificador
     * @param identificador
     * @return si existe el valor de la variable, si no existe lanza una excepcion
     */
    public Object obtener(String identificador) {
        if (values.containsKey(identificador)) {
            return values.get(identificador);
        }
        throw new RuntimeException("Variable no definida '" + identificador + "'.");
    }

    /**
     * Asigna un valor a un identificador
     * @param identificador
     * @param valor
     * @return void
     */
    public void asignar(String identificador, Object valor){
        values.put(identificador, valor);
    }
}
