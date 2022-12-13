package parser.newLangTree.expression;

import java_cup.runtime.ComplexSymbolFactory;
import visitor.Visitable;
import visitor.Visitor;

public class IdentifierExprNode extends ExpressionNode implements Visitable {

    private String value;

    public IdentifierExprNode(String value, ComplexSymbolFactory.Location left, ComplexSymbolFactory.Location right) {
        super(left, right);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
