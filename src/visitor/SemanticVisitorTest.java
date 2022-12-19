package visitor;

import parser.Symbols;
import parser.newLangTree.nodes.*;
import parser.newLangTree.nodes.expression.*;
import parser.newLangTree.nodes.expression.constants.*;
import parser.newLangTree.nodes.statements.*;
import semantic.SymbolTableStack;
import semantic.SymbolTypes;
import semantic.exception.MultipleIdentifierDeclaration;
import semantic.exception.MultipleMainDeclaration;
import semantic.symbols.FunSymbol;
import semantic.symbols.IdSymbol;

import java.util.LinkedList;
import java.util.List;

import static parser.Symbols.terminalNames;

public class SemanticVisitorTest implements Visitor{

    private SymbolTableStack stack;

    public SemanticVisitorTest() {
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

        /*for (FunDeclNode i : item.getFunDeclList()){
            i.accept(this);
        }*/


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

        item.getIdentifier().accept(this);
        //System.out.println(item.getIdentifier().getValue());
        if(item.getExpression() != null){
            item.getExpression().accept(this);

            System.out.println(item.getExpression().getType());

            int type = TypeChecker.checkBinaryExpr(Symbols.ASSIGN, item.getIdentifier().getType(), item.getExpression().getType());
            if(type == -1) {

                throw new RuntimeException("Assegnazione tra tipi incompatibili (riga: "+item.getExpression().getLeft().getLine() +
                        ", colonna: "+ item.getExpression().getRight().getColumn()+") -> "+
                        terminalNames[item.getIdentifier().getType()].toLowerCase()+ " e "+ terminalNames[item.getExpression().getType()].toLowerCase() );
            }


        }

        return null;
    }

    @Override
    public Object visit(IdInitObbNode item) {

        item.setType(item.getIdentifier().getType());

        return null;
    }


    @Override
    public Object visit(FunDeclNode item) {

        //Cambio scope
        stack.enterScope(item.getSymbolTableFunScope());

        for (ParamDeclNode param : item.getParamDecl())
            param.accept(this);

        item.getBody().accept(this);

        stack.exitScope(); //Ripristino lo scope precedente

        return null;
    }

    @Override
    public Object visit(ParamDeclNode item) {

        for(IdentifierExprNode iden : item.getIdentifierList()){
            if(stack.probe(iden.getValue())){
                throw new MultipleIdentifierDeclaration("Errore in (riga:"+iden.getLeft().getLine()+", colonna: "+iden.getLeft().getColumn()+") -> Variabile "+iden.getValue()+" già dichiarata precedentemente!");
            }
            iden.setType(item.getType());
            iden.accept(this);
        }

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
        item.setType(Symbols.INTEGER);
        return null;
    }

    @Override
    public Object visit(BooleanConstantNode item) {
        item.setType(Symbols.BOOL);
        return null;
    }

    @Override
    public Object visit(RealConstantNode item) {
        item.setType(Symbols.FLOAT);
        return null;
    }

    @Override
    public Object visit(StringConstantNode item) {
        item.setType(Symbols.STRING);
        return null;
    }

    @Override
    public Object visit(CharConstantNode item) {
        item.setType(Symbols.CHAR);
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

        IdSymbol idSym;
        if((idSym = (IdSymbol) stack.lookup(item.getValue(), SymbolTypes.VAR) ) == null)
            throw new RuntimeException("Variabile "+ item.getValue()+ " (riga: "+ item.getLeft().getLine()+ ", colonna: "
                    + item.getRight().getColumn() + " non dichiarata precedentemente");

        item.setType(idSym.getType());


        return null;
    }

    @Override
    public Object visit(UnaryExpressionNode item) {

        item.getRightExpression().accept(this);

        int type = TypeChecker.checkUnaryExpr(item.getOperation(), item.getRightExpression().getType());

        if(type == -1){
            new RuntimeException("Tipo non compatibile!");
        }
        item.setType(type);
        return null;
    }

    @Override
    public Object visit(BinaryExpressionNode item) {

        item.getLeftExpression().accept(this);
        item.getRightExpression().accept(this);

        int type = TypeChecker.checkBinaryExpr(item.getOperation(), item.getLeftExpression().getType(), item.getRightExpression().getType());

        if(type == -1){
           throw new RuntimeException("Operazione tra tipi incompatibili (riga: "+item.getLeft().getLine() +
                    ", colonna: "+ item.getRight().getColumn()+") -> "+
                   terminalNames[item.getLeftExpression().getType()].toLowerCase()+ " e "+terminalNames[item.getRightExpression().getType()].toLowerCase());
        }

        item.setType(type);


        return null;
    }

}
