package visitor;

import parser.Symbols;
import parser.newLangTree.*;
import parser.newLangTree.expression.*;
import parser.newLangTree.expression.constants.*;
import parser.newLangTree.statements.*;
import semantic.Symbol;
import semantic.SymbolTable;
import semantic.SymbolTypes;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ScopeVisitor implements Visitor{

    private SymbolTable currentScope, prevScope;

    public ScopeVisitor() {
        currentScope = new SymbolTable();
        prevScope = new SymbolTable();
    }

    @Override
    public Object visit(ProgramNode item) throws Exception {
        currentScope = item.getSymbolTableProgramScope();

        item.getDecl().accept(this);

        item.getMainFuncDecl().accept(this);

        System.out.println("PROGRAM: \n"+item.getSymbolTableProgramScope().toString());

        return null;
    }

    @Override
    public Object visit(DeclNode item) throws Exception {

        for (VarDeclNode i : item.getVarDeclList())
            i.accept(this);

        for (FunDeclNode i : item.getFunDeclList())
            i.accept(this);

        return null;
    }

    @Override
    public Object visit(MainFuncDeclNode item) throws Exception {

        item.getFunDeclNode().accept(this);

        return null;
    }

    @Override
    public Object visit(VarDeclNode item) throws Exception {

        for (IdInitNode i : item.getIdIList()) {
            i.setType(item.getType().get(0));
            i.accept(this);
        }

        for (IdInitObbNode i : item.getIdIObList())
            i.accept(this);

        return null;
    }

    @Override
    public Object visit(IdInitNode item) throws Exception {
        currentScope.put(item.getIdentifier().getValue(), new Symbol(item.getIdentifier().getValue(), SymbolTypes.VAR, item.getType().get(0)));
        return null;
    }

    @Override
    public Object visit(IdInitObbNode item) throws Exception {

        //chiamo il visitor della costante per effettuare l'inferenza di tipo sulla varibile di tipo VAR
        ExpressionNode constant = (ExpressionNode) item.getCostantValue();
        constant.accept(this);

        currentScope.put(item.getIdentifier().getValue(), new Symbol(item.getIdentifier().getValue(), SymbolTypes.VAR, constant.getType().get(0) ));
        return null;
    }

    @Override
    public Object visit(FunDeclNode item) throws Exception {

        String funName = item.getIdentifier().getValue();


        List<Integer> paramTypeListIN = new LinkedList<>(); // Lista parametri per valore
        List<Integer> paramTypeListOUT = new LinkedList<>(); // Lista parametri per riferimento

        for (ParamDeclNode p : item.getParamDecl()){
            if(!(p.isOut())){
                for (IdentifierExprNode x : p.getIdentifierList()) // aggiungiamo tanti tipi qunati gli identificatori
                    paramTypeListIN.add(p.getType().get(0));
            } else{
                for (IdentifierExprNode x : p.getIdentifierList())
                    paramTypeListOUT.add(p.getType().get(0));
            }
        }

        // creo il simbolo della funzione
        Symbol functionSymbol = new Symbol(funName, paramTypeListIN, paramTypeListOUT, item.getTypeOrVoid());

        // aggiungo la firma della funzione a programm;
        currentScope.put(funName, functionSymbol);

        prevScope = currentScope;

        currentScope = item.getSymbolTableFunScope();

        /* visito il body per aggiornare la tabella dei simboli della funzione*/

        for (ParamDeclNode param : item.getParamDecl())
            param.accept(this);


        item.getBody().accept(this);

        System.out.println("Function "+funName+":\n"+item.getSymbolTableFunScope().toString());
        /* ritorno allo scope padre*/
        currentScope = prevScope;

        return null;
    }

    @Override
    public Object visit(ParamDeclNode item) throws Exception {

        for(IdentifierExprNode iden : item.getIdentifierList()){
            iden.setType(item.getType().get(0));
            iden.accept(this);
        }

        return null;
    }

    @Override
    public Object visit(BodyNode item) throws Exception {

        for(VarDeclNode vd : item.getVarDeclList()){
            vd.accept(this);
        }

        //TODO: StatList

        return null;
    }

    @Override
    public Object visit(IfStatNode item) throws Exception {
        return null;
    }

    @Override
    public Object visit(AssignStatNode item) throws Exception {
        return null;
    }

    @Override
    public Object visit(ForStatNode item) throws Exception {
        return null;
    }

    @Override
    public Object visit(ReadStatNode item) throws Exception {
        return null;
    }

    @Override
    public Object visit(ReturnStatNode item) throws Exception {
        return null;
    }

    @Override
    public Object visit(WhileStatNode item) throws Exception {
        return null;
    }

    @Override
    public Object visit(WriteStatNode item) throws Exception {
        return null;
    }

    @Override
    public Object visit(IntegerConstantNode item) throws Exception {
        item.setType(Symbols.INTEGER);
        return null;
    }

    @Override
    public Object visit(BooleanConstantNode item) throws Exception {
        item.setType(Symbols.BOOL);
        return null;
    }

    @Override
    public Object visit(RealConstantNode item) throws Exception {
        item.setType(Symbols.FLOAT);
        return null;
    }

    @Override
    public Object visit(StringConstantNode item) throws Exception {
        item.setType(Symbols.STRING);
        return null;
    }

    @Override
    public Object visit(CharConstantNode item) throws Exception {
        item.setType(Symbols.CHAR);
        return null;
    }

    @Override
    public Object visit(FunCallExprNode item) throws Exception {
        return null;
    }

    @Override
    public Object visit(FunCallStatNode item) throws Exception {
        return null;
    }

    @Override
    public Object visit(IdentifierExprNode item) throws Exception {
        currentScope.put(item.getValue(), new Symbol(item.getValue(),SymbolTypes.VAR, item.getType().get(0)));
        return null;
    }

    @Override
    public Object visit(UnaryExpressionNode item) throws Exception {
        return null;
    }

    @Override
    public Object visit(BinaryExpressionNode item) throws Exception {
        return null;
    }
}
