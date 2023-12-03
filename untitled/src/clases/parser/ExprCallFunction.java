package clases.parser;
import clases.paquetito.Token;

import java.util.List;

public class ExprCallFunction extends Expression{
    final Expression callee;
    final List<Expression> arguments;
    // final Token paren;

    public ExprCallFunction(Expression callee, /*Token paren,*/ List<Expression> arguments) {
        this.callee = callee;
        this.arguments = arguments;
        // this.paren = paren;
        //System.out.println("ExprCallFunction: " + callee + "Arguments: " + arguments);
    }
}
