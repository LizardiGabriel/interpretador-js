package parser;
import paquetito.Token;

import javax.swing.plaf.nimbus.State;

import paquetito.TipoToken;

public class StmtFor extends Statement{
    final Statement initializer;
    final Expression condition;
    final Expression increment;
    final Statement body;

    public StmtFor(Statement initializer, Expression condition, Expression increment, Statement body) {
        this.initializer = initializer;
        this.condition = condition;
        this.increment = increment;
        this.body = body;
    }


}
