package parser.newLangTree;

import parser.newLangTree.visitor.Visitable;
import parser.newLangTree.visitor.Visitor;

public class MainFuncDeclNode extends Node implements Visitable {

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
    public Object accept(Visitor v) {
        return v.visit(this);
    }

}
