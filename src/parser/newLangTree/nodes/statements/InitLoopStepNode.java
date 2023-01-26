package parser.newLangTree.nodes.statements;

import parser.newLangTree.SingleTypeNode;
import parser.newLangTree.nodes.expression.ExpressionNode;
import visitor.Visitable;
import visitor.Visitor;

import java.util.Collections;
import java.util.List;

public class InitLoopStepNode extends SingleTypeNode implements Visitable {

    private List<ExpressionNode> expressionList;

    public InitLoopStepNode(){
        this.expressionList = Collections.emptyList();
    }

    public InitLoopStepNode(List<ExpressionNode> exprList){
        this.expressionList = exprList;
    }

    public List<ExpressionNode> getExpressionList() {
        return expressionList;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
