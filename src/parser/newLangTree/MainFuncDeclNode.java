package parser.newLangTree;

import visitor.Visitable;
import visitor.Visitor;

public class MainFuncDeclNode extends TypeNode implements Visitable {

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
