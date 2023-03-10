package parser.newLangTree.nodes.statements;

import visitor.Visitor;
import parser.newLangTree.nodes.expression.ExpressionNode;
import parser.newLangTree.nodes.expression.IdentifierExprNode;
import java.util.List;

public class AssignStatNode extends StatementNode {

    private List<IdentifierExprNode> identifierList;
    private List<ExpressionNode> expressionList;

    public AssignStatNode(List<IdentifierExprNode> identifierList, List<ExpressionNode> expressionList) {
        super();
        this.identifierList = identifierList;
        this.expressionList = expressionList;
    }

    public List<IdentifierExprNode> getIdentifierList() {
        return identifierList;
    }

    public List<ExpressionNode> getExpressionList() {
        return expressionList;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }


}
