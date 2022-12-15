package parser.newLangTree.statements;

import visitor.Visitor;
import parser.newLangTree.expression.ExpressionNode;

import java.util.List;

public class WriteStatNode extends StatementNode{

    private List<ExpressionNode> expressionList;
    private boolean newLine; //Caso di Writeln

    public WriteStatNode(List<ExpressionNode> expressionList, boolean newLine) {
        super();
        this.expressionList = expressionList;
        this.newLine = newLine;
    }

    @Override
    public Object accept(Visitor v) throws Exception {
        return v.visit(this);
    }

    public List<ExpressionNode> getExpressionList() {
        return expressionList;
    }

    public boolean isNewLine() {
        return newLine;
    }
}
