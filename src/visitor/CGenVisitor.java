package visitor;

import parser.Symbols;
import parser.newLangTree.nodes.*;
import parser.newLangTree.nodes.expression.*;
import parser.newLangTree.nodes.expression.constants.*;
import parser.newLangTree.nodes.statements.*;
import semantic.SymbolTableStack;
import semantic.SymbolTypes;
import semantic.symbols.FunSymbol;
import semantic.symbols.IdSymbol;

import java.io.IOException;
import java.io.PrintWriter;

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

        writer.println("\n// dichiarazione variabili globali");
        item.getSymbolTableProgramScope().forEach((key, sym) ->{
            if(sym instanceof IdSymbol){
                IdSymbol is = (IdSymbol) sym;
                generaDichiarazioneVariabile(is);
            }
        });

        writer.println("");
        item.getDecl().accept(this);

        return null;
    }

    @Override
    public Object visit(DeclNode item) {

        writer.println("// inizializzazione delle variabili");
        // creazione del codice c per le inizializzazioni di variabili con una costante
        for (VarDeclNode var : item.getVarDeclList()){
            for (IdInitNode id : var.getIdIList()){
                if (id.getExpression() instanceof Constant && id.getExpression() !=null) {
                    var.accept(this);
                }
            }
            for (IdInitObbNode id : var.getIdIObList()){
                    var.accept(this);
            }
        }

        // creazione del codice c per le inizializzazioni di variabili con espressioni
        for (VarDeclNode var : item.getVarDeclList()){
            for (IdInitNode id : var.getIdIList()){
                if (!(id.getExpression() instanceof Constant) && id.getExpression() != null) {
                    var.accept(this);
                }
            }
        }

        writer.println("\n//-----------Implementazione funzioni-----------");

        for (FunDeclNode fd : item.getFunDeclList()){
            fd.accept(this);
        }

        return null;
    }

    @Override
    public Object visit(MainFuncDeclNode item) {return null;
    }

    @Override
    public Object visit(VarDeclNode item) {

        for (IdInitNode id : item.getIdIList()){
                id.accept(this);
        }

        for (IdInitObbNode id : item.getIdIObList()){
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

        writer.println("\n// Dichiarazione variabili");
        stack.getCurrentScope().forEach((key, sym) ->{
            if(sym instanceof IdSymbol){
                IdSymbol idS = (IdSymbol) sym;
                if(!idS.isParameter()){
                    generaDichiarazioneVariabile(idS);
                }

            }
        });

        // creazione del codice c per le inizializzazioni di variabili con una costante
        for (VarDeclNode var : item.getVarDeclList()){
            for (IdInitNode id : var.getIdIList()){
                if (id.getExpression() instanceof Constant && id.getExpression() !=null) {
                    var.accept(this);
                }
            }
            for (IdInitObbNode id : var.getIdIObList()){
                var.accept(this);
            }
        }

        // creazione del codice c per le inizializzazioni di variabili con espressioni
        for (VarDeclNode var : item.getVarDeclList()){
            for (IdInitNode id : var.getIdIList()){
                if (!(id.getExpression() instanceof Constant) && id.getExpression() != null) {
                    var.accept(this);
                }
            }
        }



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
        if (len == 0){
            len +=1;
            str +=" ";
        }else {
            str = item.getValue();
        }
        writer.print("strcpy(malloc("+len+"*sizeof(char)),\""+str +"\")");
        return null;
    }

    @Override
    public Object visit(CharConstantNode item) {
        writer.print("\'"+item.getValue()+"\'");
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
            item.getLeftExpression().accept(this);
            generaEspressioniBinarie(item.getOperation());
            item.getRightExpression().accept(this);
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
}
