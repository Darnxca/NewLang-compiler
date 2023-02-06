package parser.newLangTree.nodes.statements;

import parser.newLangTree.nodes.expression.ExpressionNode;
import visitor.Visitor;

import java.util.Collections;
import java.util.List;

public class StepNode extends StatementNode{

    private List<ExpressionNode> exprList;

    public StepNode(List<ExpressionNode> exprList) {
        this.exprList = exprList;
    }

    public StepNode() {
        this.exprList = Collections.emptyList();
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

    public List<ExpressionNode> getExprList() {
        return exprList;
    }
}
