package paquetito;

import parser.*;

import java.lang.annotation.Documented;
import java.util.ArrayList;
import java.util.List;
import paquetito.*;

public class Parser {

    private int i = 0;
    private Token preanalisis;
    private final List<Token> tokens;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        preanalisis = this.tokens.get(i);
    }

    private List<Statement> programa() {
        List<Statement> declarations = new ArrayList<>();
        while (preanalisis.getTipo() != TipoToken.EOF) {
            declarations.add(declaration());
        }
        return declarations;
    }

    private Statement declaration() {
        switch (preanalisis.getTipo()) {
            case FUN:
                return funDeclaration();
            case VAR:
                return varDeclaration();
            default:
                return statement();
        }
    }

    /**
     * Analiza 
     * 
     * @return Nueva declaración de función.
     */

    private Statement funDeclaration() {
        match(TipoToken.FUN);
        Statement res = function();
        return res;
    }


    /**
     * Analiza una declaración de variable en el código.
     * Verifica 'VAR', recoge el nombre (identificador),
     * '=' y el valor inicial, si existe. Verifica ';'.
     * 
     * @return Nueva declaración de variable.
     */

    private Statement varDeclaration() {
        match(TipoToken.VAR);
        Token variableName = match(TipoToken.IDENTIFIER);
        Expression initializer = varInit();

        match(TipoToken.SEMICOLON);

        return new StmtVar(variableName, initializer);
    }

    /**
     * Analiza una inicialización de variable en el código fuente.
     * Verifica '=', recoge la expresión inicializadora y la devuelve.
     * 
     * @return Expresión inicializadora o null.
     */
    private Expression varInit() {

        if (preanalisis.tipo == TipoToken.EQUAL) {
            match(TipoToken.EQUAL);
            return expression();
        } else {
            return null;
        }
    }

    private Statement statement() {
        switch (preanalisis.getTipo()) {
            case FOR:
                return forStatement();
            case IF:
                return ifStatement();
            case PRINT:
                return printStatement();
            case RETURN:
                return returnStatement();
            case WHILE:
                return whileStatement();
            case LEFT_BRACE:
                return block();
            default:
                return expressionStatement();
        }
    }

    /**
     * Analiza una declaración de expresión en el código fuente.
     * Recoge la expresión, verifica ';' y devuelve una nueva declaración de
     * expresión.
     * 
     * @return Declaración de expresión.
     */

    private Statement expressionStatement() {
        Expression expr = expression();
        match(TipoToken.SEMICOLON);
        return new StmtExpression(expr);
    }

    /**
     * Analiza una declaración de bucle 'for' en el código fuente.
     * Verifica 'FOR', '(', FOR_STMT_1, FOR_STMT_2, ';', FOR_STMT_3, ')'
     * y el cuerpo.
     * 
     * @return Nueva declaración de bucle.
     */
    private Statement forStatement() {
        match(TipoToken.FOR);
        match(TipoToken.LEFT_PAREN);

        Statement forStmt1 = parseForStmt1();
        Expression forStmt2 = forStmt2();
        match(TipoToken.SEMICOLON);
        Expression forStmt3 = forStmt3();

        match(TipoToken.RIGHT_PAREN);

        Statement body = statement();
        return new StmtLoop(new ExprBinary(forStmt1, forStmt2, forStmt3), body);
    }

    private Statement parseForStmt1() {
        if (preanalisis.getTipo() == TipoToken.VAR) {
            return varDeclaration();
        } else {
            return expressionStatement();
        }
    }

    private Expression forStmt2() {
        return expression();
    }

    private Expression forStmt3() {
        if (preanalisis.getTipo() != TipoToken.RIGHT_PAREN) {
            return expression();
        }
        return null;
    }

    /**
     * Analiza una declaración de condicional 'if' en el código fuente.
     * Verifica 'IF', '(', EXPRESSION, ')', STATEMENT, ELSE_STATEMENT.
     * 
     * @return Nueva declaración de condicional.
     */
    private Statement ifStatement() {
        match(TipoToken.IF);
        match(TipoToken.LEFT_PAREN);
        Expression condition = expression();
        match(TipoToken.RIGHT_PAREN);
        Statement thenBranch = statement();
        Statement elseBranch = elseStatement();
        return new StmtIf(condition, thenBranch, elseBranch);
    }

    /**
     * Analiza una declaración de condicional 'else' en el código fuente.
     * Verifica 'ELSE', STATEMENT.
     * 
     * @return Nueva declaración de condicional.
     */
    private Statement elseStatement() {
        if (preanalisis.getTipo() == TipoToken.ELSE) {
            match(TipoToken.ELSE);
            return statement();
        } else {
            return null;
        }
    }

    /**
     * Analiza una declaración de impresión en el código fuente.
     * Verifica 'PRINT', recoge la expresión, verifica ';'
     * 
     * @return Nueva declaración de impresión.
     */
    private Statement printStatement() {
        match(TipoToken.PRINT);
        Expression expression = expression();
        match(TipoToken.SEMICOLON);
        return new StmtPrint(expression);
    }

    /**
     * Analiza una declaración de retorno en el código fuente.
     * Verifica 'RETURN', recoge la expresión, verifica ';'
     * 
     * @return Nueva declaración de retorno.
     */
    private Statement returnStatement() {
        match(TipoToken.RETURN);
        Expression value = returnExpOptional();
        match(TipoToken.SEMICOLON);
        return new StmtReturn(value);
    }

    /**
     * Analiza una expresión de retorno en el código fuente.
     * Verifica ';', recoge la expresión y la devuelve.
     * 
     * @return Expresión de retorno o null.
     */
    private Expression returnExpOptional() {
        if (preanalisis.getTipo() != TipoToken.SEMICOLON) {
            return expression();
        }
        return null;
    }

    /**
     * Analiza una declaración de bucle 'while' en el código fuente.
     * Verifica 'WHILE', '(', EXPRESSION, ')', STATEMENT.
     * 
     * @return Nueva declaración de bucle.
     */
    private Statement whileStatement() {
        match(TipoToken.WHILE);
        match(TipoToken.LEFT_PAREN);
        Expression condition = expression();
        match(TipoToken.RIGHT_PAREN);
        Statement body = statement();
        return new StmtLoop(condition, body);
    }

    /**
     * Analiza un bloque de código en el código fuente.
     * Verifica '{', STATEMENT, '}'.
     * 
     * @return Nueva declaración de bloque.
     */
    private StmtBlock block() {
        match(TipoToken.LEFT_BRACE);
        List<Statement> declarations = new ArrayList<>();
        while (preanalisis.getTipo() != TipoToken.RIGHT_BRACE && preanalisis.getTipo() != TipoToken.EOF) {
            declarations.add(declaration());
        }
        match(TipoToken.RIGHT_BRACE);
        return new StmtBlock(declarations);
    }

    /**
     * Analiza una expresión en el código fuente.
     * 
     * @return Nueva expresión.
     */
    private Expression expression() {
        return assignment();
    }

    /**
     * Analiza una asignación en el código fuente.
     * 
     * @return Una expresión de asignación o una expresión lógica. (Expression,
     *         Token, Expression)
     */
    private Expression assignment() {
        // El valor a asignar
        Expression left = logicOr();
        // La expresión a la que se está asignando
        Expression right = assignmentOptional();
        if (right != null) {
            // El operador de asignación
            Token operator = previous();
            return new ExprAssign(left, operator, right);
        }
        return left;
    }

    
}
