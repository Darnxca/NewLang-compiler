package parser.newLangTree.nodes.statements;

import parser.newLangTree.nodes.BodyNode;
import semantic.SymbolTable;
import visitor.Visitor;
import parser.newLangTree.nodes.expression.ExpressionNode;

public class WhileStatNode extends StatementNode {

    private SymbolTable symbolTableWhile = new SymbolTable();
    private ExpressionNode expression;
    private BodyNode body;

    private ElseLoopNode elseLoop; //Opzionale


    public WhileStatNode(ExpressionNode expression, BodyNode body) {
        super();
        this.expression = expression;
        this.body = body;
        this.elseLoop = null;
    }

    public WhileStatNode(ExpressionNode expression, BodyNode body, ElseLoopNode elseLoop) {
        this.expression = expression;
        this.body = body;
        this.elseLoop = elseLoop;
    }

    @Override
    public Object accept(Visitor v) {
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

    public ElseLoopNode getElseLoop() {
        return elseLoop;
    }

    public void setSymbolTableWhile(SymbolTable symbolTableWhile) {
        this.symbolTableWhile = symbolTableWhile;
    }
}
