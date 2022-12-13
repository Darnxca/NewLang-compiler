package parser.newLangTree.expression;

import java_cup.runtime.ComplexSymbolFactory;
import parser.newLangTree.Node;
import parser.newLangTree.visitor.Visitable;
import parser.newLangTree.visitor.Visitor;

public abstract class ExpressionNode extends Node implements Visitable {

    public ExpressionNode(ComplexSymbolFactory.Location left, ComplexSymbolFactory.Location right) {
        super(left, right);
    }

    public ExpressionNode() {
    }

    abstract public Object accept(Visitor visitor);

}
