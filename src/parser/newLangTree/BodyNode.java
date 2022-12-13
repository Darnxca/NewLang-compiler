package parser.newLangTree;

import parser.newLangTree.statements.StatementNode;
import visitor.Visitable;
import visitor.Visitor;

import java.util.Collections;
import java.util.List;

public class BodyNode extends TypeNode implements Visitable {

    private List<VarDeclNode> varDeclList;

    private List<StatementNode> stmtNodeList;

    public BodyNode(List<VarDeclNode> varDeclList, List<StatementNode> stmtNodeList) {
        super();
        this.varDeclList = varDeclList;
        this.stmtNodeList = stmtNodeList;
    }

    public BodyNode() {
        super();
        this.varDeclList = Collections.emptyList();
        this.stmtNodeList = Collections.emptyList();
    }

    public void setVarDeclList(List<VarDeclNode> varDeclList) {
        this.varDeclList = varDeclList;
    }

    public void setStmtNodeList(List<StatementNode> stmtNodeList) {
        this.stmtNodeList = stmtNodeList;
    }

    public List<StatementNode> getStmtNodeList() {
        return stmtNodeList;
    }

    public List<VarDeclNode> getVarDeclList() {
        return varDeclList;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

}
