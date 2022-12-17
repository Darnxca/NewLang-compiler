package parser.newLangTree.nodes.expression;

import java_cup.runtime.ComplexSymbolFactory;
import visitor.Visitable;
import visitor.Visitor;

import java.util.Collections;
import java.util.List;

public class FunCallExprNode extends ExpressionNode implements Visitable {

    private IdentifierExprNode identifier;
    private List<ExpressionNode> listOfExpr;

    public FunCallExprNode(IdentifierExprNode identifier,List<ExpressionNode> listOfExpr, ComplexSymbolFactory.Location left, ComplexSymbolFactory.Location right) {
        super(left, right);
        this.identifier = identifier;
        this.listOfExpr = listOfExpr;
    }

    public FunCallExprNode(IdentifierExprNode identifier, ComplexSymbolFactory.Location left, ComplexSymbolFactory.Location right) {
        super(left, right);
        this.identifier = identifier;
        this.listOfExpr =Collections.emptyList();
    }

    public IdentifierExprNode getIdentifier() {
        return identifier;
    }

    public List<ExpressionNode> getListOfExpr() {
        return listOfExpr;
    }

    @Override
    public Object accept(Visitor v){
        return v.visit(this);
    }
}
