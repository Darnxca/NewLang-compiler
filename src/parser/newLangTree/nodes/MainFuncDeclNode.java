package parser.newLangTree.nodes;

import parser.newLangTree.SingleTypeNode;
import visitor.Visitable;
import visitor.Visitor;

public class MainFuncDeclNode extends SingleTypeNode implements Visitable {

    private FunDeclNode funDeclNode;

    public MainFuncDeclNode(FunDeclNode funDeclNode) {
        super();
        this.funDeclNode = funDeclNode;
    }

    public MainFuncDeclNode() {
        super();
    }

    public FunDeclNode getFunDeclNode() {
        return funDeclNode;
    }

    @Override
    public Object accept(Visitor v) throws Exception {
        return v.visit(this);
    }

}
