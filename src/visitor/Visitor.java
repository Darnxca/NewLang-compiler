package visitor;

import parser.newLangTree.nodes.*;
import parser.newLangTree.nodes.expression.*;
import parser.newLangTree.nodes.expression.constants.*;
import parser.newLangTree.nodes.statements.*;

public interface Visitor {

    public Object visit(ProgramNode item) throws Exception;
    public Object visit(DeclNode item) throws Exception;
    public Object visit(MainFuncDeclNode item) throws Exception;
    public Object visit(VarDeclNode item) throws Exception;
    public Object visit(IdInitNode item) throws Exception;
    public Object visit(IdInitObbNode item) throws Exception;
    public Object visit(FunDeclNode item)throws Exception;
    public Object visit(ParamDeclNode item) throws Exception;
    public Object visit(BodyNode item) throws Exception;
    public Object visit(IfStatNode item) throws Exception;
    public Object visit(AssignStatNode item) throws Exception;
    public Object visit(ForStatNode item) throws Exception;
    public Object visit(ReadStatNode item) throws Exception;
    public Object visit(ReturnStatNode item) throws Exception;
    public Object visit(WhileStatNode item) throws Exception;
    public Object visit(WriteStatNode item) throws Exception;
    public Object visit(IntegerConstantNode item) throws Exception;
    public Object visit(BooleanConstantNode item) throws Exception;
    public Object visit(RealConstantNode item)throws Exception;
    public Object visit(StringConstantNode item)throws Exception;
    public Object visit(CharConstantNode item)throws Exception;
    public Object visit(FunCallExprNode item)throws Exception;
    public Object visit(FunCallStatNode item)throws Exception;
    public Object visit(IdentifierExprNode item)throws Exception;
    public Object visit(UnaryExpressionNode item)throws Exception;
    public Object visit(BinaryExpressionNode item)throws Exception;
}
