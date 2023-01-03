package parser.newLangTree.nodes.expression;

import java_cup.runtime.ComplexSymbolFactory;
import visitor.Visitable;
import visitor.Visitor;

public class IdentifierExprNode extends ExpressionNode implements Visitable {

    private String value;
    private boolean isPointer; //Utilizzato per la gestione dei puntatori
    private boolean isParameter; //Utilizzato per la gestione dei parametri della funziona

    public IdentifierExprNode(String value, ComplexSymbolFactory.Location left, ComplexSymbolFactory.Location right) {
        super(left, right);
        this.value = value;
        this.isPointer = false;
        this.isParameter = false;
    }

    public void setParameter(boolean parameter) {
        this.isParameter = parameter;
    }

    public boolean isParameter() {
        return isParameter;
    }

    public void setPointer(boolean pointer) {
        isPointer = pointer;
    }

    public boolean isPointer() {
        return isPointer;
    }

    public String getValue() {
        return value;
    }

    @Override
    public Object accept(Visitor v){
        return v.visit(this);
    }
}
