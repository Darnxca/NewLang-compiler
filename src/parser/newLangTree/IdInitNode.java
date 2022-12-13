package parser.newLangTree;

import parser.newLangTree.expression.ExpressionNode;
import parser.newLangTree.expression.IdentifierExprNode;
import parser.newLangTree.visitor.Visitable;
import parser.newLangTree.visitor.Visitor;

public class IdInitNode extends Node implements Visitable {

    private IdentifierExprNode identifier;
    private ExpressionNode expression;

    public IdInitNode(IdentifierExprNode identifier) {
        super();
        this.identifier = identifier;
    }

    public IdInitNode(IdentifierExprNode identifier, ExpressionNode expression) {
        super();
        this.identifier = identifier;
        this.expression = expression;
    }

    public IdentifierExprNode getIdentifier() {
        return identifier;
    }

    public ExpressionNode getExpression() {
        return expression;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
