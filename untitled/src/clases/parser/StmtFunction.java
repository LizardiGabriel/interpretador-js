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

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n--> StmtFunction: ").append(name.getLexema()).append(" ");
        for (Token param : params) {
            sb.append("Param: ").append(param.getLexema()).append(", ");
        }
        sb.append("Body: ").append(body.toString());

        return sb.toString();
    }
}
