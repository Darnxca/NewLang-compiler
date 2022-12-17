package parser.newLangTree.nodes.statements;

import visitor.Visitor;
import parser.newLangTree.nodes.expression.ExpressionNode;

public class ReturnStatNode extends StatementNode {

    private ExpressionNode expression;

    public ReturnStatNode() {
    }

    public ReturnStatNode(ExpressionNode expression) {
        super();
        this.expression = expression;
    }

    public ExpressionNode getExpression() {
        return expression;
    }

    @Override
    public Object accept(Visitor v){
        return v.visit(this);
    }
}
