package clases.paquetito;

public class Token {

    final TipoToken tipo;
    final String lexema;
    final Object literal;

    final int  contLine;
    final int contColumn;


    public Token(TipoToken tipoToken, String lexema, int contLine, int contColumn) {
        this.tipo = tipoToken;
        this.lexema = lexema;
        this.literal = null;
        this.contColumn = contColumn;
        this.contLine = contLine;
    }
    public Token(TipoToken tipoToken, String lexema, Object literal, int contLine, int contColumn) {
        this.tipo = tipoToken;
        this.lexema = lexema;
        this.literal = literal;
        this.contColumn = contColumn;
        this.contLine = contLine;
    }


    public String toString() {
        return "<" + tipo + " " + lexema + " " + literal + ">";
    }

    public TipoToken getTipo() {
        return tipo;
    }

    public Object getLiteral() {
        return literal;
    }

    public int getContLine() {
        return contLine;
    }

    public int getContColumn() {
        return contColumn;
    }


    
}
