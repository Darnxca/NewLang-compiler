package parser.newLangTree.nodes;

import parser.newLangTree.SingleTypeNode;
import parser.newLangTree.nodes.DeclNode;
import parser.newLangTree.nodes.MainFuncDeclNode;
import semantic.SymbolTable;
import visitor.Visitable;
import visitor.Visitor;

import java.util.List;

public class ProgramNode extends SingleTypeNode implements Visitable {

    private DeclNode decl;
    private MainFuncDeclNode mainFuncDecl;

    private SymbolTable symbolTableProgramScope = new SymbolTable();



    public ProgramNode(List<DeclNode> declList1, MainFuncDeclNode mainFuncDecl, List<DeclNode> declList2) {
        super();
        this.decl = initilizeDeclNode(declList1, declList2);
        this.mainFuncDecl = mainFuncDecl;
    }

    private DeclNode initilizeDeclNode(List<DeclNode> declList1, List<DeclNode> declList2){
        DeclNode temp = new DeclNode();

        for (DeclNode x: declList1){
            temp.addVarDeclList(x.getVarDeclList());
        }

        for (DeclNode x: declList2){
            temp.addVarDeclList(x.getVarDeclList());
        }

        for (DeclNode x: declList1){
            temp.addFunDeclList(x.getFunDeclList());
        }

        for (DeclNode x: declList2){
            temp.addFunDeclList(x.getFunDeclList());
        }
        return temp;
    }

    public MainFuncDeclNode getMainFuncDecl() {
        return mainFuncDecl;
    }

    public DeclNode getDecl() {
        return decl;
    }

    public SymbolTable getSymbolTableProgramScope() {
        return symbolTableProgramScope;
    }

    public void setSymbolTableProgramScope(SymbolTable symbolTableProgramScope) {
        this.symbolTableProgramScope = symbolTableProgramScope;
    }

    @Override
    public Object accept(Visitor v){
        return v.visit(this);
    }
}
