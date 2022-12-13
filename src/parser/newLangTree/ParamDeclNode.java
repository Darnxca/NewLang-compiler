package parser.newLangTree;

import parser.newLangTree.expression.IdentifierExprNode;
import parser.newLangTree.visitor.Visitable;
import parser.newLangTree.visitor.Visitor;

import java.util.List;

public class ParamDeclNode extends Node implements Visitable {
    private int type;
    private List<IdentifierExprNode> identifierList;
    private boolean out;

    public ParamDeclNode(int type, List<IdentifierExprNode> identifierList) {
        super();
        this.type = type;
        this.identifierList = identifierList;
    }

    public ParamDeclNode(int type, List<IdentifierExprNode> identifierList, boolean out) {
        super();
        this.type = type;
        this.identifierList = identifierList;
        this.out = out;
    }

    public int getType() {
        return type;
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
