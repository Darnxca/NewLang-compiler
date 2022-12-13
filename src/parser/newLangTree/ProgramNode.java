package parser.newLangTree;

import visitor.Visitable;
import visitor.Visitor;

import java.util.List;

public class ProgramNode extends TypeNode implements Visitable {

    private List<DeclNode> declList1;
    private List<DeclNode> declList2;
    private MainFuncDeclNode mainFuncDecl;

    public ProgramNode(List<DeclNode> declList1, MainFuncDeclNode mainFuncDecl, List<DeclNode> declList2) {
        super();
        this.declList1 = declList1;
        this.declList2 = declList2;
        this.mainFuncDecl = mainFuncDecl;
    }

    public List<DeclNode> getDeclList1() {
        return declList1;
    }

    public List<DeclNode> getDeclList2() {
        return declList2;
    }

    public MainFuncDeclNode getMainFuncDecl() {
        return mainFuncDecl;
    }



    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
