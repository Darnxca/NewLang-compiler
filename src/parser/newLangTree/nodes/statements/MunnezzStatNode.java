package parser.newLangTree.nodes.statements;

import java_cup.runtime.ComplexSymbolFactory;
import parser.newLangTree.nodes.expression.ExpressionNode;
import visitor.Visitor;

import java.util.Collections;
import java.util.List;

public class MunnezzStatNode extends StatementNode {

    private ExpressionNode exprCond;
    private List<AsfnazzaCaseStatNode> caseStatNodeList;

    public MunnezzStatNode(ComplexSymbolFactory.Location left, ComplexSymbolFactory.Location right, ExpressionNode exprCond, List<AsfnazzaCaseStatNode> caseStatNodeList) {
        super(left, right);
        this.exprCond = exprCond;
        this.caseStatNodeList = caseStatNodeList;
    }

    public MunnezzStatNode(ExpressionNode exprCond, List<AsfnazzaCaseStatNode> caseStatNodeList) {
        this.exprCond = exprCond;
        this.caseStatNodeList = caseStatNodeList;
    }

    public MunnezzStatNode(ExpressionNode exprCond) {
        this.exprCond = exprCond;
        this.caseStatNodeList = Collections.emptyList();
    }

    public ExpressionNode getExprCond() {
        return exprCond;
    }

    public List<AsfnazzaCaseStatNode> getCaseStatNodeList() {
        return caseStatNodeList;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
