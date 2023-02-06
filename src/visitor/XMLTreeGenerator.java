package visitor;

import org.w3c.dom.Document;
import parser.newLangTree.nodes.*;
import parser.newLangTree.nodes.expression.FunCallExprNode;
import parser.newLangTree.nodes.expression.BinaryExpressionNode;
import parser.newLangTree.nodes.expression.ExpressionNode;
import parser.newLangTree.nodes.expression.IdentifierExprNode;
import parser.newLangTree.nodes.expression.UnaryExpressionNode;
import parser.newLangTree.nodes.expression.constants.*;
import parser.newLangTree.nodes.statements.*;
import org.w3c.dom.Element;

import parser.Symbols;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.PrintWriter;

public class XMLTreeGenerator implements  Visitor{

    private DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

    private DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

    private Document document = documentBuilder.newDocument();

    PrintWriter writer;

    //https://countwordsfree.com/xmlviewer
    public XMLTreeGenerator() throws ParserConfigurationException {}


    @Override
    public Object visit(ProgramNode item) {

        Element root = document.createElement("Programm");

        Element declNode, mainNode;

        declNode = (Element) item.getDecl().accept(this);
        mainNode = (Element) item.getMainFuncDecl().accept(this);

        root.appendChild(declNode);
        root.appendChild(mainNode);

        document.appendChild(root);

        return document;
    }

    @Override
    public Object visit(DeclNode item) {

        Element varDeclNode, fundDeclNode;
        Element root = document.createElement("DeclNode");

        for (VarDeclNode i : item.getVarDeclList()) {
            varDeclNode = (Element) i.accept(this);
            root.appendChild(varDeclNode);
        }

        for (FunDeclNode i : item.getFunDeclList()) {
            fundDeclNode = (Element) i.accept(this);
            root.appendChild(fundDeclNode);
        }


        return root;
    }

    @Override
    public Object visit(MainFuncDeclNode item) {

        Element funDeclNode;
        Element root = document.createElement("Main");


        funDeclNode = (Element) item.getFunDeclNode().accept(this);

        root.appendChild(funDeclNode);
        return root;
    }

    @Override
    public Object visit(VarDeclNode item) {

        Element type, IdInitNode, IdInitObbNode;
        Element root = document.createElement("VarDeclNode");

        type = document.createElement("Type");
        type.appendChild(document.createTextNode(Symbols.terminalNames[item.getType()]));

        root.appendChild(type);

        for (IdInitNode i : item.getIdIList()) {
            IdInitNode = (Element) i.accept(this);
            root.appendChild(IdInitNode);
        }

        for (IdInitObbNode i : item.getIdIObList()) {
            IdInitObbNode = (Element) i.accept(this);
            root.appendChild(IdInitObbNode);
        }

        return root;
    }

    @Override
    public Object visit(IdInitNode item){

        Element identifier, expression;
        Element root = document.createElement("IdInitNode");


        identifier = (Element) item.getIdentifier().accept(this);
        root.appendChild(identifier);

        if (item.getExpression() != null) {
            expression = (Element) item.getExpression().accept(this);
            root.appendChild(expression);
        }


        return root;
    }

    @Override
    public Object visit(IdInitObbNode item){

        Element identifier, constantEl;
        Element root = document.createElement("IdInitObbNode");


        identifier = (Element) item.getIdentifier().accept(this);
        root.appendChild(identifier);

        ExpressionNode constant = (ExpressionNode) item.getCostantValue();
        constantEl = (Element) constant.accept(this);
        root.appendChild(constantEl);


        return root;
    }

    @Override
    public Object visit(FunDeclNode item){

        Element identifier, param, body;

        Element root = document.createElement("FuncDeclNode");
        root.setAttribute("Type",Symbols.terminalNames[item.getTypeOrVoid()] );

        identifier = (Element) item.getIdentifier().accept(this);
        root.appendChild(identifier);

        for (ParamDeclNode i : item.getParamDecl()) {
            param = (Element) i.accept(this);
            root.appendChild(param);
        }

        body = (Element) item.getBody().accept(this);
        root.appendChild(body);

        return root;
    }

    @Override
    public Object visit(ParamDeclNode item){

        Element identifier;
        Element root = document.createElement("ParamDecl");

        //Se ho parametri di ritorno devo mettere il tipo
        if(item.isOut()){
            Element out = document.createElement("Type-out");
            out.appendChild(document.createTextNode(Symbols.terminalNames[item.getType()]));
            root.appendChild(out);
        }
        else{
            Element in = document.createElement("Type-in");
            in.appendChild(document.createTextNode(Symbols.terminalNames[item.getType()]));
            root.appendChild(in);
        }

        for (IdentifierExprNode i : item.getIdentifierList()) {
            identifier = (Element) i.accept(this);
            root.appendChild(identifier);
        }


        return root;
    }

    @Override
    public Object visit(BodyNode item){

        Element varDeclNode, statementNode;

        Element root = document.createElement("Body");

        for(VarDeclNode i : item.getVarDeclList()) {
            varDeclNode = (Element) i.accept(this);
            root.appendChild(varDeclNode);
        }


        for(StatementNode i : item.getStmtNodeList()) {
            statementNode = (Element) i.accept(this);
            root.appendChild(statementNode);
        }

        return root;
    }

    @Override
    public Object visit(IfStatNode item){

        Element body, expression, elseEl;
        Element root = document.createElement("IfStat");

        expression = (Element) item.getExpression().accept(this);
        root.appendChild(expression);

        body = (Element) item.getBodyThen().accept(this);
        root.appendChild(body);

        if(item.getBodyElse() != null){
            Element root_else = document.createElement("ElseStat");
            elseEl = (Element) item.getBodyElse().accept(this);
            root_else.appendChild(elseEl);
            root.appendChild(root_else);
        }

        return root;
    }

    @Override
    public Object visit(AssignStatNode item){

        Element identifier, expression;

        Element root = document.createElement("AssignStat");

        for (IdentifierExprNode i : item.getIdentifierList()) {
            identifier = (Element) i.accept(this);
            root.appendChild(identifier);
        }

        for (ExpressionNode e : item.getExpressionList()) {
            expression = (Element) e.accept(this);
            root.appendChild(expression);
        }

        return root;
    }

    @Override
    public Object visit(ForStatNode item){

        Element identifier, body;

        Element root = document.createElement("ForStat");
        root.setAttribute("fromValue", String.valueOf(((IntegerConstantNode)item.getCostantFrom()).getValue()));
        root.setAttribute("toValue", String.valueOf(((IntegerConstantNode)item.getCostantTo()).getValue()));

        identifier = (Element) item.getIdentifier().accept(this);
        root.appendChild(identifier);

        body = (Element) item.getBody().accept(this);
        root.appendChild(body);

        return root;
    }

    @Override
    public Object visit(ReadStatNode item){

        Element string_constant, expression;
        Element root = document.createElement("ReadStat");

        if (item.getStringCostant() != null) {
            string_constant = (Element) item.getStringCostant().accept(this);
            root.appendChild(string_constant);
        }

        for(IdentifierExprNode i : item.getIdentifierList()){
            expression = (Element) i.accept(this);
            root.appendChild(expression);
        }

        return root;
    }

    @Override
    public Object visit(ReturnStatNode item){

        Element expression;
        Element root = document.createElement("ReturnStat");

        if(item.getExpression() != null) {
            expression = (Element) item.getExpression().accept(this);
            root.appendChild(expression);
        }

        return root;
    }

    @Override
    public Object visit(WhileStatNode item){

        Element expression, body;

        Element root = document.createElement("WhileStat");

        expression = (Element) item.getExpression().accept(this);
        root.appendChild(expression);

        body = (Element) item.getBody().accept(this);
        root.appendChild(body);

        return root;
    }

    @Override
    public Object visit(WriteStatNode item){

        Element root, expression;

        if(item.isNewLine()) {
            root = document.createElement("Writeln");
        }
        else {
            root = document.createElement("Write");
        }

        for(ExpressionNode i : item.getExpressionList()) {
            expression = (Element) i.accept(this);
            root.appendChild(expression);
        }

        return root;
    }

    @Override
    public Object visit(IntegerConstantNode item){

        Element root = document.createElement("IntegerConstant");
        root.appendChild(document.createTextNode(String.valueOf(item.getValue())));

        return root;
    }

    @Override
    public Object visit(BooleanConstantNode item){

        Element root = document.createElement("BooleanConstant");
        root.appendChild(document.createTextNode(String.valueOf(item.getValue())));

        return root;
    }

    @Override
    public Object visit(RealConstantNode item){

        Element root = document.createElement("RealConstant");
        root.appendChild(document.createTextNode(String.valueOf(item.getValue())));

        return root;
    }

    @Override
    public Object visit(StringConstantNode item){

        Element root = document.createElement("StringConstant");
        root.appendChild(document.createTextNode(String.valueOf(item.getValue())));

        return root;
    }

    @Override
    public Object visit(CharConstantNode item){

        Element root = document.createElement("CharConstant");
        root.appendChild(document.createTextNode(String.valueOf(item.getValue())));

        return root;
    }

    @Override
    public Object visit(FunCallExprNode item){
        Element identifier, expression;
        Element root = document.createElement("FunCallExpr");

        identifier = (Element) item.getIdentifier().accept(this);
        root.appendChild(identifier);

        for (ExpressionNode i : item.getListOfExpr()) {
            expression = (Element) i.accept(this);
            root.appendChild(expression);
        }

        return root;
    }

    @Override
    public Object visit(FunCallStatNode item){

        Element identifier, expression;
        Element root = document.createElement("FunCallStat");

        identifier = (Element) item.getIdentifier().accept(this);
        root.appendChild(identifier);

        for (ExpressionNode i : item.getListOfExpr()) {
            expression = (Element) i.accept(this);
            root.appendChild(expression);
        }

        return root;
    }

    @Override
    public Object visit(IdentifierExprNode item){

        Element root = document.createElement("IdentifierExpression");
        root.appendChild(document.createTextNode(String.valueOf(item.getValue())));

        return root;

    }

    @Override
    public Object visit(BinaryExpressionNode item){

        Element left, right;
        Element root = document.createElement("BinaryExpression");


        left = (Element) item.getLeftExpression().accept(this);
        root.appendChild(left);

        Element op = document.createElement("Operation");
        op.appendChild(document.createTextNode(Symbols.terminalNames[item.getOperation()]));
        root.appendChild(op);

        right = (Element) item.getRightExpression().accept(this);
        root.appendChild(right);

        return root;
    }

    @Override
    public Object visit(MunnezzStatNode item) {
        return null;
    }

    @Override
    public Object visit(AsfnazzaCaseStatNode item) {
        return null;
    }

    @Override
    public Object visit(StepNode stepNode) {
        return null;
    }

    @Override
    public Object visit(InitNode initNode) {
        return null;
    }

    @Override
    public Object visit(InitDoForStepNode initDoForStepNode) {
        return null;
    }

    @Override
    public Object visit(ForStepNode forStepNode) {
        return null;
    }

    @Override
    public Object visit(UnaryExpressionNode item){

        Element right;
        Element root = document.createElement("UnaryExpression");

        Element op = document.createElement("Operation");
        op.appendChild(document.createTextNode(Symbols.terminalNames[item.getOperation()]));
        root.appendChild(op);

        right = (Element) item.getRightExpression().accept(this);
        root.appendChild(right);

        return root;
    }
}
