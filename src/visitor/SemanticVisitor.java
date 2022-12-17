package visitor;


import parser.Symbols;
import parser.newLangTree.nodes.*;
import parser.newLangTree.nodes.expression.BinaryExpressionNode;
import parser.newLangTree.nodes.expression.FunCallExprNode;
import parser.newLangTree.nodes.expression.IdentifierExprNode;
import parser.newLangTree.nodes.expression.UnaryExpressionNode;
import parser.newLangTree.nodes.expression.constants.*;
import parser.newLangTree.nodes.statements.*;
import semantic.symbols.IdSymbol;
import semantic.symbols.Symbol;
import semantic.SymbolTableStack;
import semantic.SymbolTypes;

public class SemanticVisitor implements Visitor {

    public SymbolTableStack stack;

    public SemanticVisitor() {
        stack = new SymbolTableStack();
    }

    public SymbolTableStack getStack() {
        return stack;
    }

    @Override
    public Object visit(ProgramNode item) {
        stack.enterScope();
        /*for (DeclNode vd : item.getDeclList())
            vd.accept(this);*/

        /*for (MainFuncDeclNode p : item.getMainFuncDecl())
                p.accept(this);
                */
        return null;
    }

    @Override
    public Object visit(DeclNode item) {

        for (VarDeclNode vd : item.getVarDeclList())
                vd.accept(this);

        return null;
    }

    @Override
    public Object visit(MainFuncDeclNode item) {
        return null;
    }

    @Override
    public Object visit(VarDeclNode item) {

        for (int i = 0; i < item.getIdIList().size(); i++) {
            IdInitNode id = item.getIdIList().get(i);
            id.setType(item.getType());
            id.accept(this);
        }
        return null;
    }

    @Override
    public Object visit(IdInitNode item) {

        if (stack.probe(item.getIdentifier().getValue()) || stack.lookup(item.getIdentifier().getValue(), SymbolTypes.FUNCTION) != null)
            throw new RuntimeException("Simbolo non dichiarato");

        if (item.getExpression() != null) {
            item.getExpression().accept(this);
        }

        stack.addId(new IdSymbol(item.getIdentifier().getValue(), item.getType()));

        return null;
    }

    @Override
    public Object visit(IdInitObbNode item) {
        return null;
    }

    @Override
    public Object visit(FunDeclNode item) {
        return null;
    }

    @Override
    public Object visit(ParamDeclNode item) {
        return null;
    }

    @Override
    public Object visit(BodyNode item) {
        return null;
    }

    @Override
    public Object visit(IfStatNode item) {
        return null;
    }

    @Override
    public Object visit(AssignStatNode item) {
        return null;
    }

    @Override
    public Object visit(ForStatNode item) {
        return null;
    }

    @Override
    public Object visit(ReadStatNode item) {
        return null;
    }

    @Override
    public Object visit(ReturnStatNode item) {
        return null;
    }

    @Override
    public Object visit(WhileStatNode item) {
        return null;
    }

    @Override
    public Object visit(WriteStatNode item) {
        return null;
    }

    @Override
    public Object visit(IntegerConstantNode item) {
        item.setType(Symbols.INTEGER);
        return null;
    }

    @Override
    public Object visit(BooleanConstantNode item) {
        return null;
    }

    @Override
    public Object visit(RealConstantNode item) {
        return null;
    }

    @Override
    public Object visit(StringConstantNode item) {
        return null;
    }

    @Override
    public Object visit(CharConstantNode item) {
        return null;
    }

    @Override
    public Object visit(FunCallExprNode item) {
        return null;
    }

    @Override
    public Object visit(FunCallStatNode item) {
        return null;
    }

    @Override
    public Object visit(IdentifierExprNode item) {
        Symbol s;
        if ((s = stack.lookup(item.getValue(),SymbolTypes.VAR)) == null )
            throw new RuntimeException("Simbolo non trovato "+ item.getValue());

        //item.setType(s.getType());

        return null;

    }

    @Override
    public Object visit(UnaryExpressionNode item) {
        return null;
    }

    @Override
    public Object visit(BinaryExpressionNode item) {
        return null;
    }
}
