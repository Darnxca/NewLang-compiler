package parser.newLangTree.nodes.expression.constants;

import java_cup.runtime.ComplexSymbolFactory;
import visitor.Visitor;
import parser.newLangTree.nodes.expression.ExpressionNode;

public class StringConstantNode extends ExpressionNode implements Constant {

    private String value;

    public StringConstantNode( String value,ComplexSymbolFactory.Location left, ComplexSymbolFactory.Location right) {
        super(left, right);
        this.value = value;
    }


    public String getValue() {
        return value;
    }

    @Override
    public Object accept(Visitor v) throws Exception {
        return v.visit(this);
    }
}
