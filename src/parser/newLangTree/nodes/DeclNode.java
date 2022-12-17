package parser.newLangTree.nodes;

import parser.newLangTree.SingleTypeNode;
import visitor.Visitable;
import visitor.Visitor;

import java.util.LinkedList;
import java.util.List;

public class DeclNode extends SingleTypeNode implements Visitable {
    private List<VarDeclNode> varDeclList;
    private List<FunDeclNode> funDeclList;

    public DeclNode() {
        super();
        this.funDeclList = new LinkedList<>();
        this.varDeclList = new LinkedList<>();
    }

    public void setVarDeclList(List<VarDeclNode> varDeclList) {
        this.varDeclList= varDeclList;
    }

    public void setFunDeclList(List<FunDeclNode> funDeclList) {
        this.funDeclList = funDeclList;
    }

    public void addVarDeclList(List<VarDeclNode> varDeclList){
        this.varDeclList.addAll(varDeclList);
    }

    public void addFunDeclList(List<FunDeclNode> funDeclList){
        this.funDeclList.addAll(funDeclList);
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
