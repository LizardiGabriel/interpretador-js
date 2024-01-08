package clases.parser;


import javax.swing.plaf.nimbus.State;
import java.util.List;

public class StmtBlock extends Statement{
    final List<Statement> statements;

    public StmtBlock(List<Statement> statements) {

        this.statements = statements;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n--> StmtBlock: {");
        for (Statement stmt : statements) {
            if (stmt != null) {
                sb.append(stmt.toString()).append(" ");
            }else {
                sb.append("null ");
            }

        }
        sb.append("}");
        return sb.toString();
    }
}


