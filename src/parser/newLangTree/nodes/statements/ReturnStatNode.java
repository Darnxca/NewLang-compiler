package parser.newLangTree.nodes.statements;

import java_cup.runtime.ComplexSymbolFactory;
import visitor.Visitor;
import parser.newLangTree.nodes.expression.ExpressionNode;

public class ReturnStatNode extends StatementNode {

    private ExpressionNode expression;

    public ReturnStatNode(ComplexSymbolFactory.Location left, ComplexSymbolFactory.Location right) {
        super(left, right);
        this.expression = null;
    }

    public ReturnStatNode(ExpressionNode expression, ComplexSymbolFactory.Location left, ComplexSymbolFactory.Location right) {
        super(left, right);
        this.expression = expression;
    }

    public ExpressionNode getExpression() {
        return expression;
    }

    @Override
    public Object accept(Visitor v){
        return v.visit(this);
    }
}
