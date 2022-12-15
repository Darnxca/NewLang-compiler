package parser.newLangTree;

import parser.newLangTree.expression.IdentifierExprNode;
import parser.newLangTree.expression.constants.Constant;
import visitor.Visitable;
import visitor.Visitor;

public class IdInitObbNode extends TypeNode implements Visitable {

    private IdentifierExprNode identifier;
    private Constant constantValue;

    public IdInitObbNode(IdentifierExprNode identifier, Constant constantValue) {
        super();
        this.identifier = identifier;
        this.constantValue = constantValue;
    }

    public IdentifierExprNode getIdentifier() {
        return identifier;
    }

    public Constant getCostantValue() {
        return constantValue;
    }

    @Override
    public Object accept(Visitor v) throws Exception {
        return v.visit(this);
    }
}


