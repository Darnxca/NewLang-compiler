package parser.newLangTree.nodes.statements;

import parser.newLangTree.SingleTypeNode;
import parser.newLangTree.nodes.IdInitNode;
import visitor.Visitable;
import visitor.Visitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InitLoopNode extends SingleTypeNode implements Visitable {

    private List<IdInitNode> idInitNodeList = new ArrayList<>();

    public InitLoopNode(int type, List<IdInitNode> idInitNodeList) {
        super();
        this.setType(type);
        this.idInitNodeList = idInitNodeList;

    }

    public InitLoopNode() {
        super();
        this.idInitNodeList = Collections.emptyList();

    }

    public List<IdInitNode> getIdInitNodeList() {
        return idInitNodeList;
    }

    @Override
    public Object accept(Visitor v)  {
        return v.visit(this);
    }
}
