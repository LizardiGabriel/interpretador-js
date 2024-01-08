package clases.parser;


import clases.paquetito.TablaSimbolos;

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

    @Override
    public Object resolver(TablaSimbolos tablita) {
        for (Statement stmt : statements) {
            if (stmt != null) {
                stmt.resolver(tablita);
            }
            if(stmt instanceof StmtReturn){
                //System.out.println("yo retorno menso: " + stmt.resolver(tablita).toString());
                return stmt.resolver(tablita).toString();
            }


        }

        return null;
    }


}


