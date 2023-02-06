package parser.newLangTree.nodes.statements;

import semantic.SymbolTable;
import visitor.Visitor;

import java.util.Collections;
import java.util.List;

public class InitDoForStepNode extends StatementNode {

    private InitNode init;
    private List<StatementNode> statList;
    private ForStepNode forStep;
    private StepNode step;
    private SymbolTable symbolTableInitDoForStep = new SymbolTable();

    public InitDoForStepNode(InitNode init, List<StatementNode> statList, ForStepNode forStep, StepNode step) {
        this.init = init;
        this.statList = statList;
        this.forStep = forStep;
        this.step = step;
    }

    public InitDoForStepNode(InitNode init, ForStepNode forStep, StepNode step) {
        this.init = init;
        this.forStep = forStep;
        this.statList = Collections.emptyList();
        this.step = step;
    }

    public ForStepNode getForStep() {
        return forStep;
    }

    public List<StatementNode> getStatList() {
        return statList;
    }

    public InitNode getInit() {
        return init;
    }


    public StepNode getStep() {
        return step;
    }

    public SymbolTable getSymbolTableInitDoForStep() {
        return symbolTableInitDoForStep;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
