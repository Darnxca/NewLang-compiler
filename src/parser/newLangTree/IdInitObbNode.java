package parser.newLangTree;

import parser.newLangTree.expression.IdentifierExprNode;
import parser.newLangTree.expression.constants.Constant;
import parser.newLangTree.visitor.Visitable;
import parser.newLangTree.visitor.Visitor;

public class IdInitObbNode extends Node implements Visitable {

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
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}


