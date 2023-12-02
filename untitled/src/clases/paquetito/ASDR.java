package paquetito;

import java.util.List;

public class ASDR implements Parser0 {

    private int i = 0;
    private boolean hayErrores = false;
    private Token preanalisis;
    private final List<Token> tokens;

    public ASDR(List<Token> tokens) {
        this.tokens = tokens;
        preanalisis = this.tokens.get(i);
    }

    @Override
    public boolean parse() {
        DECLARATION();
        return !hayErrores && preanalisis.tipo == TipoToken.EOF;
    }

    private void DECLARATION() {
        if (preanalisis.tipo == TipoToken.FUN) {
            FUN_DECL();
        } else if (preanalisis.tipo == TipoToken.VAR) {
            VAR_DECL();
        } else {
            STATEMENT();
        }
    }

    private void FUN_DECL() {
        match(TipoToken.FUN);
        FUNCTION();
    }

    private void VAR_DECL() {
        match(TipoToken.VAR);
        match(TipoToken.IDENTIFIER);
        VAR_INIT();
        match(TipoToken.SEMICOLON);
    }

    private void VAR_INIT() {
        if (preanalisis.tipo == TipoToken.EQUAL) {
            match(TipoToken.EQUAL);
            EXPRESSION();
        }
    }

    private void STATEMENT() {
        if (preanalisis.tipo == TipoToken.FOR) {
            FOR_STMT();
        } else if (preanalisis.tipo == TipoToken.IF) {
            IF_STMT();
        } else if (preanalisis.tipo == TipoToken.PRINT) {
            PRINT_STMT();
        } else if (preanalisis.tipo == TipoToken.RETURN) {
            RETURN_STMT();
        } else if (preanalisis.tipo == TipoToken.WHILE) {
            WHILE_STMT();
        } else if (preanalisis.tipo == TipoToken.LEFT_BRACE) {
            BLOCK();
        } else {
            EXPR_STMT();
        }
    }

    private void EXPR_STMT() {
        EXPRESSION();
        match(TipoToken.SEMICOLON);
    }

    private void FOR_STMT() {
        match(TipoToken.FOR);
        match(TipoToken.LEFT_PAREN);
        FOR_STMT_1();
        FOR_STMT_2();
        FOR_STMT_3();
        match(TipoToken.RIGHT_PAREN);
        STATEMENT();
    }

    private void FOR_STMT_1() {
        if (preanalisis.tipo == TipoToken.VAR) {
            VAR_DECL();
        } else if (preanalisis.tipo != TipoToken.SEMICOLON) {
            EXPR_STMT();
        }
        match(TipoToken.SEMICOLON);
    }

    private void FOR_STMT_2() {
        if (preanalisis.tipo != TipoToken.SEMICOLON) {
            EXPRESSION();
        }
        match(TipoToken.SEMICOLON);
    }

    private void FOR_STMT_3() {
        if (preanalisis.tipo != TipoToken.RIGHT_PAREN) {
            EXPRESSION();
        }
    }

    private void IF_STMT() {
        match(TipoToken.IF);
        match(TipoToken.LEFT_PAREN);
        EXPRESSION();
        match(TipoToken.RIGHT_PAREN);
        STATEMENT();
        ELSE_STATEMENT();
    }

    private void ELSE_STATEMENT() {
        if (preanalisis.tipo == TipoToken.ELSE) {
            match(TipoToken.ELSE);
            STATEMENT();
        }
    }

    private void PRINT_STMT() {
        match(TipoToken.PRINT);
        EXPRESSION();
        match(TipoToken.SEMICOLON);
    }

    private void RETURN_STMT() {
        match(TipoToken.RETURN);
        RETURN_EXP_OPC();
        match(TipoToken.SEMICOLON);
    }

    private void RETURN_EXP_OPC() {
        if (preanalisis.tipo != TipoToken.SEMICOLON) {
            EXPRESSION();
        }
    }

    private void WHILE_STMT() {
        match(TipoToken.WHILE);
        match(TipoToken.LEFT_PAREN);
        EXPRESSION();
        match(TipoToken.RIGHT_PAREN);
        STATEMENT();
    }

    private void BLOCK() {
        match(TipoToken.LEFT_BRACE);
        DECLARATION();
        match(TipoToken.RIGHT_BRACE);
    }

    private void EXPRESSION() {
        ASSIGNMENT();
    }

    private void ASSIGNMENT() {
        LOGIC_OR();
        ASSIGNMENT_OPC();
    }

    private void ASSIGNMENT_OPC() {
        if (preanalisis.tipo == TipoToken.EQUAL) {
            match(TipoToken.EQUAL);
            EXPRESSION();
        }
    }

    private void LOGIC_OR() {
        LOGIC_AND();
        LOGIC_OR_2();
    }

    private void LOGIC_OR_2() {
        if (preanalisis.tipo == TipoToken.OR) {
            match(TipoToken.OR);
            LOGIC_AND();
            LOGIC_OR_2();
        }
    }

    private void LOGIC_AND() {
        EQUALITY();
        LOGIC_AND_2();
    }

    private void LOGIC_AND_2() {
        if (preanalisis.tipo == TipoToken.AND) {
            match(TipoToken.AND);
            EQUALITY();
            LOGIC_AND_2();
        }
    }

    private void EQUALITY() {
        COMPARISON();
        EQUALITY_2();
    }

    private void EQUALITY_2() {
        if (preanalisis.tipo == TipoToken.BANG_EQUAL) {
            match(TipoToken.BANG_EQUAL);
            COMPARISON();
            EQUALITY_2();
        } else if (preanalisis.tipo == TipoToken.EQUAL_EQUAL) {
            match(TipoToken.EQUAL_EQUAL);
            COMPARISON();
            EQUALITY_2();
        }
    }

    private void COMPARISON() {
        TERM();
        COMPARISON_2();
    }

    private void COMPARISON_2() {
        if (preanalisis.tipo == TipoToken.GREATER) {
            match(TipoToken.GREATER);
            TERM();
            COMPARISON_2();
        } else if (preanalisis.tipo == TipoToken.GREATER_EQUAL) {
            match(TipoToken.GREATER_EQUAL);
            TERM();
            COMPARISON_2();
        } else if (preanalisis.tipo == TipoToken.LESS) {
            match(TipoToken.LESS);
            TERM();
            COMPARISON_2();
        } else if (preanalisis.tipo == TipoToken.LESS_EQUAL) {
            match(TipoToken.LESS_EQUAL);
            TERM();
            COMPARISON_2();
        }
    }

    private void TERM() {
        FACTOR();
        TERM_2();
    }

    private void TERM_2() {
        if (preanalisis.tipo == TipoToken.MINUS) {
            match(TipoToken.MINUS);
            FACTOR();
            TERM_2();
        } else if (preanalisis.tipo == TipoToken.PLUS) {
            match(TipoToken.PLUS);
            FACTOR();
            TERM_2();
        }
    }

    private void FACTOR() {
        UNARY();
        FACTOR_2();
    }

    private void FACTOR_2() {
        if (preanalisis.tipo == TipoToken.SLASH) {
            match(TipoToken.SLASH);
            UNARY();
            FACTOR_2();
        } else if (preanalisis.tipo == TipoToken.STAR) {
            match(TipoToken.STAR);
            UNARY();
            FACTOR_2();
        }
    }

    private void UNARY() {
        if (preanalisis.tipo == TipoToken.BANG) {
            match(TipoToken.BANG);
            UNARY();
        } else if (preanalisis.tipo == TipoToken.MINUS) {
            match(TipoToken.MINUS);
            UNARY();
        } else {
            CALL();
        }
    }

    private void CALL() {
        PRIMARY();
        CALL_2();
    }

    private void CALL_2() {
        if (preanalisis.tipo == TipoToken.LEFT_PAREN) {
            match(TipoToken.LEFT_PAREN);
            ARGUMENTS_OPC();
            match(TipoToken.RIGHT_PAREN);
            CALL_2();
        }
    }

    private void PRIMARY() {
        if (preanalisis.tipo == TipoToken.TRUE || preanalisis.tipo == TipoToken.FALSE ||
            preanalisis.tipo == TipoToken.NULL || preanalisis.tipo == TipoToken.NUMBER ||
            preanalisis.tipo == TipoToken.STRING || preanalisis.tipo == TipoToken.IDENTIFIER) {
            match(preanalisis.tipo);
        } else if (preanalisis.tipo == TipoToken.LEFT_PAREN) {
            match(TipoToken.LEFT_PAREN);
            EXPRESSION();
            match(TipoToken.RIGHT_PAREN);
        } else {
            hayErrores = true;
            System.out.println("Se esperaba un literal, identificador o expresion entre parentesis");
        }
    }

    private void FUNCTION() {
        match(TipoToken.IDENTIFIER);
        match(TipoToken.LEFT_PAREN);
        PARAMETERS_OPC();
        match(TipoToken.RIGHT_PAREN);
        BLOCK();
    }

    private void FUNCTIONS() {
        if (preanalisis.tipo == TipoToken.FUN) {
            FUN_DECL();
            FUNCTIONS();
        }
    }

    private void PARAMETERS_OPC() {
        if (preanalisis.tipo != TipoToken.RIGHT_PAREN) {
            PARAMETERS();
        }
    }

    private void PARAMETERS() {
        match(TipoToken.IDENTIFIER);
        PARAMETERS_2();
    }

    private void PARAMETERS_2() {
        if (preanalisis.tipo == TipoToken.COMMA) {
            match(TipoToken.COMMA);
            match(TipoToken.IDENTIFIER);
            PARAMETERS_2();
        }
    }

    private void ARGUMENTS_OPC() {
        if (preanalisis.tipo != TipoToken.RIGHT_PAREN) {
            EXPRESSION();
            ARGUMENTS();
        }
    }

    private void ARGUMENTS() {
        if (preanalisis.tipo == TipoToken.COMMA) {
            match(TipoToken.COMMA);
            EXPRESSION();
            ARGUMENTS();
        }
    }

    private void match(TipoToken tt) {
        if (preanalisis.tipo == tt) {
            i++;
            preanalisis = tokens.get(i);
        } else {
            hayErrores = true;
            System.out.println("Error encontrado");
        }
    }
}
