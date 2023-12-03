package clases.parser;
import clases.paquetito.Token;

import java.util.List;

public class StmtFunction extends Statement {
    final Token name;
    final List<Token> params;
    final StmtBlock body;

    public StmtFunction(Token name, List<Token> params, StmtBlock body) {
        this.name = name;
        this.params = params;
        this.body = body;
        //System.out.println("StmtFunction: " + name + " " + params + " " + body);
    }
}
