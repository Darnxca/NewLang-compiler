package parser.newLangTree.nodes.statements;

import java_cup.runtime.ComplexSymbolFactory;
import parser.newLangTree.SingleTypeNode;
import semantic.SymbolTable;
import visitor.Visitable;
import visitor.Visitor;

import java.util.Collections;
import java.util.List;

public class InitDoForStepNode extends StatementNode implements Visitable {

    /**
     * InitLoop:il  DO LBRAC StatList:sl RBRAC InitLoopCond:ilc InitLoopStep:ils
     *                 | InitLoop:il  DO LBRAC RBRAC InitLoopCond:ilc InitLoopStep:ils
     */

    private InitLoopNode initLoop;
    private List<StatementNode> statementList; //Può essere vuota
    private InitLoopCondNode loopCond;
    private InitLoopStepNode loopStep;

    private SymbolTable symbolTableInDoForStepScope = new SymbolTable();


    public InitDoForStepNode(ComplexSymbolFactory.Location left, ComplexSymbolFactory.Location right, InitLoopNode initLoop, List<StatementNode> statementList, InitLoopCondNode loopCond, InitLoopStepNode loopStep) {
        super(left, right);
        this.initLoop = initLoop;
        this.statementList = statementList;
        this.loopCond = loopCond;
        this.loopStep = loopStep;
    }

    public InitDoForStepNode(ComplexSymbolFactory.Location left, ComplexSymbolFactory.Location right, InitLoopNode initLoop, InitLoopCondNode loopCond, InitLoopStepNode loopStep) {
        super(left, right);
        this.initLoop = initLoop;
        this.statementList = Collections.emptyList();
        this.loopCond = loopCond;
        this.loopStep = loopStep;
    }


    public InitLoopCondNode getLoopCond() {
        return loopCond;
    }

    public InitLoopNode getInitLoop() {
        return initLoop;
    }

    public List<StatementNode> getStatementList() {
        return statementList;
    }

    public InitLoopStepNode getLoopStep() {
        return loopStep;
    }

    public void setSymbolTableInDoForStepScope(SymbolTable symbolTableInDoForStepScope) {
        this.symbolTableInDoForStepScope = symbolTableInDoForStepScope;
    }

    public SymbolTable getSymbolTableInDoForStepScope() {
        return symbolTableInDoForStepScope;
    }

    @Override
    public Object accept(Visitor v)  {
        return v.visit(this);
    }
}

