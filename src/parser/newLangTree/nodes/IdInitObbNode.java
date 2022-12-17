package parser.newLangTree.nodes;

import parser.newLangTree.SingleTypeNode;
import parser.newLangTree.nodes.expression.IdentifierExprNode;
import parser.newLangTree.nodes.expression.constants.Constant;
import visitor.Visitable;
import visitor.Visitor;

public class IdInitObbNode extends SingleTypeNode implements Visitable {

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
    public Object accept(Visitor v){
        return v.visit(this);
    }
}


