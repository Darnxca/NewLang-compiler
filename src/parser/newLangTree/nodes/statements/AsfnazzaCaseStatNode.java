package parser.newLangTree.nodes.statements;

import java_cup.runtime.ComplexSymbolFactory;
import parser.newLangTree.nodes.expression.ExpressionNode;
import visitor.Visitor;

import java.util.Collections;
import java.util.List;

public class AsfnazzaCaseStatNode extends StatementNode {

    private ExpressionNode exprCase;
    private List<StatementNode> statementNodeList;

    public AsfnazzaCaseStatNode(ComplexSymbolFactory.Location left, ComplexSymbolFactory.Location right, ExpressionNode exprCase, List<StatementNode> statementNodeList) {
        super(left, right);
        this.exprCase = exprCase;
        this.statementNodeList = statementNodeList;
    }

    public AsfnazzaCaseStatNode(ExpressionNode exprCase, List<StatementNode> statementNodeList) {
        this.exprCase = exprCase;
        this.statementNodeList = statementNodeList;
    }

    public AsfnazzaCaseStatNode(ExpressionNode exprCase) {
        this.exprCase = exprCase;
        this.statementNodeList = Collections.emptyList();
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

    public ExpressionNode getExprCase() {
        return exprCase;
    }

    public List<StatementNode> getStatementNodeList() {
        return statementNodeList;
    }
}
