package clases.parser;


public class StmtReturn extends Statement {
    final Expression value;

    public StmtReturn(Expression value) {
        this.value = value;
        //System.out.println("StmtReturn: " + value);
    }
}
