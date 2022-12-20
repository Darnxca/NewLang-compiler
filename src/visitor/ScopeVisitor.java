package visitor;

import parser.Symbols;
import parser.newLangTree.nodes.*;
import parser.newLangTree.nodes.expression.*;
import parser.newLangTree.nodes.expression.constants.*;
import parser.newLangTree.nodes.statements.*;
import semantic.*;
import semantic.exception.MultipleIdentifierDeclaration;
import semantic.exception.MultipleMainDeclaration;
import semantic.symbols.FunSymbol;
import semantic.symbols.IdSymbol;

import java.util.LinkedList;
import java.util.List;

public class ScopeVisitor implements Visitor{

    private SymbolTableStack stack;

    public ScopeVisitor() {
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

        for (FunDeclNode i : item.getFunDeclList()){
            if(i.getIdentifier().getValue().equalsIgnoreCase("main")) {
                throw new MultipleMainDeclaration("Errore in (riga:"+i.getIdentifier().getLeft().getLine()+", colonna: "+i.getIdentifier().getLeft().getColumn()+") -> Non è possibile chiamare una funzione  'main'!");
            }
                i.accept(this);
           }


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
            i.setType(item.getType());
            i.accept(this);
        }

        for (IdInitObbNode i : item.getIdIObList())
            i.accept(this);

        return null;
    }

    @Override
    public Object visit(IdInitNode item) {
        if(stack.probe(item.getIdentifier().getValue())){
            throw new MultipleIdentifierDeclaration("Variabile "+item.getIdentifier().getValue()+ " (riga:"+item.getIdentifier().getLeft().getLine()+ ", colonna:"+ item.getIdentifier().getLeft().getColumn()+ ") già dichiarata precedentemente!");
        }

       //Prelevo lo scope precedente e lo aggiorno
        stack.addId(new IdSymbol(item.getIdentifier().getValue(), item.getType()));

        return null;
    }

    @Override
    public Object visit(IdInitObbNode item) {
        if(stack.probe(item.getIdentifier().getValue())){
            throw new RuntimeException("Variabile "+item.getIdentifier().getValue()+ " (riga:"+item.getIdentifier().getLeft().getLine()+ ", colonna:"+ item.getIdentifier().getLeft().getColumn()+ ") già dichiarata precedentemente!");
        }

        //chiamo il visitor della costante per effettuare l'inferenza di tipo sulla varibile di tipo VAR
        ExpressionNode constant = (ExpressionNode) item.getCostantValue();
        constant.accept(this);

        item.getIdentifier().setType(constant.getType());

        //Aggiorno scope
        stack.addId(new IdSymbol(item.getIdentifier().getValue(), constant.getType()));

        return null;
    }

    @Override
    public Object visit(FunDeclNode item) {

        String funName = item.getIdentifier().getValue();
        if(stack.probe(funName)){
            throw new RuntimeException("Errore in (riga:"+item.getIdentifier().getLeft().getLine()+", colonna: "+item.getIdentifier().getLeft().getColumn()+") -> Funzione "+funName+" già dichiarata precedentemente!");
        }

        List<Integer> paramTypeList = new LinkedList<>(); // Lista parametri per valore
        List<Integer> paramType = new LinkedList<>(); // Lista parametri per riferimento

        for (ParamDeclNode p : item.getParamDecl()){
            if(!(p.isOut())){
                for (IdentifierExprNode x : p.getIdentifierList()) { // aggiungiamo tanti tipi quanti gli identificatori
                    paramTypeList.add(p.getType());
                    paramType.add(VarTypes.IN);
                }
            } else{
                for (IdentifierExprNode x : p.getIdentifierList()) {
                    paramTypeList.add(p.getType());
                    paramType.add(VarTypes.OUT);
                }
            }
        }

        // creo il simbolo della funzione

        // aggiungo la firma della funzione a programm;
        stack.addId(new FunSymbol(funName, paramTypeList, paramType, item.getTypeOrVoid()));

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

        //Salvataggio e aggiornamento scope corrente
        stack.addId(new IdSymbol(item.getValue(), item.getType()));
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
