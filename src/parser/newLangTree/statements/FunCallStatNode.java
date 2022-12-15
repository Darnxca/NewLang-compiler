package parser.newLangTree.statements;

import java_cup.runtime.ComplexSymbolFactory;
import parser.newLangTree.expression.ExpressionNode;
import parser.newLangTree.expression.IdentifierExprNode;
import visitor.Visitor;

import java.util.Collections;
import java.util.List;

public class FunCallStatNode extends StatementNode {
    private IdentifierExprNode identifier;
    private List<ExpressionNode> listOfExpr;

    public FunCallStatNode(IdentifierExprNode identifier,List<ExpressionNode> listOfExpr, ComplexSymbolFactory.Location left, ComplexSymbolFactory.Location right) {
        super(left, right);
        this.identifier = identifier;
        this.listOfExpr = listOfExpr;
    }

    public FunCallStatNode(IdentifierExprNode identifier, ComplexSymbolFactory.Location left, ComplexSymbolFactory.Location right) {
        super(left, right);
        this.identifier = identifier;
        this.listOfExpr = Collections.emptyList();
    }

    public IdentifierExprNode getIdentifier() {
        return identifier;
    }

    public List<ExpressionNode> getListOfExpr() {
        return listOfExpr;
    }

    @Override
    public Object accept(Visitor v) throws Exception {
        return v.visit(this);
    }


}
