package parser.newLangTree.statements;

import parser.newLangTree.BodyNode;
import parser.newLangTree.visitor.Visitor;
import parser.newLangTree.expression.ExpressionNode;

public class WhileStatNode extends StatementNode {

    private ExpressionNode expression;
    private BodyNode body;


    public WhileStatNode(ExpressionNode expression, BodyNode body) {
        super();
        this.expression = expression;
        this.body = body;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

    public ExpressionNode getExpression() {
        return expression;
    }

    public BodyNode getBody() {
        return body;
    }
}
