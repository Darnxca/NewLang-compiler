package parser.newLangTree.statements;

import parser.newLangTree.BodyNode;
import visitor.Visitor;
import parser.newLangTree.expression.ExpressionNode;

public class IfStatNode extends StatementNode {

    private ExpressionNode expression;
    private BodyNode bodyThen;
    private BodyNode bodyElse;

    public IfStatNode() {
        super();
    }

    public IfStatNode(ExpressionNode expression, BodyNode bodyThen) {
        super();
        this.expression = expression;
        this.bodyThen = bodyThen;
        this.bodyElse = null;
    }

    public IfStatNode(ExpressionNode expression, BodyNode bodyThen, BodyNode bodyElse) {
        super();
        this.expression = expression;
        this.bodyThen = bodyThen;
        this.bodyElse = bodyElse;
    }

    public ExpressionNode getExpression() {
        return expression;
    }

    public BodyNode getBodyElse() {
        return bodyElse;
    }

    public BodyNode getBodyThen() {
        return bodyThen;
    }

    @Override
    public Object accept(Visitor v) throws Exception {
        return v.visit(this);
    }

}
