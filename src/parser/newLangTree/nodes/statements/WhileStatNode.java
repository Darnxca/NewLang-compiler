package parser.newLangTree.nodes.statements;

import parser.newLangTree.nodes.BodyNode;
import semantic.SymbolTable;
import visitor.Visitor;
import parser.newLangTree.nodes.expression.ExpressionNode;

public class WhileStatNode extends StatementNode {

    private SymbolTable symbolTableWhile = new SymbolTable();
    private ExpressionNode expression;
    private BodyNode body;


    public WhileStatNode(ExpressionNode expression, BodyNode body) {
        super();
        this.expression = expression;
        this.body = body;
    }

    @Override
    public Object accept(Visitor v) throws Exception {
        return v.visit(this);
    }

    public ExpressionNode getExpression() {
        return expression;
    }

    public BodyNode getBody() {
        return body;
    }

    public SymbolTable getSymbolTableWhile() {
        return symbolTableWhile;
    }

    public void setSymbolTableWhile(SymbolTable symbolTableWhile) {
        this.symbolTableWhile = symbolTableWhile;
    }
}
