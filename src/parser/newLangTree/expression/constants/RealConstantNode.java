package parser.newLangTree.expression.constants;

import java_cup.runtime.ComplexSymbolFactory;
import parser.newLangTree.visitor.Visitor;
import parser.newLangTree.expression.ExpressionNode;

public class RealConstantNode extends ExpressionNode implements Constant {

    private float value;

    public RealConstantNode(float value,ComplexSymbolFactory.Location left, ComplexSymbolFactory.Location right) {
        super(left, right);
        this.value = value;
    }

    public float getValue() {
        return value;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
