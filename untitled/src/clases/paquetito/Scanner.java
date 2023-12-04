package clases.paquetito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scanner {

    private void addToken(Token token) {

        tokens.add(token);
    }

    private static final Map<String, TipoToken> palabrasReservadas;

    static {
        palabrasReservadas = new HashMap<>();
        palabrasReservadas.put("and",    TipoToken.AND);
        palabrasReservadas.put("else",   TipoToken.ELSE);
        palabrasReservadas.put("false",  TipoToken.FALSE);
        palabrasReservadas.put("for",    TipoToken.FOR);
        palabrasReservadas.put("fun",    TipoToken.FUN);
        palabrasReservadas.put("if",     TipoToken.IF);
        palabrasReservadas.put("null",   TipoToken.NULL);
        palabrasReservadas.put("or",     TipoToken.OR);
        palabrasReservadas.put("print",  TipoToken.PRINT);
        palabrasReservadas.put("return", TipoToken.RETURN);
        palabrasReservadas.put("true",   TipoToken.TRUE);
        palabrasReservadas.put("var",    TipoToken.VAR);
        palabrasReservadas.put("while",  TipoToken.WHILE);
    }

    private final String source;

    private final List<Token> tokens = new ArrayList<>();
    
    public Scanner(String source){
        this.source = source + " ";
    }

    public List<Token> scan() throws Exception {
        int estado = 0;
        int cont_line = 1;
        int cont_column = 1;
        String lexema = "";
        String lexema2 = "";
        char c;

        for(int i=0; i<source.length(); i++){
            c = source.charAt(i);
            if (c == '\n') {
                cont_line++;
                cont_column = 1; // Reiniciamos la columna al cambiar de línea
            } else {
                cont_column++;
            }

            switch (estado) {
                case 0:
                    if (c == '>') {
                        estado = 1;
                        lexema += c;
                    } else if (c == '<') {
                        estado = 4;
                        lexema += c;
                    } else if (c == '=') {
                        estado = 7;
                        lexema += c;
                    } else if (c == '!') {
                        estado = 10;
                        lexema += c;
                    } else if (c == '"') {
                        estado = 24;
                        lexema += c;
                    } else if (c == '/') {
                        estado = 26;
                        lexema += c;
                    } else if (c == '(') {
                        lexema += c;
                        addToken(new Token(TipoToken.LEFT_PAREN, lexema, cont_line, cont_column));
                        lexema = "";
                    } else if (c == ')') {
                        lexema += c;
                        addToken(new Token(TipoToken.RIGHT_PAREN, lexema, cont_line, cont_column));
                        lexema = "";
                    } else if (c == '{') {
                        lexema += c;
                        addToken(new Token(TipoToken.LEFT_BRACE, lexema, cont_line, cont_column));
                        lexema = "";
                    } else if (c == '}') {
                        lexema += c;
                        addToken(new Token(TipoToken.RIGHT_BRACE, lexema, cont_line, cont_column));
                        lexema = "";
                    } else if (c == ',') {
                        lexema += c;
                        addToken(new Token(TipoToken.COMMA, lexema, cont_line, cont_column));
                        lexema = "";
                    } else if (c == '.') {
                        lexema += c;
                        addToken(new Token(TipoToken.DOT, lexema, cont_line, cont_column));
                        lexema = "";
                    } else if (c == '-') {
                        lexema += c;
                        addToken(new Token(TipoToken.MINUS, lexema, cont_line, cont_column));
                        lexema = "";
                    } else if (c == '+') {
                        lexema += c;
                        addToken(new Token(TipoToken.PLUS, lexema, cont_line, cont_column));
                        lexema = "";
                    } else if (Character.compare(c, '\u003B') == 0) {
                        lexema += c;
                        addToken(new Token(TipoToken.SEMICOLON, lexema, cont_line, cont_column));
                        lexema = "";
                    } else if (c == '*') {
                        lexema += c;
                        addToken(new Token(TipoToken.STAR, lexema, cont_line, cont_column));
                        lexema = "";
                    } else if (i == source.length() - 1) {
                        lexema += c;
                        addToken(new Token(TipoToken.EOF, lexema, cont_line, cont_column));
                        lexema = "";
                    } else if (Character.isLetter(c)) {
                        estado = 13;
                        lexema += c;
                    } else if (Character.isDigit(c)) {
                        estado = 15;
                        lexema += c;
                    }
                    break;
                case 1:
                    if (c == '=') {
                        lexema += c;
                        addToken(new Token(TipoToken.GREATER_EQUAL, lexema, cont_line, cont_column));
                    } else {
                        addToken(new Token(TipoToken.GREATER, lexema, cont_line, cont_column));
                        i--;
                    }
                    estado = 0;
                    lexema = "";
                    break;
                case 4:
                    if (c == '=') {
                        lexema += c;
                        addToken(new Token(TipoToken.LESS_EQUAL, lexema, cont_line, cont_column));
                    } else {
                        addToken(new Token(TipoToken.LESS, lexema, cont_line, cont_column));
                        i--;
                    }
                    estado = 0;
                    lexema = "";
                    break;
                case 7:
                    if (c == '=') {
                        lexema += c;
                        addToken(new Token(TipoToken.EQUAL_EQUAL, lexema, cont_line, cont_column));
                    } else {
                        addToken(new Token(TipoToken.EQUAL, lexema, cont_line, cont_column));
                        i--;
                    }
                    estado = 0;
                    lexema = "";
                    break;
                case 10:
                    if (c == '=') {
                        lexema += c;
                        addToken(new Token(TipoToken.BANG_EQUAL, lexema, cont_line, cont_column));
                    } else {
                        addToken(new Token(TipoToken.BANG, lexema, cont_line, cont_column));
                        i--;
                    }
                    estado = 0;
                    lexema = "";
                    break;
                case 13:
                    if (Character.isLetterOrDigit(c)) {
                        estado = 13;
                        lexema += c;
                    } else {
                        TipoToken tt = palabrasReservadas.get(lexema);

                        if (tt == null) {
                            addToken(new Token(TipoToken.IDENTIFIER, lexema, cont_line, cont_column));
                        } else {
                            addToken(new Token(tt, lexema, cont_line, cont_column));
                        }

                        estado = 0;
                        lexema = "";
                        i--;

                    }
                    break;
                case 15:
                    if (Character.isDigit(c)) {
                        estado = 15;
                        lexema += c;
                    } else if (c == '.') {
                        estado = 16;
                        lexema += c;
                    } else if (c == 'E') {
                        estado = 18;
                        lexema += c;
                    } else {
                        addToken(new Token(TipoToken.NUMBER, lexema, Integer.valueOf(lexema), cont_line, cont_column));
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
                case 16:
                    if (Character.isDigit(c)) {
                        estado = 17;
                        lexema += c;
                    } else {
                        Interprete.error(cont_line, "Faltan argumentos al numero decimal.");
                    }
                    break;
                case 17:
                    if (Character.isDigit(c)) {
                        estado = 17;
                        lexema += c;
                    } else if (c == 'E') {
                        estado = 18;
                        lexema += c;
                    } else {
                        addToken(new Token(TipoToken.NUMBER, lexema, Float.valueOf(lexema), cont_line, cont_column));
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
                case 18:
                    if (c == '+' || c == '-') {
                        estado = 19;
                        lexema += c;
                    } else if (Character.isDigit(c)) {
                        estado = 20;
                        lexema += c;
                    } else {
                        Interprete.error(cont_line, "Faltan argumentos en el exponente.");
                    }
                    break;
                case 19:
                    if (Character.isDigit(c)) {
                        estado = 20;
                        lexema += c;
                    } else {
                        Interprete.error(cont_line, "Faltan argumentos en el exponente.");
                    }
                    break;
                case 20:
                    if (Character.isDigit(c)) {
                        estado = 20;
                        lexema += c;
                    } else {
                        addToken(new Token(TipoToken.NUMBER, lexema, Float.valueOf(lexema), cont_line, cont_column));
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
                case 24:
                    if (c == '"') {
                        lexema += c;
                        addToken(new Token(TipoToken.STRING, lexema, lexema2, cont_line, cont_column));
                        estado = 0;
                        lexema = "";
                        lexema2 = "";
                    } else if (c != '\n') {
                        if (i == source.length() - 1)
                            Interprete.error(cont_line, "String no cerrado");
                        else {
                            lexema += c;
                            lexema2 += c;
                            estado = 24;
                        }
                    } else {
                        Interprete.error(cont_line, "un string no puede contener saltos de linea.");
                    }
                    break;
                case 26:
                    if (c == '/') {
                        lexema += c;
                        estado = 30;
                    } else if (c == '*') {
                        lexema += c;
                        estado = 27;
                    } else {
                        Token t = new Token(TipoToken.SLASH, lexema, cont_line, cont_column);
                        addToken(t);
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
                case 27:
                    if (c == '*') {
                        lexema += c;
                        estado = 28;
                    } else if (c == '\n') {
                        lexema += c;
                        estado = 27;
                    } else if (i == source.length() - 1) {
                        Interprete.error(cont_line, "Comentario multilinea no cerrado.");
                    } else {
                        lexema += c;
                        estado = 27;
                    }
                    break;
                case 28:
                    if (c == '/') {
                        lexema += c;
                        estado = 29;
                    } else if (c == '\n') {
                        lexema += c;
                        estado = 27;
                    } else if (i == source.length() - 1) {
                        Interprete.error(cont_line, "Comentario multilinea no cerrado.");
                    } else {
                        lexema += c;
                        estado = 27;
                    }
                    break;
                case 29:
                    estado = 0;
                    lexema = "";
                    i--;
                    break;
                case 30:
                    if (c != '\n' && i != source.length() - 1) {
                        lexema += c;
                    } else {
                        estado = 0;
                        lexema = "";
                    }
                    break;
            }


        }


        return tokens;
    }



}


