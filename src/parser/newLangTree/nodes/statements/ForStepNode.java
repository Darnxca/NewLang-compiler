package parser.newLangTree.nodes.statements;

import parser.newLangTree.nodes.expression.ExpressionNode;
import visitor.Visitor;

public class ForStepNode extends StatementNode{

    private ExpressionNode exprLoop;

    public ForStepNode(ExpressionNode exprLoop) {
        this.exprLoop = exprLoop;
    }

    public ForStepNode() {
        this.exprLoop = null;
    }

    public ExpressionNode getExprLoop() {
        return exprLoop;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }


}
