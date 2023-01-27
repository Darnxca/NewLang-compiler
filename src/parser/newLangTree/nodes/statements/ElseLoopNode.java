package parser.newLangTree.nodes.statements;

import parser.newLangTree.nodes.VarDeclNode;
import parser.newLangTree.nodes.expression.ExpressionNode;
import semantic.SymbolTable;
import visitor.Visitable;
import visitor.Visitor;
import java.util.List;


public class ElseLoopNode extends StatementNode implements Visitable {

    private List<VarDeclNode> varDeclList; //Lista di dichiarazioni
    private List<StatementNode> statList; //Lista di istruzioni
    private ExpressionNode condizione2; //Condizione 2 (quella da utilizzare senza negare)
    private ExpressionNode condizione1; //Condizione 1 (quella del while da negare)

    private SymbolTable symbolTableElseLoop = new SymbolTable();

    public ElseLoopNode(List<VarDeclNode> varDeclList, List<StatementNode> statList, ExpressionNode condizione2, ExpressionNode condizione1) {
        this.varDeclList = varDeclList;
        this.statList = statList;
        this.condizione2 = condizione2;
        this.condizione1 = condizione1;
    }

    public List<VarDeclNode> getVarDeclList() {
        return varDeclList;
    }

    public ExpressionNode getCondizione2() {
        return condizione2;
    }

    public List<StatementNode> getStatList() {
        return statList;
    }

    public ExpressionNode getCondizione1() {
        return condizione1;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

    public void setSymbolTableElseLoop(SymbolTable symbolTableElseLoop) {
        this.symbolTableElseLoop = symbolTableElseLoop;
    }

    public SymbolTable getSymbolTableElseLoop() {
        return symbolTableElseLoop;
    }
}
