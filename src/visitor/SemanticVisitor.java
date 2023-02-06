package visitor;

import parser.Symbols;
import parser.newLangTree.nodes.*;
import parser.newLangTree.nodes.expression.*;
import parser.newLangTree.nodes.expression.constants.*;
import parser.newLangTree.nodes.statements.*;
import semantic.SymbolTableStack;
import semantic.symbols.SymbolTypes;
import exception.*;
import exception.TypeMismatch;
import semantic.symbols.FunSymbol;
import semantic.symbols.IdSymbol;
import semantic.utils.TypeChecker;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

import static parser.Symbols.terminalNames;

public class SemanticVisitor implements Visitor{

    private SymbolTableStack stack;

    public SemanticVisitor() {
        stack = new SymbolTableStack();
    }

    @Override
    public Object visit(ProgramNode item) {

        stack.enterScope(item.getSymbolTableProgramScope()); //Aggiorno lo stack con lo scope di program

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

            //Check tipo dell'assegnazione
            int type = TypeChecker.checkBinaryExpr(Symbols.ASSIGN, item.getIdentifier().getType(), item.getExpression().getType());
            if(type == -1) {

                throw new TypeMismatch("Errore (riga: "+item.getExpression().getLeft().getLine() +
                        " , colonna: "+ item.getExpression().getRight().getColumn()+")"+
                        "\n-> Assegnazione tra tipi incompatibili  ("+ terminalNames[item.getIdentifier().getType()].toLowerCase()
                        + " e "+ terminalNames[item.getExpression().getType()].toLowerCase() +")" );
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

        /* Controlliamo se tutti i possibili return presenti negli statement della funzione sono compatibili
         * con il tipo di ritorno della funzione stessa.
         * */
        int countReturn = 0;

        for (StatementNode x : item.getBody().getStmtNodeList()){
            if (x instanceof ReturnStatNode) countReturn++;
            if (!TypeChecker.checkAllTypeReturn(x, item.getTypeOrVoid())) {
                throw new TypeMismatch("Preparatevi a passare dei guai, dei guai molto grossi.." +
                        "(riga: "+ x.getLeft().getLine()
                        + ", colonna: "+ x.getRight().getColumn()+ ")"+
                        " \n-> Tipo di ritorno e tipo della funzione non coincidono ");
            }
        }


        if(countReturn == 0 && item.getTypeOrVoid() != Symbols.VOID ){ //Check return obbligatorio in funzioni non VOID
            throw new MissinReturnInBodyFunction("Preparatevi a passare dei guai, dei guai molto grossi.."+
                    " \n-> Manca il return all'interno del corpo principale della funzione "+ item.getIdentifier().getValue() + "!!");
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

        //Qui valuto in automatico se le varibili dell'espressione sono dichiarate nello scope attuale
        item.getExpression().accept(this);

        //Check tipo
        if(item.getExpression().getType() != Symbols.BOOL){
            throw new TypeMismatch("Errore (riga: "+item.getExpression().getLeft().getLine()+
                    ", colonna: "+item.getExpression().getRight().getColumn()+ ") " +
                    "\n-> Espressione inserita di tipo " +
                    terminalNames[item.getExpression().getType()].toLowerCase()+
                    ", l'if si aspetta un tipo boolean!!");
        }

        //Lo scope attuale ora sarà quello dell'if
        stack.enterScope(item.getSymbolTableIfScope());

        item.getBodyThen().accept(this);

        //Ripristino lo scope padre
        stack.exitScope();

        if(item.getBodyElse() != null) {
            //Cambio scope di nuovo, devo conservarlo
            stack.enterScope(item.getSymbolTableElseScope());
            item.getBodyElse().accept(this);
            stack.exitScope(); //Ripristino lo scope precedente
        }

        return null;
    }

    @Override
    public Object visit(AssignStatNode item) {

        int numIden = item.getIdentifierList().size(); //Recupero quante variabili ho a sinistra
        int numExpr = item.getExpressionList().size(); //Recupero quante espressioni ho a destra

        //Se ho più variabili a sinistra -> integer x, y << 3 [è un errore]
        //Se ho più espressioni a destra -> integer x << 3,6 [è un errore]
        if(numIden > numExpr){
            throw new MissingAssignArguments("Errore (riga : "+item.getIdentifierList().get(0).getLeft().getLine()+
                    ", colonna :"+item.getExpressionList().get(numExpr-1).getRight().getColumn()+") " +
                    "\n->Assegnazione tra "+ numIden+  " identificatore/i e "+ numExpr+" espressione/i ");
        }
        else if(numIden < numExpr){
            throw new MissingAssignArguments("Errore (riga : "+item.getIdentifierList().get(0).getLeft().getLine()+
                    ", colonna :"+item.getIdentifierList().get(numIden-1).getRight().getColumn()+") "+
                    "\n-> Assegnazione tra "+ numIden+  " identificatore/i e "+ numExpr+" espressione/i ");
        }

        LinkedList<Integer> tipiIden = new LinkedList<>(); // lista che contiene i tipi degli identificatori
        LinkedList<Integer> tipiExpr = new LinkedList<>(); // lista che contiene i tipi delle espressioni
        LinkedList<IdentifierExprNode> errorIden = new LinkedList<>(); // lista usata per salvarsi temporaneamente gli identificatori
        LinkedList<ExpressionNode> errorExpr = new LinkedList<>(); // lista usata per salvarsi temporaneamente le espressioni

        // Recupero tipo degli identificatori
        for (IdentifierExprNode iden: item.getIdentifierList()){
            iden.accept(this);
            tipiIden.add(iden.getType());
            errorIden.add(iden);
        }

        // Recupero tipo delle espressioni
        for (ExpressionNode expr : item.getExpressionList()){
            expr.accept(this);
            tipiExpr.add(expr.getType());
            errorExpr.add(expr);
        }

        // Controllo corrispondenza tra tipi secondo le regole
        for(int i = 0; i < tipiIden.size(); i ++){
            if (TypeChecker.checkBinaryExpr(Symbols.ASSIGN,tipiIden.get(i), tipiExpr.get(i)) == -1){
                throw  new TypeMismatch("Errore (riga :"+errorIden.get(i).getLeft().getLine()+
                        ", colonna: " + errorIden.get(i).getLeft().getColumn() + ")"+
                        "\n ->Tipo non corrispondente "+ terminalNames[tipiIden.get(i)].toLowerCase() +
                        " e " + terminalNames[tipiExpr.get(i)].toLowerCase());
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
            item.getExpression().accept(this);
            item.setType(item.getExpression().getType());
        }else {
            item.setType(Symbols.VOID);
        }


        return null;
    }

    @Override
    public Object visit(WhileStatNode item) {

        //Qui valuto in automatico se le varibili dell'espressione sono dichiarate nello scope attuale
        item.getExpression().accept(this);
        if(item.getExpression().getType() != Symbols.BOOL){ //Check tipo
            throw new TypeMismatch("Errore (riga: "+item.getExpression().getLeft().getLine()+
                    ", colonna: "+item.getExpression().getRight().getColumn()+ " ) " +
                    "\n-> Espressione inserita di tipo " +
                    terminalNames[item.getExpression().getType()].toLowerCase()+ ", il while si aspetta un tipo boolean!!");
        }

        //Cambio di scope.. quello attuale sarà del while
        stack.enterScope(item.getSymbolTableWhile());

        item.getBody().accept(this);

        stack.exitScope(); //Ripristino dello scope precedente

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

        //Controllo nello scope precedente se la funzione da utilizzare è stata dichiarata
        FunSymbol function;
        if((function = (FunSymbol) stack.lookup(item.getIdentifier().getValue(), SymbolTypes.FUNCTION)) == null)
            throw new FunctionNotDeclared("Errore (riga: "+ item.getLeft().getLine()+
                    ", colonna: " + item.getRight().getColumn() + ")"+
                    "\n-> Funzione "+ item.getIdentifier().getValue()+  " non dichiarata");

        LinkedList<Integer> paramType = new LinkedList<>();
        LinkedList<ExpressionNode> expressionType = new LinkedList<>();
        for (ExpressionNode x : item.getListOfExpr()){
            x.accept(this);
            paramType.add(x.getType());
            expressionType.add(x);
        }

        //Controllo se nella funzione chiamata i parametri attuali inseriti sono uguali ai formali
        if(paramType.size() != function.getListOfParams().size()){
            throw new MissingParametersFunction("Errore (riga :" +item.getIdentifier().getLeft().getLine()  +
                    ", colonna :" + item.getIdentifier().getRight().getColumn()+ ")"+
                    "\n-> Parametri della funzione mancanti ");
        }


        for (int i =0; i < paramType.size(); i++){
            //Check tipo parametri
            if(!TypeChecker.checkCallParamTypes(paramType.get(i), function.getListOfParams().get(i).getPrimitiveTypeOfParam())){
                throw new TypeMismatch("Errore ( riga :"+item.getIdentifier().getLeft().getLine()+
                        ", colonna :" + item.getIdentifier().getRight().getColumn()+ ")" +
                        "\n-> Tipo dei parametri della funzione " +item.getIdentifier().getValue() + " non corrisponde" );
            }
            /*Controllo se ad una variabile di tipo out è stato assegnato un valore costante
             * somma(integer a,b | float out result) ... somma(4, 5, 6) -> Non valido
             * somma(integer a,b | float out result) ... somma(4, 5, v) -> Valido
             */
            if ( (expressionType.get(i) instanceof Constant) && function.getListOfParams().get(i).isOut()){
                throw new TypeMismatch("Errore ( riga :"+ expressionType.get(i).getLeft().getLine()+
                        ", colonna :" + expressionType.get(i).getRight().getColumn() + ")" +
                        "\n-> Non si può assegnare una costante ad un variabile di tipo out!");
            }
        }

        item.setType(function.getReturnType());


        return null;
    }

    @Override
    public Object visit(FunCallStatNode item) {

        //Controllo nello scope precedente se la funzione da utilizzare è stata dichiarata
        FunSymbol function;
        if((function = (FunSymbol) stack.lookup(item.getIdentifier().getValue(), SymbolTypes.FUNCTION)) == null)
            throw new FunctionNotDeclared("Errore (riga: "+ item.getLeft().getLine()+
                    ", colonna: "+ item.getRight().getColumn()+
                    ")\n-> Funzione "+ item.getIdentifier().getValue()+ " non dichiarata");

        LinkedList<Integer> paramType = new LinkedList<>();
        LinkedList<ExpressionNode> expressionType = new LinkedList<>();
        for (ExpressionNode x : item.getListOfExpr()){
            x.accept(this);
            paramType.add(x.getType());
            expressionType.add(x);
        }

        //Controllo se nella funzione chiamata i parametri attuali inseriti sono uguali ai formali
        if(paramType.size() != function.getListOfParams().size()){
            throw new MissingParametersFunction("Errore (riga :" +item.getIdentifier().getLeft().getLine() +
                    ", colonna :" + item.getIdentifier().getRight().getColumn()+")"
                    + "\n->Parametri della funzione mancanti" );
        }

        for (int i =0; i < paramType.size(); i++){
            //Check tipo parametri
            if(!TypeChecker.checkCallParamTypes(paramType.get(i), function.getListOfParams().get(i).getPrimitiveTypeOfParam())){
                throw new TypeMismatch("Errore ( riga :"+item.getIdentifier().getLeft().getLine()+
                        " colonna :" + item.getIdentifier().getRight().getColumn()+
                        ")\n->Tipo dei parametri della funzione " +item.getIdentifier().getValue() + " non corrisponde" );
            }
            /*Controllo se ad una variabile di tipo out è stato assegnato un valore costante
             * somma(integer a,b | float out result) ... somma(4, 5, 6) -> Non valido
             * somma(integer a,b | float out result) ... somma(4, 5, v) -> Valido
             */
            if ( (expressionType.get(i) instanceof Constant) && function.getListOfParams().get(i).isOut()){
                throw new TypeMismatch("Errore ( riga : "+ expressionType.get(i).getLeft().getLine() +
                        ", colonna :" + expressionType.get(i).getRight().getColumn() +
                        ")\n-> Non si può assegnare una costante ad un variabile di tipo out ");
            }

            if ( !(expressionType.get(i) instanceof IdentifierExprNode) && function.getListOfParams().get(i).isOut()){
                throw new TypeMismatch("Errore ( riga : "+ expressionType.get(i).getLeft().getLine() +
                        ", colonna :" + expressionType.get(i).getRight().getColumn() +
                        "\n-> Non si può assegnare un'espessione ad una variabile di tipo out ");
            }
        }

        item.setType(function.getReturnType());

        return null;
    }

    @Override
    public Object visit(IdentifierExprNode item) {

        //Controllo se l'identificatore è presente nello scope
        IdSymbol idSym;
        if((idSym = (IdSymbol) stack.lookup(item.getValue(), SymbolTypes.VAR) ) == null)
            throw new VariableNotDeclared("Errore (riga: "+ item.getLeft().getLine()+
                    ", colonna: "+ item.getRight().getColumn()+ ")"+
                    "\n->Variabile "+ item.getValue()+ " non dichiarata precedentemente!");

        item.setType(idSym.getType());


        return null;
    }

    @Override
    public Object visit(UnaryExpressionNode item) {

        item.getRightExpression().accept(this);

        // Check tipo despressione unaria
        int type = TypeChecker.checkUnaryExpr(item.getOperation(), item.getRightExpression().getType());

        if(type == -1){
            throw new TypeMismatch("Errore (riga: "+ item.getLeft().getLine() +
                    ", colonna: "+ item.getRight().getColumn()+ ")"
                    +"\n->Attenzione! Tipo nell'espressione non compatibile!");
        }
        item.setType(type);
        return null;
    }

    @Override
    public Object visit(BinaryExpressionNode item) {

        item.getLeftExpression().accept(this);
        item.getRightExpression().accept(this);

        //Check tipo espressione binaria
        int type = TypeChecker.checkBinaryExpr(item.getOperation(), item.getLeftExpression().getType(), item.getRightExpression().getType());

        if(type == -1){
            throw new TypeMismatch("Errore  (riga: "+item.getLeft().getLine()
                    +", colonna: "+ item.getRight().getColumn()+")"+
                    "\n->Operazione tra tipi incompatibili ("+
                    terminalNames[item.getLeftExpression().getType()].toLowerCase()+
                    " e "+terminalNames[item.getRightExpression().getType()].toLowerCase()+")");
        }

        item.setType(type);


        return null;
    }

    @Override
    public Object visit(MunnezzStatNode item) {

        item.getExprCond().accept(this);
        //La condizione dello switch può essere solo di tipo intero o char
        if(item.getExprCond().getType() != Symbols.INTEGER && item.getExprCond().getType() != Symbols.CHAR){
            throw new TypeMismatch("Errore  (riga: "+item.getExprCond().getLeft().getLine()
                    +", colonna: "+ item.getExprCond().getRight().getColumn()+")"+
                    "\n-> Il tipo di condizione inserito nello switch è "+
                    terminalNames[item.getExprCond().getType()].toLowerCase()+
                    ", mentre lo switch può avere solo tipo intero o char");
        }

        if(item.getCaseStatNodeList() != null){
            for(AsfnazzaCaseStatNode as : item.getCaseStatNodeList()) {
                as.accept(this);
            }

            for(AsfnazzaCaseStatNode as : item.getCaseStatNodeList()) {
                if(as.getExprCase().getType() != item.getExprCond().getType()){
                    throw new TypeMismatch("Errore (riga: "+as.getExprCase().getLeft().getLine()+
                            ", colonna: "+as.getExprCase().getRight().getColumn()+ ") " +
                            "\n-> Espressione inserita di tipo " +
                            terminalNames[as.getExprCase().getType()].toLowerCase()+
                            ", il case deve avere la stessa condizione dello switch!!");
                }

            }
        }

        return null;
    }

    @Override
    public Object visit (AsfnazzaCaseStatNode item) {

        item.getExprCase().accept(this);
        for(StatementNode st : item.getStatementNodeList())
            st.accept(this);

        return null;
    }

    @Override
    public Object visit(StepNode stepNode) {
        if(stepNode.getExprList() != null){
            for(ExpressionNode e : stepNode.getExprList())
                e.accept(this);
        }
        return null;
    }

    @Override
    public Object visit(InitNode initNode) {
        if(initNode.getInitExpr() != null)
            initNode.getInitExpr().accept(this);
        return null;
    }

    @Override
    public Object visit(InitDoForStepNode initDoForStepNode) {

        stack.enterScope(initDoForStepNode.getSymbolTableInitDoForStep());
        initDoForStepNode.getInit().accept(this);

        if(initDoForStepNode.getStatList() != null){
            for (StatementNode s: initDoForStepNode.getStatList())
                s.accept(this);
        }

        initDoForStepNode.getForStep().accept(this);
        initDoForStepNode.getStep().accept(this);

        stack.exitScope();


        return null;
    }

    @Override
    public Object visit(ForStepNode forStepNode) {

        if (forStepNode.getExprLoop() != null)
            forStepNode.getExprLoop().accept(this);

        return null;
    }

}
