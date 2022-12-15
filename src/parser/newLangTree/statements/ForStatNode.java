package parser.newLangTree.statements;

import parser.newLangTree.BodyNode;
import visitor.Visitor;
import parser.newLangTree.expression.IdentifierExprNode;
import parser.newLangTree.expression.constants.Constant;

public class ForStatNode extends StatementNode {

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


}

