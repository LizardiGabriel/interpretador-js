import java.util.List;

public class ASDR implements Parser {

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
