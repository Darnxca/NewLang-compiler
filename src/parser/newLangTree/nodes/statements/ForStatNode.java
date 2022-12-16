package parser.newLangTree.nodes.statements;

import parser.newLangTree.nodes.BodyNode;
import semantic.SymbolTable;
import visitor.Visitor;
import parser.newLangTree.nodes.expression.IdentifierExprNode;
import parser.newLangTree.nodes.expression.constants.Constant;

public class ForStatNode extends StatementNode {
    private SymbolTable symbolTableFor = new SymbolTable();
    private IdentifierExprNode id;
    private Constant constantFrom;
    private Constant constantTo;
    private BodyNode body;

    public ForStatNode(IdentifierExprNode id, Constant constantFrom, Constant constantTo, BodyNode body) {
        super();
        this.id = id;
        this.constantFrom = constantFrom;
        this.constantTo = constantTo;
        this.body = body;
    }

    public BodyNode getBody() {
        return body;
    }

    public Constant getCostantFrom() {
        return constantFrom;
    }

    public Constant getCostantTo() {
        return constantTo;
    }

    public IdentifierExprNode getIdentifier() {
        return id;
    }

    @Override
    public Object accept(Visitor v) throws Exception {
        return v.visit(this);
    }

    public SymbolTable getSymbolTableFor() {
        return symbolTableFor;
    }

    public void setSymbolTableFor(SymbolTable symbolTableFor) {
        this.symbolTableFor = symbolTableFor;
    }
}

