package parser.newLangTree.nodes.statements;

import java_cup.runtime.ComplexSymbolFactory;
import parser.newLangTree.SingleTypeNode;
import parser.newLangTree.nodes.expression.ExpressionNode;
import visitor.Visitable;
import visitor.Visitor;


public class InitLoopCondNode extends SingleTypeNode implements Visitable {

    private ExpressionNode expression;

    public InitLoopCondNode() {
        super();
        this.expression = null;
    }

    public InitLoopCondNode(ExpressionNode expression) {
        this.expression = expression;
    }

    public ExpressionNode getExpression() {
        return expression;
    }

    @Override
    public Object accept(Visitor v)  {
        return v.visit(this);
    }
}
