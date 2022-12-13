package visitor;

import parser.newLangTree.*;
import parser.newLangTree.expression.FunCallExprNode;
import parser.newLangTree.expression.BinaryExpressionNode;
import parser.newLangTree.expression.ExpressionNode;
import parser.newLangTree.expression.IdentifierExprNode;
import parser.newLangTree.expression.UnaryExpressionNode;
import parser.newLangTree.expression.constants.*;
import parser.newLangTree.statements.*;
import org.w3c.dom.Element;
import parser.Symbols;

import java.io.IOException;
import java.io.PrintWriter;

public class XMLTreeGenerator implements  Visitor{

    PrintWriter writer;

    public XMLTreeGenerator(String filename) {
        try {
            writer = new PrintWriter( "XMLTreeCode/"+filename+".xml");
            writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            writer.println("<!-- Per visualizzare l'albero https://countwordsfree.com/xmlviewer -->");
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

        writer.println("<ProgramNode>");

        writer.println("<Declist>");
        for (DeclNode i : item.getDeclList1())
            i.accept(this);
        writer.println("</Declist>");

        item.getMainFuncDecl().accept(this);

        writer.println("<Declist>");
        for (DeclNode i : item.getDeclList2())
            i.accept(this);
        writer.println("</Declist>");

        writer.println("</ProgramNode>");
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

        writer.println("<Main>");

        item.getFunDeclNode().accept(this);

        writer.println("</Main>");
        return null;
    }

    @Override
    public Object visit(VarDeclNode item) {
        writer.println("<VarDeclNode>");

        writer.println("<Type>" + Symbols.terminalNames[item.getType().get(0)] + "</Type>");

        for (IdInitNode i : item.getIdIList())
            i.accept(this);

        for (IdInitObbNode i : item.getIdIObList())
             i.accept(this);

        writer.println("</VarDeclNode>");
        return null;
    }

    @Override
    public Object visit(IdInitNode item) {
        writer.println("<IdInitNode>");

        item.getIdentifier().accept(this);

        if (item.getExpression() != null)
            item.getExpression().accept(this);

        writer.println("</IdInitNode>");
        return null;
    }

    @Override
    public Object visit(IdInitObbNode item) {
        writer.println("<IdInitObbNode>");

        item.getIdentifier().accept(this);

        ExpressionNode constant = (ExpressionNode) item.getCostantValue();
        Element Constant = (Element) constant.accept(this);

        writer.println("</IdInitObbNode>");
        return null;
    }

    @Override
    public Object visit(FunDeclNode item) {
        writer.println("<FuncDeclNode Type='"+Symbols.terminalNames[item.getTypeOrVoid()]+"'>");

        item.getIdentifier().accept(this);

        for (ParamDeclNode i : item.getParamDecl()) {
            i.accept(this);
        }

        item.getBody().accept(this);

        writer.println("</FuncDeclNode>");

        return  null;
    }

    @Override
    public Object visit(ParamDeclNode item) {

        writer.println("<ParamDecl>");

        //Se ho parametri di ritorno devo mettere il tipo
        if(item.isOut()){
            writer.println("<Type-out>"+ Symbols.terminalNames[item.getType().get(0)]+"</Type-out>");
        }
        else{
            writer.println("<Type-in>"+ Symbols.terminalNames[item.getType().get(0)]+"</Type-in>");
        }

        for (IdentifierExprNode i : item.getIdentifierList()) {
            i.accept(this);
        }

        writer.println("</ParamDecl>");

        return null;
    }

    @Override
    public Object visit(BodyNode item) {

        writer.println("<Body>");

        for(VarDeclNode i : item.getVarDeclList())
            i.accept(this);


        for(StatementNode i : item.getStmtNodeList())
            i.accept(this);

        writer.println("</Body>");
        return null;
    }

    @Override
    public Object visit(IfStatNode item) {
        writer.println("<IfStat>");

        item.getExpression().accept(this);

        item.getBodyThen().accept(this);

        if(item.getBodyElse() != null){
            writer.println("<Else>");
            item.getBodyElse().accept(this);
            writer.println("</Else>");
        }

        writer.println("</IfStat>");
        return null;
    }

    @Override
    public Object visit(AssignStatNode item) {
        writer.println("<AssignStat>");

        for (IdentifierExprNode i : item.getIdentifierList())
            i.accept(this);


        for (ExpressionNode e : item.getExpressionList())
            e.accept(this);

        writer.println("</AssignStat>");


        return null;
    }

    @Override
    public Object visit(ForStatNode item) {
        writer.println("<ForStat fromValue='"+item.getCostantFrom()+"' toValue='"+item.getCostantTo()+"'>");


        item.getIdentifier().accept(this);

        item.getBody().accept(this);

        writer.println("</ForStat>");
        return null;
    }

    @Override
    public Object visit(ReadStatNode item) {
        writer.println("<ReadStat>");

        item.getStringCostant().accept(this);

        for(IdentifierExprNode i : item.getIdentifierList()){
            i.accept(this);
        }

        writer.println("</ReadStat>");
        return null;
    }

    @Override
    public Object visit(ReturnStatNode item) {

        writer.println("<ReturnStat>");

        item.getExpression().accept(this);

        writer.println("</ReturnStat>");
        return null;
    }

    @Override
    public Object visit(WhileStatNode item) {
        writer.println("<WhileStat>");

        item.getExpression().accept(this);

        item.getBody().accept(this);

        writer.println("</WhileStat>");
        return null;
    }

    @Override
    public Object visit(WriteStatNode item){
        if(item.isNewLine())
        {
            writer.println("<Writeln>");

        }
        else {
            writer.println("<Write>");
        }


        for(ExpressionNode i : item.getExpressionList())
        {
           i.accept(this);
        }

        if(item.isNewLine())
        {
            writer.println("</Writeln>");

        }
        else {
            writer.println("</Write>");
        }

        return null;

    }

    @Override
    public Object visit(IntegerConstantNode item) {


        writer.println("<IntegerConstant> " + item.getValue() + "</IntegerConstant>");

        return null;
    }

    @Override
    public Object visit(BooleanConstantNode item) {
        writer.println("<BooleanConstant> " + item.getValue() + "</BooleanConstant>");

        return null;
    }

    @Override
    public Object visit(RealConstantNode item) {
        writer.println("<RealConstant> " + item.getValue() + "</RealConstant>");

        return null;
    }

    @Override
    public Object visit(StringConstantNode item) {
        writer.println("<StringConstant> " + item.getValue() + "</StringConstant>");

        return null;
    }

    @Override
    public Object visit(CharConstantNode item) {

        writer.println("<CharConstant> " + item.getValue() + "</CharConstant>");

        return null;
    }

    @Override
    public Object visit(FunCallExprNode item) {
        writer.println("<FunCallExpr>");

        item.getIdentifier().accept(this);


        for (ExpressionNode i : item.getListOfExpr())
            i.accept(this);

        writer.println("</FunCallExpr>");
        return null;
    }

    @Override
    public Object visit(FunCallStatNode item) {
        writer.println("<FunCallStat>");

        item.getIdentifier().accept(this);


        for (ExpressionNode i : item.getListOfExpr())
            i.accept(this);

        writer.println("</FunCallStat>");
        return null;
    }

    @Override
    public Object visit(IdentifierExprNode item) {
        writer.println("<IdentifierExpression>");
        writer.println("<value> " + item.getValue()+ "</value>");
        writer.println("</IdentifierExpression>");
        return null;

    }

    @Override
    public Object visit(BinaryExpressionNode item) {
        writer.println("<BinaryExpression>");
        item.getLeftExpression().accept(this);
        writer.println("<Operation> " + Symbols.terminalNames[item.getOperation()] + "</Operation>");
        item.getRightExpression().accept(this);
        writer.println("</BinaryExpression>");
        return null;
    }
    @Override
    public Object visit(UnaryExpressionNode item) {
        writer.println("<UnaryExpression>");
        writer.println("<Operation> " + Symbols.terminalNames[item.getOperation()] + "</Operation>");
        item.getRightExpression().accept(this);
        writer.println("</UnaryExpression>");
        return null;
    }
}
