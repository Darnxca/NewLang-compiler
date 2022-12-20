package visitor;

import parser.Symbols;
import parser.newLangTree.nodes.*;
import parser.newLangTree.nodes.expression.*;
import parser.newLangTree.nodes.expression.constants.*;
import parser.newLangTree.nodes.statements.*;
import semantic.SymbolTableStack;
import semantic.SymbolTypes;
import semantic.symbols.IdSymbol;

import java.util.LinkedList;

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

        return null;
    }

    @Override
    public Object visit(DeclNode item) {

        for (VarDeclNode i : item.getVarDeclList())
            i.accept(this);

        for (FunDeclNode i : item.getFunDeclList()){
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
            i.accept(this);
        }

        for (IdInitObbNode i : item.getIdIObList())
            i.accept(this);

        return null;
    }

    @Override
    public Object visit(IdInitNode item) {

        item.getIdentifier().accept(this);

        if(item.getExpression() != null){
            item.getExpression().accept(this);

            int type = TypeChecker.checkBinaryExpr(Symbols.ASSIGN, item.getIdentifier().getType(), item.getExpression().getType());
            if(type == -1) {

                throw new RuntimeException("Assegnazione tra tipi incompatibili (riga: "+item.getExpression().getLeft().getLine() +
                        ", colonna: "+ item.getExpression().getRight().getColumn()+") -> "+
                        terminalNames[item.getIdentifier().getType()].toLowerCase()+ " e "+ terminalNames[item.getExpression().getType()].toLowerCase() );
            }

            item.setType(type);

        }

        item.setType(item.getIdentifier().getType());

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

        // controlliamo se tutti i possibili return presenti nella funzione sono compatibili con il suo tipo di ritorno
        for (StatementNode x : item.getBody().getStmtNodeList()){
                if (!TypeChecker.checkAllTypeReturn(x, item.getTypeOrVoid())) {
                    throw new RuntimeException("Tipo di ritorno e tipo della funzione non coincidono");
                }
        }

        stack.exitScope(); //Ripristino lo scope precedente

        return null;
    }

    @Override
    public Object visit(ParamDeclNode item) {

        for(IdentifierExprNode iden : item.getIdentifierList()){
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

        item.getExpression().accept(this);

        if(item.getExpression().getType() != Symbols.BOOL){
            throw new RuntimeException("Errore (riga: "+item.getExpression().getLeft().getLine()+
                    ", colonna: "+item.getExpression().getRight().getColumn()+ " ) -> Espressione inserita di tipo " +
                    terminalNames[item.getExpression().getType()].toLowerCase()+ ", l'if si aspetta un tipo boolean!" +
                    "!");
        }

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
        int numIden = item.getIdentifierList().size();
        int numExpr = item.getExpressionList().size();

        if(numIden > numExpr){
            throw new RuntimeException("Errore assegnazione: assegnazione tra "+ numIden+  " identificatori e "+ numExpr+" espressioni " +
                    " (riga :" +item.getIdentifierList().get(0).getLeft().getLine()+" colonna :"+item.getExpressionList().get(numExpr-1).getRight().getColumn()+")");
        } else if(numIden < numExpr){
            throw new RuntimeException("Errore assegnazione: assegnazione tra "+ numIden+  " identificatori e "+ numExpr+" espressioni " +
                    " (riga :" +item.getIdentifierList().get(0).getLeft().getLine()+" colonna :"+item.getIdentifierList().get(numIden-1).getRight().getColumn()+")");
        }

        LinkedList<Integer> tipiIden = new LinkedList<>(); // lista che contiene i tipi degli identificatori
        LinkedList<Integer> tipiExpr = new LinkedList<>(); // lista che contiene i tipi delle espressioni
        LinkedList<IdentifierExprNode> errorIden = new LinkedList<>(); // lista usata per salvarsi temporaneamente gli identificatori
        LinkedList<ExpressionNode> errorExpr = new LinkedList<>(); // lista usata per salvarsi temporaneamente le espressioni

        // ottenimento tipo degli identificatori
        for (IdentifierExprNode iden: item.getIdentifierList()){
            iden.accept(this);
            tipiIden.add(iden.getType());
            errorIden.add(iden);
        }

        // ottenimento tipo delle espressioni
        for (ExpressionNode expr : item.getExpressionList()){
            expr.accept(this);
            tipiExpr.add(expr.getType());
            errorExpr.add(expr);
        }

        // controllo corrispondenza tra tipi
        for(int i = 0; i < tipiIden.size(); i ++){
            if (TypeChecker.checkBinaryExpr(Symbols.ASSIGN,tipiIden.get(i), tipiExpr.get(i)) == -1){
                throw  new RuntimeException("Tipo non corrispondente in (riga :"+errorIden.get(i).getLeft().getLine()+", colonna: "+
                        errorIden.get(i).getLeft().getColumn() + ") e (riga:"+ errorExpr.get(i).getLeft().getLine()+", colonna: "+
                        errorExpr.get(i).getLeft().getColumn() + ") -> "+ terminalNames[tipiIden.get(i)] + " e " + terminalNames[tipiExpr.get(i)]);
            }
        }

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

        for(IdentifierExprNode iden : item.getIdentifierList())
            iden.accept(this);

        return null;
    }

    @Override
    public Object visit(ReturnStatNode item) {

        if (item.getExpression() != null) {
            System.out.println();
            item.getExpression().accept(this);
            item.setType(item.getExpression().getType());
        }else {
            item.setType(Symbols.VOID);
        }


        return null;
    }

    @Override
    public Object visit(WhileStatNode item) {

        //Cambio scope, quindi aggiorno quello corrente
        stack.enterScope(item.getSymbolTableWhile());

        item.getExpression().accept(this);

        if(item.getExpression().getType() != Symbols.BOOL){
            throw new RuntimeException("Errore (riga: "+item.getExpression().getLeft().getLine()+
                    ", colonna: "+item.getExpression().getRight().getColumn()+ " ) -> Espressione inserita di tipo " +
                    terminalNames[item.getExpression().getType()].toLowerCase()+ ", il while si aspetta un tipo boolean!" +
                    "!");
        }

        item.getBody().accept(this);

        //Ristabilire lo stack del padre
        stack.exitScope(); //Ripristino lo scope padre

        return null;
    }

    @Override
    public Object visit(WriteStatNode item) {

        for (ExpressionNode e: item.getExpressionList())
            e.accept(this);

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
