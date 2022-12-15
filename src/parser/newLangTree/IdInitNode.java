package parser.newLangTree;

import parser.newLangTree.expression.ExpressionNode;
import parser.newLangTree.expression.IdentifierExprNode;
import visitor.Visitable;
import visitor.Visitor;

public class IdInitNode extends TypeNode implements Visitable {

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
    public Object accept(Visitor v) throws Exception {
        return v.visit(this);
    }
}
