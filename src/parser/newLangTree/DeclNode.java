package parser.newLangTree;

import parser.newLangTree.visitor.Visitable;
import parser.newLangTree.visitor.Visitor;

import java.util.Collections;
import java.util.List;

public class DeclNode extends Node implements Visitable {
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
