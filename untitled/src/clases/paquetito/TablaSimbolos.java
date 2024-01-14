package clases.paquetito;
import java.util.HashMap;
import java.util.Map;


public class TablaSimbolos {
    private final Map<String, Object> values;
    TablaSimbolos anterior ;

    public TablaSimbolos(){
        this.values = new HashMap<>();
        this.anterior = null;
    }

    public TablaSimbolos(TablaSimbolos anterior){
        this.values = new HashMap<>();
        this.anterior = anterior;
    }


    public boolean existeIdentificador(String identificador){

        if(anterior != null){
            if( anterior.existeIdentificador(identificador)){
                return true;

            }else{

            }
        }
        if (values.containsKey(identificador)) {
            return true;
        }
        return false;
    }

    public Object obtener(String identificador) {
        if(anterior != null){
            if( anterior.existeIdentificador(identificador)){
                return anterior.obtener(identificador);

            }else {
                return values.get(identificador);
            }
        }else{

            return values.get(identificador);
        }
    }


    public void asignar(String identificador, Object valor){
        if(anterior != null){
            if( anterior.existeIdentificador(identificador)){
                anterior.asignar(identificador, valor);

            }else{
                values.put(identificador, valor);
            }
        }else{
            values.put(identificador, valor);
        }
    }
}
