package visitor;

import exception.MultipleFunctionDeclaration;
import exception.MultipleMainDeclaration;
import exception.MultipleVariableDeclaration;
import parser.Symbols;
import parser.newLangTree.nodes.*;
import parser.newLangTree.nodes.expression.*;
import parser.newLangTree.nodes.expression.constants.*;
import parser.newLangTree.nodes.statements.*;
import semantic.SymbolTableStack;
import semantic.symbols.*;

import java.util.LinkedList;
import java.util.List;

public class PrintScopeVisitor implements Visitor{

    private SymbolTableStack stack;

    public PrintScopeVisitor() {
        stack = new SymbolTableStack();
    }

    @Override
    public Object visit(ProgramNode item) {
        stack.enterScope(item.getSymbolTableProgramScope()); //Aggiorno lo stack

        item.getDecl().accept(this);

        item.getMainFuncDecl().accept(this);

        System.out.println("PROGRAM: \n"+item.getSymbolTableProgramScope().toString());

        return null;
    }

    @Override
    public Object visit(DeclNode item) {

        for (VarDeclNode i : item.getVarDeclList())
            i.accept(this);

        for (FunDeclNode i : item.getFunDeclList())
                i.accept(this);

        return null;
    }

    @Override
    public Object visit(MainFuncDeclNode item) {
        
        item.getFunDeclNode().accept(this);

        return null;
    }

    @Override
    public Object visit(VarDeclNode item) {

        for (IdInitNode i : item.getIdIList()) {
            i.accept(this);
        }

        for (IdInitObbNode i : item.getIdIObList())
            i.accept(this);

        return null;
    }


    @Override
    public Object visit(IdInitNode item) {
        return null;
    }

    @Override
    public Object visit(IdInitObbNode item) {
        return null;
    }

    @Override
    public Object visit(FunDeclNode item) {

        String funName = item.getIdentifier().getValue();

        //Cambio scope
        stack.enterScope(item.getSymbolTableFunScope());

        for (ParamDeclNode param : item.getParamDecl())
            param.accept(this);

        item.getBody().accept(this);

        System.out.println("Function "+funName+":\n"+item.getSymbolTableFunScope().toString());

        stack.exitScope(); //Ripristino lo scope precedente

        return null;
    }

    @Override
    public Object visit(ParamDeclNode item) {
        return null;
    }

    @Override
    public Object visit(BodyNode item) {

        for(VarDeclNode vd : item.getVarDeclList()){
            vd.accept(this);
        }

        //TODO: StatList
        for(StatementNode st : item.getStmtNodeList()){
            st.accept(this);
        }

        return null;
    }

    @Override
    public Object visit(IfStatNode item) {

       //Lo scope attuale sarà quello dell'if
        stack.enterScope(item.getSymbolTableIfScope());

        item.getBodyThen().accept(this);
        System.out.println("IF-THEN\n"+item.getSymbolTableIfScope().toString());

        //Ripristino lo scope padre
        stack.exitScope();

        if(item.getBodyElse() != null) {

            //Cambio scope di nuovo, devo conservarlo e aggiorno lo scope
            stack.enterScope(item.getSymbolTableElseScope());

            item.getBodyElse().accept(this);
            stack.exitScope(); //Ripristino lo scope

            System.out.println("IF-ELSE\n"+item.getSymbolTableElseScope().toString());
        }

        return null;
    }

    @Override
    public Object visit(AssignStatNode item) {
        return null;
    }

    @Override
    public Object visit(ForStatNode item) {

        //Cambio scope, aggiorno scope corrente
        stack.enterScope(item.getSymbolTableFor());

        item.getIdentifier().accept(this);

        item.getBody().accept(this);

        //Ripristino scope del padre
        stack.exitScope();

        System.out.println("FOR\n"+item.getSymbolTableFor().toString());

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

        //Cambio scope, quindi aggiorno quello corrente
        stack.enterScope(item.getSymbolTableWhile());

        item.getBody().accept(this);

        //Ristabilire lo stack del padre
        stack.exitScope(); //Ripristino lo scope padre

        System.out.println("WHILE\n"+item.getSymbolTableWhile().toString());

        return null;
    }

    @Override
    public Object visit(WriteStatNode item) {
        return null;
    }

    @Override
    public Object visit(IntegerConstantNode item) {
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

    @Override
    public Object visit(MunnezzStatNode item) {
        return null;
    }

    @Override
    public Object visit(AsfnazzaCaseStatNode item) {
        return null;
    }
}
