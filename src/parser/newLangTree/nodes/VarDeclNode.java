package parser.newLangTree.nodes;

import parser.newLangTree.SingleTypeNode;
import parser.newLangTree.nodes.IdInitNode;
import parser.newLangTree.nodes.IdInitObbNode;
import visitor.Visitable;
import visitor.Visitor;

import java.util.LinkedList;
import java.util.List;

public class VarDeclNode extends SingleTypeNode implements Visitable {


    private List<IdInitNode> idIList;
    private List<IdInitObbNode> idIObList;

    public VarDeclNode(int type) {
        super();
        this.setType(type);
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



    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
