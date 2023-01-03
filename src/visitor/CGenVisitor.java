package visitor;

import codeGenerator.CodeGenerator;
import codeGenerator.VariableGenerator;
import parser.newLangTree.nodes.*;
import parser.newLangTree.nodes.expression.BinaryExpressionNode;
import parser.newLangTree.nodes.expression.FunCallExprNode;
import parser.newLangTree.nodes.expression.IdentifierExprNode;
import parser.newLangTree.nodes.expression.UnaryExpressionNode;
import parser.newLangTree.nodes.expression.constants.*;
import parser.newLangTree.nodes.statements.*;
import semantic.VarTypes;
import semantic.symbols.FunSymbol;
import semantic.symbols.Symbol;

import java.io.IOException;
import java.io.PrintWriter;

public class CGenVisitor implements Visitor{

    PrintWriter writer;

    public CGenVisitor(String filename){
        try {
            writer = new PrintWriter( "CodiciC/"+filename+".c");
            CodeGenerator.setWriter(writer);
            CodeGenerator.generaLibrerie();

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

        item.getSymbolTableProgramScope().forEach((key, sym) ->{
            if(sym instanceof FunSymbol){
                FunSymbol fs = (FunSymbol) sym;
                CodeGenerator.generaPrototipoFunzione(fs);
            }
        });

        return null;
    }

    @Override
    public Object visit(DeclNode item) {
        return null;
    }

    @Override
    public Object visit(MainFuncDeclNode item) {
        return null;
    }

    @Override
    public Object visit(VarDeclNode item) {
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
}
