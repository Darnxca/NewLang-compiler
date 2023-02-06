package parser.newLangTree.nodes.statements;

import parser.newLangTree.nodes.VarDeclNode;
import visitor.Visitor;

public class InitNode extends StatementNode{

    private VarDeclNode initExpr;

    public InitNode(VarDeclNode initExpr) {
        this.initExpr = initExpr;
    }

    public InitNode() {
        this.initExpr = null;
    }
    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

    public VarDeclNode getInitExpr() {
        return initExpr;
    }
}
