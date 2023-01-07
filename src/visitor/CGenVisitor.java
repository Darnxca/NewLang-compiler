package visitor;

import parser.Symbols;
import parser.newLangTree.nodes.*;
import parser.newLangTree.nodes.expression.*;
import parser.newLangTree.nodes.expression.constants.*;
import parser.newLangTree.nodes.statements.*;
import semantic.SymbolTableStack;
import semantic.symbols.SymbolTypes;
import semantic.symbols.FunSymbol;
import semantic.symbols.IdSymbol;
import semantic.symbols.ParamFunSymbol;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class CGenVisitor implements Visitor{

    private SymbolTableStack stack;
    private static PrintWriter writer;

    public CGenVisitor(String filename){
        try {
            stack = new SymbolTableStack();
            writer = new PrintWriter( "CodiciC/"+filename+".c");
            generaLibrerie();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void flush() {
        writer.flush();
        writer.close();
    }



    @Override
    public Object visit(ProgramNode item) {

        stack.enterScope(item.getSymbolTableProgramScope());

        writer.println("// prototipi delle funzioni");
        item.getSymbolTableProgramScope().forEach((key, sym) ->{
            if(sym instanceof FunSymbol){
                FunSymbol fs = (FunSymbol) sym;
                generaPrototipoFunzione(fs);
            }
        });

        writer.println("");
        item.getDecl().accept(this);

        item.getMainFuncDecl().accept(this);

        return null;
    }

    @Override
    public Object visit(DeclNode item) {

        writer.println("// inizializzazione delle variabili");

        ArrayList<VarDeclNode> vd = ordinaVarDecl(item.getVarDeclList());
        for (VarDeclNode var : vd){
            var.accept(this);
        }

        writer.println("\n//-----------Implementazione funzioni-----------");

        for (FunDeclNode fd : item.getFunDeclList()){
            fd.accept(this);
        }


        return null;
    }


    @Override
    public Object visit(MainFuncDeclNode item) {

        String nomeFunz = item.getFunDeclNode().getIdentifier().getValue();

        if(!nomeFunz.equals("main")){
            writer.println("int main(int argc, char *argv[]){");

            FunSymbol function = (FunSymbol) stack.lookup(nomeFunz, SymbolTypes.FUNCTION);

            List<ParamFunSymbol> parametriFormali =  function.getListOfParams();


            /* dichiariamo gli eventuali parametri del main*/
            for (int i =0; i < parametriFormali.size(); i++) {
                ParamFunSymbol paramFunSymbol = parametriFormali.get(i);
                IdSymbol idSymbol = new IdSymbol(paramFunSymbol.getIdentifier(),paramFunSymbol.getPrimitiveTypeOfParam());
                writer.print("\t");
                generaDichiarazioneVariabile(idSymbol);
            }

            writer.print("\t");
            writer.print(function.getIdentifier()+"(");

            for (int i =0; i < parametriFormali.size(); i++) {
                if (parametriFormali.get(i).isOut())
                    writer.print("&");

                writer.print(parametriFormali.get(i).getIdentifier());

                if (i != parametriFormali.size()-1)
                    writer.print(", ");
            }

            writer.println(");");
            writer.println("}");

        }

        item.getFunDeclNode().accept(this);


        return null;
    }

    @Override
    public Object visit(VarDeclNode item) {


        for (IdInitNode id : item.getIdIList()){
            inserisciTab();
                writer.print(getTypeFromToken(id.getType())+ " ");
                id.accept(this);
        }

        for (IdInitObbNode id : item.getIdIObList()){
            inserisciTab();
            writer.print(getTypeFromToken(id.getType())+ " ");
            id.accept(this);
        }

        return null;
    }

    @Override
    public Object visit(IdInitNode item) {

        item.getIdentifier().accept(this);
        if (item.getExpression() != null){
            writer.print(" = ");
            item.getExpression().accept(this);
            writer.println(";");
        }else
            writer.println(";");

        return null;
    }

    @Override
    public Object visit(IdInitObbNode item) {

        item.getIdentifier().accept(this);
        writer.print(" = ");
        ExpressionNode costantValue = (ExpressionNode) item.getCostantValue();
        costantValue.accept(this);
        writer.println(";");

        return null;
    }

    @Override
    public Object visit(FunDeclNode item) {


        stack.enterScope(item.getSymbolTableFunScope());

        writer.print(getTypeFromToken(item.getTypeOrVoid())+ " ");

        item.getIdentifier().accept(this);

        writer.print(" (");

        for (int i= 0; i< item.getParamDecl().size(); i++){
            ParamDeclNode pd = item.getParamDecl().get(i);

            if(i != item.getParamDecl().size()-1){
                pd.accept(this);
                writer.print(", ");
            }
            else {
                pd.accept(this);
            }

        }

        writer.print(" )");
        writer.println("{");
        item.getBody().accept(this);

        writer.println("}");

        stack.exitScope();
        return null;
    }

    @Override
    public Object visit(ParamDeclNode item) {

        for(int i= 0; i< item.getIdentifierList().size(); i++){

            IdentifierExprNode id = item.getIdentifierList().get(i);
            writer.print(getTypeFromToken(id.getType())+" ");
            id.accept(this);

            if(i != item.getIdentifierList().size()-1){
                writer.print(", ");
            }
        }
        return null;
    }

    @Override
    public Object visit(BodyNode item) {

        inserisciTab();
        writer.print("// Dichiarazione variabili\n");
        inserisciTab();
        writer.println("int correctInputCheck = 0;");
        // creazione del codice c per le inizializzazioni di variabili
        ArrayList<VarDeclNode> vd = ordinaVarDecl(item.getVarDeclList());

        for (VarDeclNode var : vd){
            var.accept(this);
        }

        for (StatementNode st : item.getStmtNodeList()) {
            inserisciTab();
            st.accept(this);
        }

        return null;
    }

    @Override
    public Object visit(IfStatNode item) {
        stack.enterScope(item.getSymbolTableIfScope());
        writer.print("if(");
        item.getExpression().accept(this);

        writer.println("){");

        item.getBodyThen().accept(this);
        stack.exitScope();
        inserisciTab();
        writer.println("}");



        if(item.getBodyElse() != null) {
            //Cambio scope di nuovo, devo conservarlo e aggiorno lo scope
            inserisciTab();
            stack.enterScope(item.getSymbolTableElseScope());
            writer.println("else{");
            item.getBodyElse().accept(this);
            stack.exitScope(); //Ripristino lo scope
            inserisciTab();
            writer.println("}");

        }
        return null;
    }

    @Override
    public Object visit(AssignStatNode item) {
        for (int i = 0; i < item.getIdentifierList().size(); i++){
            item.getIdentifierList().get(i).accept(this);
            writer.print(" = ");
            item.getExpressionList().get(i).accept(this);
            writer.println(";");
        }
        return null;
    }

    @Override
    public Object visit(ForStatNode item) {

        IntegerConstantNode constantFrom = (IntegerConstantNode) item.getCostantFrom();
        IntegerConstantNode constantTo = (IntegerConstantNode) item.getCostantTo();

        writer.print("for( int ");

        // rimozione dell'indice del for dallo scope per evitare ridichiarazione
        item.getSymbolTableFor().remove(item.getIdentifier().getValue());
        stack.enterScope(item.getSymbolTableFor());

        item.getIdentifier().accept(this);

        writer.print(" = " + constantFrom.getValue()+ "; ");

        if(constantFrom.getValue() <= constantTo.getValue()){

            item.getIdentifier().accept(this);
            writer.print(" <= "+ constantTo.getValue() +";");

            item.getIdentifier().accept(this);
            writer.print("++");
        } else {
            item.getIdentifier().accept(this);
            writer.print(" >= "+ constantTo.getValue() +";");

            item.getIdentifier().accept(this);
            writer.print("--");
        }
        writer.println("){");

        item.getBody().accept(this);
        //Ripristino scope del padre
        stack.exitScope();
        inserisciTab();
        writer.println("}");
        return null;
    }

    @Override
    public Object visit(ReadStatNode item) {

        StringConstantNode stringConst;
        String scanf = "";
        if ((stringConst = item.getStringCostant()) != null){
            scanf += "\tprintf(\""+ stringConst.getValue()+"\\n\");\n";
            writer.println("printf(\""+ stringConst.getValue()+"\\n\");");
            inserisciTab();
            scanf += getTab()+"\t\tfflush(stdout);\n";
            writer.println("fflush(stdout);");
        }

        /* controllo se saranno lette delle stringhe */

        for (int i = 0; i < item.getIdentifierList().size(); i++){
            if (item.getIdentifierList().get(i).getType() == Symbols.STRING){
                inserisciTab();
                scanf += getTab()+"\t\tfree("+ item.getIdentifierList().get(i).getValue()+ ");\n";
                scanf += getTab()+"\t\t"+item.getIdentifierList().get(i).getValue() + " = " + "malloc(20*sizeof(char));\n";
                writer.println(item.getIdentifierList().get(i).getValue() + " = " + "malloc(20*sizeof(char));");
            }
        }
        inserisciTab();

        scanf +=getTab()+"\t\tcorrectInputCheck = scanf(\"";
        writer.print("correctInputCheck = scanf(\"");

        String formatSpecifiers ="";
        for (int i = 0; i < item.getIdentifierList().size(); i++){
            formatSpecifiers += scanfFormatSpecifiers(item.getIdentifierList().get(i).getType());
            if (i != item.getIdentifierList().size()-1)
                writer.print(" ");
        }

        scanf += formatSpecifiers;

        scanf += "\", ";
        writer.print("\", ");

        for (int i = 0; i < item.getIdentifierList().size(); i++){
            if (item.getIdentifierList().get(i).getType() != Symbols.STRING) {
                scanf += "&";
                writer.print("&");
            }
            scanf += item.getIdentifierList().get(i).getValue();
            item.getIdentifierList().get(i).accept(this);
            if (i != item.getIdentifierList().size()-1) {
                scanf += ", ";
                writer.print(", ");
            }
        }

        scanf += ");";
        writer.println(");");
        inserisciTab();

        gestioneErroreLetturaScanf(item.getIdentifierList().size(), scanf);

        return null;
    }

    @Override
    public Object visit(ReturnStatNode item) {

        if (item.getExpression() != null) {
            writer.print("return ");
            item.getExpression().accept(this);
            writer.println(";");
        }else{
            writer.print("return;");
        }
        return null;
    }

    @Override
    public Object visit(WhileStatNode item) {


        stack.enterScope(item.getSymbolTableWhile());

        writer.print("while(");

        item.getExpression().accept(this);

        writer.println("){");
        item.getBody().accept(this);
        //Ristabilire lo stack del padre
        stack.exitScope(); //Ripristino lo scope padre

        inserisciTab();
        writer.println("}");


        return null;
    }

    @Override
    public Object visit(WriteStatNode item) {
        writer.print("printf(\"");

        for (int i = 0; i < item.getExpressionList().size(); i++){
            printfFormatSpecifiers(item.getExpressionList().get(i).getType());
            if (i != item.getExpressionList().size()-1)
                writer.print(" ");
        }
        if (item.isNewLine())
            writer.print("\\n\",");
        else
            writer.print("\",");

        for (int i = 0; i < item.getExpressionList().size(); i++){
            item.getExpressionList().get(i).accept(this);
            if (i != item.getExpressionList().size()-1)
                writer.print(", ");
        }

        writer.println(");");
        inserisciTab();
        writer.println("fflush(stdout);");
        return null;
    }

    @Override
    public Object visit(IntegerConstantNode item) {
        writer.print(item.getValue());
        return null;
    }

    @Override
    public Object visit(BooleanConstantNode item) {
        if(item.getValue())
            writer.print(1);
        else
            writer.print(0);

        return null;
    }

    @Override
    public Object visit(RealConstantNode item) {
        writer.print(item.getValue());
        return null;
    }

    @Override
    public Object visit(StringConstantNode item) {
        int len = item.getValue().length();
        String str ="";
        // serve nel caso in cui l'utente imposti una stringa vuota
        if (len == 0){
            len +=1;
            str +=" ";
        }else {
            str = item.getValue();
        }
        writer.print("strcpy(malloc("+(len+1)+"*sizeof(char)),\""+str +"\")");
        return null;
    }

    @Override
    public Object visit(CharConstantNode item) {
        writer.print("\'"+item.getValue()+"\'");
        return null;
    }

    @Override
    public Object visit(FunCallExprNode item) {

        String nomeFunzione = item.getIdentifier().getValue();
        List<ExpressionNode> parametriFormali = item.getListOfExpr();

        FunSymbol function = (FunSymbol) stack.lookup(nomeFunzione, SymbolTypes.FUNCTION);


        writer.print(nomeFunzione +"(");

        for (int i =0; i < function.getListOfParams().size(); i++) {
            if (function.getListOfParams().get(i).isOut())
                writer.print("&");

            parametriFormali.get(i).accept(this);
            if (i != function.getListOfParams().size()-1)
                writer.print(", ");
        }

        writer.print(")");

        return null;
    }

    @Override
    public Object visit(FunCallStatNode item) {

        String nomeFunzione = item.getIdentifier().getValue();
        List<ExpressionNode> parametriFormali = item.getListOfExpr();

        FunSymbol function = (FunSymbol) stack.lookup(nomeFunzione, SymbolTypes.FUNCTION);


        writer.print(nomeFunzione +"(");

        for (int i =0; i < function.getListOfParams().size(); i++) {
            if (function.getListOfParams().get(i).isOut())
                writer.print("&");

            parametriFormali.get(i).accept(this);
            if (i != function.getListOfParams().size()-1)
                writer.print(", ");
        }

        writer.println(");");
        return null;
    }

    @Override
    public Object visit(IdentifierExprNode item) {

        IdSymbol idSym = null;
        if((idSym = (IdSymbol) stack.lookup(item.getValue(), SymbolTypes.VAR)) == null){
            writer.print(item.getValue());
        }
        else if(idSym.isPointer() ){
            writer.print("* "+ item.getValue());
        }
        else{
            writer.print(item.getValue());
        }

        return null;
    }

    @Override
    public Object visit(UnaryExpressionNode item) {
        writer.print("(");
        generaEspressioniUnarie(item.getOperation());
        item.getRightExpression().accept(this);
        writer.print(")");
        return null;
    }

    @Override
    public Object visit(BinaryExpressionNode item) {

        if(item.getOperation() == Symbols.STR_CONCAT){
            writer.print("strcat(");
            item.getLeftExpression().accept(this);
            writer.print(",");
            item.getRightExpression().accept(this);
            writer.print(")");
        }else if (item.getOperation() == Symbols.POW){
            writer.print("pow(");
            item.getLeftExpression().accept(this);
            writer.print(",");
            item.getRightExpression().accept(this);
            writer.print(")");
        }else{
            writer.print("(");
            item.getLeftExpression().accept(this);
            generaEspressioniBinarie(item.getOperation());
            item.getRightExpression().accept(this);
            writer.print(")");
        }
        return null;
    }


    private static void generaLibrerie(){
        writer.println("#include <stdio.h>");
        writer.println("#include <stdlib.h>");
        writer.println("#include <string.h>");
        writer.println("#include <math.h>");
        writer.println("");
    }
    private static void generaPrototipoFunzione(FunSymbol fs){
        writer.print(fs.getTypeFromToken(fs.getReturnType()) +
                " "+ fs.getIdentifier()+"(");
        for(int i = 0; i< fs.getListOfParams().size(); i++){
            if(fs.getListOfParams().get(i).isOut()){
                if(i != fs.getListOfParams().size()-1){
                    writer.print(fs.getTypeFromToken(fs.getListOfParams().get(i).getPrimitiveTypeOfParam())+" *"+ fs.getListOfParams().get(i).getIdentifier()+", ");
                }
                else {
                    writer.print(fs.getTypeFromToken(fs.getListOfParams().get(i).getPrimitiveTypeOfParam())+" *"+ fs.getListOfParams().get(i).getIdentifier());

                }

            }
            else {
                if(i != fs.getListOfParams().size()-1){
                    writer.print(fs.getTypeFromToken(fs.getListOfParams().get(i).getPrimitiveTypeOfParam())+" "+ fs.getListOfParams().get(i).getIdentifier()+ ",");
                }
                else {
                    writer.print(fs.getTypeFromToken(fs.getListOfParams().get(i).getPrimitiveTypeOfParam())+" "+ fs.getListOfParams().get(i).getIdentifier());
                }

            }
        }
        writer.println(");");

    }


    private static void generaDichiarazioneVariabile(IdSymbol is){
        writer.println(is.getTypeFromToken(is.getType()) +
                " "+ is.getIdentifier()+";");
    }

    private static void generaEspressioniBinarie(int operazione){
        switch (operazione){
            case Symbols.PLUS: writer.print(" + "); break;
            case Symbols.MINUS: writer.print(" - "); break;
            case Symbols.TIMES: writer.print(" * "); break;
            case Symbols.DIV: writer.print(" / "); break;
            case Symbols.AND: writer.print(" && "); break;
            case Symbols.OR: writer.print(" || "); break;
            case Symbols.GT: writer.print(" > "); break;
            case Symbols.GE: writer.print(" >= "); break;
            case Symbols.LT: writer.print(" < "); break;
            case Symbols.LE: writer.print(" <= "); break;
            case Symbols.EQ: writer.print(" == "); break;
            case Symbols.NE: writer.print(" != "); break;
        }

    }

    private static void generaEspressioniUnarie(int operazione){
        switch (operazione){
            case Symbols.MINUS: writer.print(" -"); break;
            case Symbols.NOT: writer.print(" !"); break;
        }

    }

    private ArrayList<VarDeclNode> ordinaVarDecl(List<VarDeclNode> vd){

        ArrayList<IdInitNode> initConstant = new ArrayList<>();
        ArrayList<IdInitObbNode> initVarConstant = new ArrayList<>();
        ArrayList<IdInitNode> initExpr = new ArrayList<>();
        ArrayList<IdInitNode> initId = new ArrayList<>();

        // creazione del codice c per le inizializzazioni di variabili con una costante
        for (VarDeclNode var : vd){
            for (IdInitNode id : var.getIdIList()){
                if (id.getExpression() instanceof Constant && id.getExpression() !=null) {
                    initConstant.add(id);
                }
            }
            for (IdInitObbNode id : var.getIdIObList()){
                initVarConstant.add(id);
            }
        }

        // creazione del codice c per le inizializzazioni di variabili con espressioni
        for (VarDeclNode var : vd){
            for (IdInitNode id : var.getIdIList()){
                if (!(id.getExpression() instanceof Constant) && id.getExpression() != null) {
                    initExpr.add(id);
                }
            }
        }

        for (VarDeclNode var : vd){
            for (IdInitNode id : var.getIdIList()){
                if (id.getExpression() == null) {
                    initId.add(id);
                }
            }
        }

        ArrayList<VarDeclNode> varDeclNode = new ArrayList<>();

        VarDeclNode vardDeclId = new VarDeclNode();
        vardDeclId.setIdInitNodeVarDeclNode(initId);
        varDeclNode.add(vardDeclId);

        VarDeclNode vardDeclConstant = new VarDeclNode();
        vardDeclConstant.setIdInitNodeVarDeclNode(initConstant);
        vardDeclConstant.setIdInitObbNode(initVarConstant);
        varDeclNode.add(vardDeclConstant);

        VarDeclNode vardDeclExpr = new VarDeclNode();
        vardDeclExpr.setIdInitNodeVarDeclNode(initExpr);
        varDeclNode.add(vardDeclExpr);

        return varDeclNode;
    }

    private void printfFormatSpecifiers(int token){
        switch (token){
            case Symbols.INTEGER :
                writer.print("%d");
                break;
            case Symbols.FLOAT:
                writer.print("%f");
                break;
            case Symbols.CHAR:
                writer.print("%c");
                break;
            case Symbols.BOOL:
                writer.print("%d");
                break;
            case Symbols.STRING:
                writer.print("%s");
                break;
        }
    }

    private String scanfFormatSpecifiers(int token){
        String scanf = "";
        switch (token){
            case Symbols.INTEGER :
                scanf+= " %d";
                writer.print("%d");
                break;
            case Symbols.FLOAT:
                scanf+= " %f";
                writer.print("%f");
                break;
            case Symbols.CHAR:
                scanf+= " %c";
                writer.print("%c");
                break;
            case Symbols.BOOL:
                scanf+= " %d";
                writer.print("%d");
                break;
            case Symbols.STRING:
                scanf+= " %s";
                writer.print("%s");
                break;
        }
        return scanf;
    }

    private static String getTypeFromToken(int token){
        String type = "";
        switch (token){
            case Symbols.INTEGER :
                type += "int";
                break;
            case Symbols.FLOAT:
                type += "float";
                break;
            case Symbols.CHAR:
                type += "char";
                break;
            case Symbols.BOOL:
                type += "int";
                break;
            case Symbols.STRING:
                type += "char* ";
                break;
            case Symbols.VOID:
                type += "void";
                break;
        }

        return type;
    }

    private void inserisciTab(){
        int livello_di_profondita = stack.getStack().size();
        for (int i = 0; i < livello_di_profondita-1; i++)
            writer.print("\t");
    }

    private String getTab(){
        int livello_di_profondita = stack.getStack().size();
        String tab = "";
        for (int i = 0; i < livello_di_profondita-1; i++)
            tab +="\t";

        return tab;
    }
    private void gestioneErroreLetturaScanf(int numElementi, String scanf){

        writer.println("if (correctInputCheck != " + numElementi + ") {");
        inserisciTab();
        writer.println("\tdo{");
        inserisciTab();
        writer.println("\t\twhile (getchar() != '\\n' ); // ripulisce lo standard input"); // ripulisce lo standard input
        inserisciTab();
        writer.println("\t"+scanf);
        inserisciTab();
        writer.print("\t\tprintf(\"\\n\");\n");
        inserisciTab();
        writer.print("\t}");
        writer.println("while ( correctInputCheck !="+ numElementi +");\n");
        inserisciTab();
        writer.println("}");
    }
}
