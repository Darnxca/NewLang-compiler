package visitor;

import parser.newLangTree.nodes.*;
import parser.newLangTree.nodes.expression.*;
import parser.newLangTree.nodes.expression.constants.*;
import parser.newLangTree.nodes.statements.*;

public interface Visitor {

    public Object visit(ProgramNode item);
    public Object visit(DeclNode item);
    public Object visit(MainFuncDeclNode item);
    public Object visit(VarDeclNode item);
    public Object visit(IdInitNode item);
    public Object visit(IdInitObbNode item);
    public Object visit(FunDeclNode item);
    public Object visit(ParamDeclNode item);
    public Object visit(BodyNode item);
    public Object visit(IfStatNode item);
    public Object visit(AssignStatNode item);
    public Object visit(ForStatNode item);
    public Object visit(ReadStatNode item);
    public Object visit(ReturnStatNode item);
    public Object visit(WhileStatNode item);
    public Object visit(WriteStatNode item);
    public Object visit(IntegerConstantNode item);
    public Object visit(BooleanConstantNode item);
    public Object visit(RealConstantNode item);
    public Object visit(StringConstantNode item);
    public Object visit(CharConstantNode item);
    public Object visit(FunCallExprNode item);
    public Object visit(FunCallStatNode item);
    public Object visit(IdentifierExprNode item);
    public Object visit(UnaryExpressionNode item);
    public Object visit(BinaryExpressionNode item);
    public Object visit(MunnezzStatNode item);
    public Object visit(AsfnazzaCaseStatNode item);
}
