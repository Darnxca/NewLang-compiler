package parser.newLangTree;

import parser.newLangTree.expression.IdentifierExprNode;
import visitor.Visitable;
import visitor.Visitor;

import java.util.Collections;
import java.util.List;

public class FunDeclNode extends TypeNode implements Visitable {

    private IdentifierExprNode identifier;
    private List<ParamDeclNode> paramDecl;
    private int typeOrVoid; //Valore del simbolo, typeOrVoid è una foglia

    private BodyNode body;

    public FunDeclNode(IdentifierExprNode identifier, List<ParamDeclNode> paramDeclNode, int typeOrVoid, BodyNode body) {
        super();
        this.identifier = identifier;
        this.paramDecl = paramDeclNode;
        this.typeOrVoid = typeOrVoid;
        this.body = body;
    }

    public FunDeclNode(IdentifierExprNode identifier, int typeOrVoid, BodyNode body) {
        super();
        this.paramDecl= Collections.emptyList();
        this.identifier = identifier;
        this.typeOrVoid = typeOrVoid;
        this.body = body;
    }

    public FunDeclNode() {
        super();
    }

    public IdentifierExprNode getIdentifier() {
        return identifier;
    }

    public BodyNode getBody() {
        return body;
    }

    public int getTypeOrVoid() {
        return typeOrVoid;
    }

    public List<ParamDeclNode> getParamDecl() {
        return paramDecl;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
