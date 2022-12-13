package parser.newLangTree.expression.constants;

import java_cup.runtime.ComplexSymbolFactory;
import visitor.Visitor;
import parser.newLangTree.expression.ExpressionNode;

public class BooleanConstantNode extends ExpressionNode implements Constant {

    private boolean value;

    public BooleanConstantNode(boolean value,ComplexSymbolFactory.Location left, ComplexSymbolFactory.Location right) {
        super(left, right);
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }



}
