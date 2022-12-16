package parser.newLangTree.nodes.expression;

import java_cup.runtime.ComplexSymbolFactory;
import visitor.Visitable;
import visitor.Visitor;

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
    public Object accept(Visitor v) throws Exception {
        return v.visit(this);
    }
}
