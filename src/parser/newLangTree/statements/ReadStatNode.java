package parser.newLangTree.statements;

import parser.newLangTree.expression.constants.StringConstantNode;
import visitor.Visitor;
import parser.newLangTree.expression.IdentifierExprNode;

import java.util.List;

public class ReadStatNode extends StatementNode {

    private List<IdentifierExprNode> identifierList;
    private StringConstantNode stringCostant;

    public ReadStatNode(List<IdentifierExprNode> identifierList, StringConstantNode stringCostant) {
        super();
        this.identifierList = identifierList;
        this.stringCostant = stringCostant;
    }

    public ReadStatNode(List<IdentifierExprNode> identifierList) {
        super();
        this.identifierList = identifierList;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

    public List<IdentifierExprNode> getIdentifierList() {
        return identifierList;
    }

    public StringConstantNode getStringCostant() {
        return stringCostant;
    }
}
