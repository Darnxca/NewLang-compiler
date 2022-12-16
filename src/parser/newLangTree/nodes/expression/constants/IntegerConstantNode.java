package parser.newLangTree.nodes.expression.constants;

import java_cup.runtime.ComplexSymbolFactory;
import visitor.Visitor;
import parser.newLangTree.nodes.expression.ExpressionNode;

public class IntegerConstantNode extends ExpressionNode implements Constant {

    private int value;

    public IntegerConstantNode(int value,ComplexSymbolFactory.Location left, ComplexSymbolFactory.Location right) {
        super(left, right);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }

}
