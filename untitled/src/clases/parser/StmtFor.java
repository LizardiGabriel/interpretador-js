package clases.parser;

import clases.paquetito.TipoToken;
import clases.paquetito.Token;

import javax.swing.plaf.nimbus.State;

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
        //System.out.println("StmtFor: " + initializer + " " + condition + " " + increment + " " + body);
    }


}
