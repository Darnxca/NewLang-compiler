package parser.newLangTree;

import parser.newLangTree.expression.IdentifierExprNode;
import visitor.Visitable;
import visitor.Visitor;

import java.util.List;

public class ParamDeclNode extends TypeNode implements Visitable {
    private List<IdentifierExprNode> identifierList;
    private boolean out;

    public ParamDeclNode(int type, List<IdentifierExprNode> identifierList) {
        super();
        this.setType(type);
        this.identifierList = identifierList;
    }

    public ParamDeclNode(int type, List<IdentifierExprNode> identifierList, boolean out) {
        super();
        this.setType(type);
        this.identifierList = identifierList;
        this.out = out;
    }

    public List<IdentifierExprNode> getIdentifierList() {
        return identifierList;
    }

    public boolean isOut() {
        return out;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
