package parser.newLangTree;

import visitor.Visitable;
import visitor.Visitor;

import java.util.Collections;
import java.util.List;

public class DeclNode extends TypeNode implements Visitable {
    private List<VarDeclNode> varDeclList;
    private List<FunDeclNode> funDeclList;

    public DeclNode() {
        super();
        this.funDeclList = Collections.emptyList();
        this.varDeclList = Collections.emptyList();
    }
    public void addVarDeclList(List<VarDeclNode> varDeclList) {
        this.varDeclList= varDeclList;
    }

    public void addFunDeclList(List<FunDeclNode> funDeclList) {
        this.funDeclList = funDeclList;
    }

    public List<VarDeclNode> getVarDeclList() {
        return varDeclList;
    }

    public List<FunDeclNode> getFunDeclList() {
        return funDeclList;
    }


    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
