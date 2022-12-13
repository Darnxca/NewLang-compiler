package parser.newLangTree.expression;

import java_cup.runtime.ComplexSymbolFactory;
import parser.newLangTree.TypeNode;
import visitor.Visitable;
import visitor.Visitor;

public abstract class ExpressionNode extends TypeNode implements Visitable {

    public ExpressionNode(ComplexSymbolFactory.Location left, ComplexSymbolFactory.Location right) {
        super(left, right);
    }

    public ExpressionNode() {
    }

    abstract public Object accept(Visitor visitor);

}
