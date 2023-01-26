package visitor;

import exception.UseOfKeyWord;
import parser.Symbols;
import parser.newLangTree.nodes.*;
import parser.newLangTree.nodes.expression.*;
import parser.newLangTree.nodes.expression.constants.*;
import parser.newLangTree.nodes.statements.*;
import semantic.*;
import exception.MultipleFunctionDeclaration;
import exception.MultipleVariableDeclaration;
import exception.MultipleMainDeclaration;
import semantic.symbols.*;

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

        return null;
    }

    @Override
    public Object visit(DeclNode item) {

        for (VarDeclNode i : item.getVarDeclList())
            i.accept(this);

        for (FunDeclNode i : item.getFunDeclList()){
            if(i.getIdentifier().getValue().equalsIgnoreCase("main")) {
                throw new MultipleMainDeclaration("Errore (riga:"+i.getIdentifier().getLeft().getLine()+", colonna: "+i.getIdentifier().getLeft().getColumn()+") \n-> Non è possibile chiamare una funzione  'main'!");
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
            throw new MultipleVariableDeclaration("Errore (riga: "+item.getIdentifier().getLeft().getLine()+
                    ", colonna: "+ item.getIdentifier().getLeft().getColumn()+") \n->Variabile "+item.getIdentifier().getValue()+
                     " già dichiarata precedentemente! :P");
        }

        //Aggiornamento scope
        stack.addId(new IdSymbol(item.getIdentifier().getValue(), item.getType()));

        return null;
    }

    @Override
    public Object visit(IdInitObbNode item) {
        if (stack.probe(item.getIdentifier().getValue())) {
            throw new MultipleVariableDeclaration("Errore (riga: " + item.getIdentifier().getLeft().getLine() +
                    ", colonna: " + item.getIdentifier().getLeft().getColumn()
                    + " \n-> Variabile " + item.getIdentifier().getValue() + " già dichiarata precedentemente! :P");
        }

        //chiamo il visitor della costante per effettuare l'inferenza di tipo sulla varibile di tipo VAR
        ExpressionNode constant = (ExpressionNode) item.getCostantValue();
        constant.accept(this);

        item.getIdentifier().setType(constant.getType());

        //Aggiorno lo scope
        stack.addId(new IdSymbol(item.getIdentifier().getValue(), constant.getType()));

        return null;
    }

    @Override
    public Object visit(FunDeclNode item) {

        String funName = item.getIdentifier().getValue();
        if(stack.probe(funName)){
            throw new MultipleFunctionDeclaration("Errore (riga:"+item.getIdentifier().getLeft().getLine()+
                    ", colonna: "+item.getIdentifier().getLeft().getColumn()+") \n-> Funzione "+funName+" già dichiarata precedentemente! :P");
        }

        List<ParamFunSymbol> paramList = new LinkedList<>(); // Lista parametri per valore

        for (ParamDeclNode p : item.getParamDecl()){
            for (IdentifierExprNode x : p.getIdentifierList()) { // aggiungiamo tanti tipi quanti gli identificatori
                if(!(p.isOut())){
                    paramList.add(new ParamFunSymbol(x.getValue(), SymbolTypes.VAR, p.getType(), VarTypes.IN));
                } else{
                    paramList.add(new ParamFunSymbol(x.getValue(), SymbolTypes.VAR, p.getType(), VarTypes.OUT));
                }
            }
        }

        // creo il simbolo della funzione

        // aggiungo la firma della funzione a programm;
        stack.addId(new FunSymbol(funName, paramList, item.getTypeOrVoid()));

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
                throw new MultipleVariableDeclaration("Errore (riga:"+iden.getLeft().getLine()
                        +", colonna: "+iden.getLeft().getColumn()+") \n-> Variabile "+iden.getValue()+" già dichiarata precedentemente! :P");
            }
            iden.setPointer(item.isOut()); //Valuto se l'identificatore del parametro è un puntatore
            iden.setParameter(true); //l'identificatore viene sfruttato come parametro della funzione
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

        //Ripristino lo scope padre
        stack.exitScope();

        if(item.getBodyElse() != null) {

            //Cambio scope di nuovo, devo conservarlo e aggiorno lo scope
            stack.enterScope(item.getSymbolTableElseScope());

            item.getBodyElse().accept(this);
            stack.exitScope(); //Ripristino lo scope

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
        stack.addId(new IdSymbol(item.getValue(), item.getType(), item.isPointer(), item.isParameter()));
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
    public Object visit(InitLoopCondNode item) {
        return null;
    }

    @Override
    public Object visit(InitLoopNode item) {
        if(item.getIdInitNodeList() != null) {
            for (IdInitNode idNode : item.getIdInitNodeList()) {
                idNode.setType(item.getType());
                idNode.accept(this);
            }
        }

        return null;
    }

    @Override
    public Object visit(InitLoopStepNode item) {
        return null;
    }

    @Override
    public Object visit(InitDoForStepNode item) {
        //Cambio scope, quindi aggiorno quello corrente
        stack.enterScope(item.getSymbolTableInDoForStepScope());
        item.getInitLoop().accept(this);

        //Ristabilire lo stack del padre
        stack.exitScope(); //Ripristino lo scope padre
        return null;
    }
}
