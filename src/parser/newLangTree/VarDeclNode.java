package parser.newLangTree;

import parser.newLangTree.visitor.Visitable;
import parser.newLangTree.visitor.Visitor;

import java.util.LinkedList;
import java.util.List;

public class VarDeclNode extends Node implements Visitable {

    private int type;
    private List<IdInitNode> idIList;
    private List<IdInitObbNode> idIObList;

    public VarDeclNode(int type) {
        super();
        this.type = type;
        this.idIList = new LinkedList<>();
        this.idIObList = new LinkedList<>();
    }

    public VarDeclNode() {
        super();
    }
    public void setIdInitNodeVarDeclNode(List<IdInitNode> idIList) {
        this.idIList = idIList;

    }

    public void setIdInitObbNode(List<IdInitObbNode> idIObList) {
        this.idIObList = idIObList;
    }

    public List<IdInitNode> getIdIList() {
        return idIList;
    }

    public List<IdInitObbNode> getIdIObList() {
        return idIObList;
    }

    public int getType() {
        return type;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
