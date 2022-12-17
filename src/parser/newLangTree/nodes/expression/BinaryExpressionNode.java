package parser.newLangTree.nodes.expression;

import java_cup.runtime.ComplexSymbolFactory;
import visitor.Visitor;

public class BinaryExpressionNode extends ExpressionNode {

    private ExpressionNode leftExpression;
    private int operation;
    private ExpressionNode rightExpression;

    public BinaryExpressionNode(ExpressionNode leftExpression, int operation, ExpressionNode rightExpression, ComplexSymbolFactory.Location left, ComplexSymbolFactory.Location right) {
        super(left,right);
        this.leftExpression = leftExpression;
        this.operation = operation;
        this.rightExpression = rightExpression;
    }

    public ExpressionNode getLeftExpression() {
        return leftExpression;
    }

    public void setLeftExpression(ExpressionNode leftExpression) {
        this.leftExpression = leftExpression;
    }

    public int getOperation() {
        return operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }

    public ExpressionNode getRightExpression() {
        return rightExpression;
    }

    public void setRightExpression(ExpressionNode rightExpression) {
        this.rightExpression = rightExpression;
    }

    @Override
    public Object accept(Visitor visitor){
        return visitor.visit(this);
    }
}
