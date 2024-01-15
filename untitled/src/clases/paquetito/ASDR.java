package clases.paquetito;

import clases.parser.*;

import java.lang.annotation.Documented;
import java.util.ArrayList;
import java.util.List;

public class ASDR implements Parser {

    private boolean hayErrores = false;
    private int i = 0;
    private Token preanalisis;
    private final List<Token> tokens;

    public ASDR(List<Token> tokens) {
        this.tokens = tokens;
        preanalisis = this.tokens.get(i);
    }
    TablaSimbolos tablita = new TablaSimbolos();

    /**
     *
     * @return boolean
     */
    @Override
    public boolean parse() {
        List<Statement> declaraciones = programa();
        //programa();

        if (preanalisis.tipo == TipoToken.EOF && !hayErrores) {
            //System.out.println("sintaxis correcta");

            //resolver cada una
            System.out.println("\n\n\n//////////////////////////////////////////////////////////////");
            System.out.println("------>Ejecucion<------");

            for (Statement declaracion : declaraciones) {
                declaracion.resolver(tablita);
            }
            System.out.println("<------Fin de ejecucucion------>");
            System.out.println("//////////////////////////////////////////////////////////////");


            return true;
        } else {
            System.out.println("Se encontraron errores");
        }

        return false;
    }
    
    private List<Statement> programa() {

        List<Statement> declarations = new ArrayList<>();
        while (preanalisis.getTipo() != TipoToken.EOF) {
            Statement stmt = declaration();
            //System.out.println(stmt.toString());
            declarations.add(stmt);
        }
        return declarations;
    }

    // checar epsilon
    private Statement declaration() {
        Statement devolver;
        switch (preanalisis.getTipo()) {
            case FUN:
                devolver = funDeclaration();
                return devolver;
            case VAR:
                devolver = varDeclaration();
                return devolver;
            default:
                devolver = statement();
                return devolver;
        }
    }

    /**
     * Analiza una declaración de función
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

        //tablita.asignar(variableName.getLexema(), initializer);

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
                return forStmtBlock();
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
     * Verifica 'FOR', '(', FOR_STMT_1, FOR_EXPR_2, ';', FOR_STMT_3, ')'
     * y el cuerpo.
     *
     * @return UN OBJETO StmtBlock
     */
    private StmtBlock forStmtBlock() {

        // stmtBlock -> (init, stmtloop), stmtloop -> (condition, stmtBlock), stmtBlock -> (body, increment)

        match(TipoToken.FOR);
        match(TipoToken.LEFT_PAREN);

        Statement forStmt1 = parseForStmt1();
        Expression forExpr2 = forExpr2();
        match(TipoToken.SEMICOLON);
        Statement forStmt3 = forStmt3();

        match(TipoToken.RIGHT_PAREN);

        Statement body = statement();


        List <Statement> lstStmt = new ArrayList<>();

        if (forStmt3 != null) {
            lstStmt.add(forStmt3);
            lstStmt.add(body);

        } else {
            lstStmt.add(body);
        }

        StmtBlock bodyIncrement = new StmtBlock(lstStmt);
        StmtLoop stmtLoop = new StmtLoop(forExpr2, bodyIncrement);

        List <Statement> lstStmt2 = new ArrayList<>();
        lstStmt2.add(forStmt1);
        lstStmt2.add(stmtLoop);

        StmtBlock stmtBlock = new StmtBlock(lstStmt2);

        return stmtBlock;

    }

    private Statement parseForStmt1() {
        if (preanalisis.getTipo() == TipoToken.VAR) {
            return varDeclaration();
        } else {
            return expressionStatement();
        }
    }

    private Expression forExpr2() {
        return expression();
    }

    private Statement forStmt3() {
        if (preanalisis.getTipo() != TipoToken.RIGHT_PAREN) {
            Expression expr = expression();
            return new StmtExpression(expr);
        } else {
            return null;
        }

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
        Expression name = logicOr();
        if (preanalisis.tipo == TipoToken.EQUAL) {
            Token operator = preanalisis;
            advance();
            Expression value = expression();
            return new ExprAssign(name, operator, value);
        }
        return name;
    }


    /**
     * Analiza una expresión lógica en el código fuente.
     *
     * @return Una expresión lógica (Expression,
     *         Token, Expression) o o una expresión de igualdad. (Expression)
     */
    private Expression logicOr() {
        //System.out.println("estoy en logicOr");
        Expression result = logicAnd();
        Expression or2Result = logicOr2(result);
        return or2Result;
    }

    // checar la recursion ?
    private Expression logicOr2(Expression expr) {
        if (preanalisis.getTipo() == TipoToken.OR) {
            match(TipoToken.OR);
            Token operator = previous();

            Expression logAnd = logicAnd();

            ExprLogical exprLog = new ExprLogical(expr, operator, logAnd);

            return logicOr2(exprLog);

        }else {
            return expr;
        }

    }

    // checar la recursion ?
    private Expression logicAnd() {
        //System.out.println("estoy en logicAnd");
        Expression result = equality();
        Expression and2Result = logicAnd2(result);
        return and2Result;
    }

    private Expression logicAnd2(Expression expr) {
        if (preanalisis.getTipo() == TipoToken.AND) {
            match(TipoToken.AND);
            Token operator = previous();

            Expression equality = equality();
            ExprLogical exprLog = new ExprLogical(expr, operator, equality);

            return logicAnd2(exprLog);
        }else {
            return expr;
        }


    }


    // checar la recursion ?
    private Expression equality() {
        //System.out.println("estoy en equality");
        Expression result = comparison();
        Expression res = equality2(result);
        return res;
    }

    private Expression equality2(Expression left) {
        switch (preanalisis.getTipo()) {
            case BANG_EQUAL:
                match(TipoToken.BANG_EQUAL);
                Token operator = previous();
                Expression right = comparison();
                ExprBinary expb = new ExprBinary(left, operator, right);
                return equality2(expb);
            case EQUAL_EQUAL:
                match(TipoToken.EQUAL_EQUAL);
                operator = previous();
                right = comparison();
                expb = new ExprBinary(left, operator, right);
                return equality2(expb);

            default:
                return left;

        }


    }

    // checar la recursion ?

    private Expression comparison() {
        //System.out.println("estoy en comparison");
        Expression result = term();
        Expression res = comparision2(result);
        return res;
    }

    private Expression comparision2(Expression left) {
        ExprLogical expb;
        switch (preanalisis.getTipo()) {
            case GREATER:
                match(TipoToken.GREATER);
                Token operator = previous();
                Expression right = term();
                expb = new ExprLogical(left, operator, right);
                return comparision2(expb);
            case GREATER_EQUAL:
                match(TipoToken.GREATER_EQUAL);
                operator = previous();
                right = term();
                expb = new ExprLogical(left, operator, right);
                return comparision2(expb);
            case LESS:
                match(TipoToken.LESS);
                operator = previous();
                right = term();
                expb = new ExprLogical(left, operator, right);
                return comparision2(expb);
            case LESS_EQUAL:
                match(TipoToken.LESS_EQUAL);
                operator = previous();
                right = term();
                expb = new ExprLogical(left, operator, right);
                return comparision2(expb);
            default:
                return left;
        }

    }

    /// checar recursividad ?

    private Expression term() {
        //System.out.println("estoy en term");
        Expression result = factor();
        Expression res = term2(result);
        //System.out.println("retorno res: " + String.valueOf(res));
        return res;
    }

    private Expression term2(Expression left) {
        //System.out.println("estoy en term2, left es: " + String.valueOf(left));
        switch (preanalisis.getTipo()) {
            case MINUS:
                //System.out.println("estoy en menos");
                match(TipoToken.MINUS);

                Token operator = previous();
                Expression right = factor();
                ExprBinary expb = new ExprBinary(left, operator, right);
                return term2(expb);
            case PLUS:
                //System.out.println("estoy en mas");
                match(TipoToken.PLUS);
                operator = previous();
                right = factor();

                ExprBinary expb2 = new ExprBinary(left, operator, right);

                //System.out.println("lleagando a term2 en mas: " + String.valueOf(expb2));
                return term2(expb2);
            default:
                //System.out.println("estoy en default");
                return left;
        }


    }

    /// checar recursividad ?

    private Expression factor() {
        Expression expr = unary();
        expr = factor2(expr);
        return expr;
    }

    private Expression factor2(Expression expr) {
        switch (preanalisis.getTipo()) {
            case SLASH:
                match(TipoToken.SLASH);
                Token operador = previous();
                Expression expr2 = unary();
                ExprBinary expb = new ExprBinary(expr, operador, expr2);
                return factor2(expb);
            case STAR:
                match(TipoToken.STAR);
                operador = previous();
                expr2 = unary();
                expb = new ExprBinary(expr, operador, expr2);
                return factor2(expb);
            default:
                return expr;
        }

    }

    private Expression unary() {
        switch (preanalisis.getTipo()) {
            case BANG:
                match(TipoToken.BANG);
                Token operador = previous();
                Expression expr = unary();
                return new ExprUnary(operador, expr);
            case MINUS:
                match(TipoToken.MINUS);
                operador = previous();
                expr = unary();
                return new ExprUnary(operador, expr);
            default:
                return call();
        }
    }

    private Expression call() {
        Expression expr = primary();
        Expression call2Result = call2(expr);
        return call2Result;
    }

    private Expression call2(Expression expr) {
        switch (preanalisis.getTipo()) {
            case LEFT_PAREN:
                match(TipoToken.LEFT_PAREN);
                List<Expression> lstArguments = argumentsOptional();
                match(TipoToken.RIGHT_PAREN);
                ExprCallFunction ecf = new ExprCallFunction(expr, lstArguments);
                return call2(ecf);

            default:
                return expr;
        }

    }

    // en expressionLiteral se necesita un valor

    private Expression primary() {
        switch (preanalisis.getTipo()) {
            case TRUE:
                match(TipoToken.TRUE);
                return new ExprLiteral(true);
            case FALSE:
                match(TipoToken.FALSE);
                return new ExprLiteral(false);
            case NULL:
                match(TipoToken.NULL);
                return new ExprLiteral(null);
            case NUMBER:
                match(TipoToken.NUMBER);
                Token numero = previous();
                return new ExprLiteral(numero.getLiteral());
            case STRING:
                match(TipoToken.STRING);
                Token cadena = previous();
                return new ExprLiteral(cadena.getLiteral());
            case IDENTIFIER:
                match(TipoToken.IDENTIFIER);
                Token id = previous();
                return new ExprVariable(id);
            case LEFT_PAREN:
                match(TipoToken.LEFT_PAREN);
                Expression expr = expression();
                match(TipoToken.RIGHT_PAREN);
                return new ExprGrouping(expr);
        }
        return null;
    }

    private Statement function() {
        Token name = match(TipoToken.IDENTIFIER);
        match(TipoToken.LEFT_PAREN);
        List<Token> parameters = parametersOptional();
        match(TipoToken.RIGHT_PAREN);
        StmtBlock body = block();

        StmtFunction retornar = new StmtFunction(name, parameters, body);
        //System.out.println(retornar.toString());
        return retornar;
    }
    

    private List<Token> parametersOptional() {
        if (preanalisis.getTipo() != TipoToken.RIGHT_PAREN) {
            return parameters();
        }
        return new ArrayList<>();
    }

    private List<Token> parameters() {
        List<Token> parameters = new ArrayList<>();
        parameters.add(match(TipoToken.IDENTIFIER));
        parameters.addAll(parameters2());
        return parameters;
    }

    // checar recursividad
    private List<Token> parameters2() {
        List<Token> parameters = new ArrayList<>();
        while (preanalisis.getTipo() == TipoToken.COMMA) {
            match(TipoToken.COMMA);
            parameters.add(match(TipoToken.IDENTIFIER));
        }
        return parameters;
    }

    private List<Expression> argumentsOptional() {
        if (preanalisis.getTipo() != TipoToken.RIGHT_PAREN) {
            return arguments();
        }
        return new ArrayList<>();
    }

    private List<Expression> arguments() {
    List<Expression> arguments = new ArrayList<>();
    arguments.add(expression());
    while (preanalisis.getTipo() == TipoToken.COMMA) {
        match(TipoToken.COMMA);
        if (preanalisis.getTipo() == TipoToken.RIGHT_PAREN) {
            System.out.println("Error linea: " + preanalisis.contLine +
                    " columna: " + preanalisis.contColumn +
                    ", se esperaba: EXPRESSION" +
                    ", se encontro: " + preanalisis.getTipo()
            );
            hayErrores = true;
        } else {
            arguments.add(expression());
        }
    }
    return arguments;
}

    private Token match(TipoToken tipo) {

            if (preanalisis.getTipo() == tipo) {
                Token token = preanalisis;
                advance();
                return token;
            }else{
                hayErrores = true;
                System.out.println("Error linea: " + preanalisis.contLine +
                        " columna: " + preanalisis.contColumn +
                        ", se esperaba: " + tipo +
                        ", se encontro: " + preanalisis.getTipo()

                );
                try{
                    advance();
                }catch (IndexOutOfBoundsException e){
                    System.out.println("Se llego al final del archivo, " + e.getMessage() );
                    System.exit(0);

                }
                
            }

        return null;
    }

    private void advance() {
        i++;
        preanalisis = tokens.get(i);
    }

    private Token previous() {
        return this.tokens.get(i - 1);
    }





}

