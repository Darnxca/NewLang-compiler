package parser.newLangTree.nodes.statements;

import parser.newLangTree.nodes.BodyNode;
import semantic.SymbolTable;
import visitor.Visitor;
import parser.newLangTree.nodes.expression.ExpressionNode;

public class IfStatNode extends StatementNode {

    private ExpressionNode expression;
    private BodyNode bodyThen;
    private BodyNode bodyElse;

    private SymbolTable symbolTableIfScope = new SymbolTable();
    private SymbolTable symbolTableElseScope = new SymbolTable();

    public IfStatNode() {
        super();
    }

    public IfStatNode(ExpressionNode expression, BodyNode bodyThen) {
        super();
        this.expression = expression;
        this.bodyThen = bodyThen;
        this.bodyElse = null;
    }

    public IfStatNode(ExpressionNode expression, BodyNode bodyThen, BodyNode bodyElse) {
        super();
        this.expression = expression;
        this.bodyThen = bodyThen;
        this.bodyElse = bodyElse;
    }

    public ExpressionNode getExpression() {
        return expression;
    }

    public BodyNode getBodyElse() {
        return bodyElse;
    }

    public BodyNode getBodyThen() {
        return bodyThen;
    }

    @Override
    public Object accept(Visitor v){
        return v.visit(this);
    }

    public SymbolTable getSymbolTableIfScope() {
        return symbolTableIfScope;
    }

    public void setSymbolTableIfScope(SymbolTable symbolTableIfScope) {
        this.symbolTableIfScope = symbolTableIfScope;
    }

    public SymbolTable getSymbolTableElseScope() {
        return symbolTableElseScope;
    }

    public void setSymbolTableElseScope(SymbolTable symbolTableElseScope) {
        this.symbolTableElseScope = symbolTableElseScope;
    }
}
