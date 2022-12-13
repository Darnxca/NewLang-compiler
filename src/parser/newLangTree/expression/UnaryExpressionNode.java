package parser.newLangTree.expression;

import java_cup.runtime.ComplexSymbolFactory;
import parser.newLangTree.visitor.Visitable;
import parser.newLangTree.visitor.Visitor;

public class UnaryExpressionNode extends ExpressionNode implements Visitable {

    private int operation;
    private ExpressionNode rightExpression; //Guarda MINUS Expr

    public UnaryExpressionNode(int operation, ExpressionNode rightExpression,ComplexSymbolFactory.Location left, ComplexSymbolFactory.Location right) {
        super(left, right);
        this.operation = operation;
        this.rightExpression = rightExpression;
    }

    public ExpressionNode getRightExpression() {
        return rightExpression;
    }

    public int getOperation() {
        return operation;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
