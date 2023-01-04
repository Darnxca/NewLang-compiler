package visitor;

import parser.Symbols;
import parser.newLangTree.nodes.statements.*;

public class TypeChecker {

    public static int checkUnaryExpr(Integer operation, Integer type){

        if(operation == Symbols.MINUS){
            if(type == Symbols.INTEGER ){
                return Symbols.INTEGER;
            }else if(type == Symbols.FLOAT){
                return Symbols.FLOAT;
            }
        }else if(operation == Symbols.NOT){
            if(type == Symbols.BOOL){
                return Symbols.BOOL;
            }
        }

        return -1; //Errore
    }



    public static int checkBinaryExpr(Integer operation, Integer typeLeft, Integer typeRight){

        if(isAritmeticOperation(operation)){
            if(typeLeft == Symbols.INTEGER && typeRight == Symbols.INTEGER){
                return Symbols.INTEGER;
            }
            else if( typeLeft == Symbols.FLOAT && typeRight == Symbols.FLOAT){
                return Symbols.FLOAT;
            }
            else if( typeLeft == Symbols.INTEGER && typeRight == Symbols.FLOAT){
                return Symbols.FLOAT;
            }
            else if( typeLeft == Symbols.FLOAT && typeRight == Symbols.INTEGER){
                return Symbols.FLOAT;
            }

        }
        else if( operation == Symbols.STR_CONCAT){
            if(typeLeft == Symbols.STRING && typeRight == Symbols.STRING){
                return Symbols.STRING;
            }

        } else if(isMathOperation(operation)){
            if(typeLeft == Symbols.INTEGER && typeRight == Symbols.INTEGER){
                return Symbols.FLOAT;
            }
            else if( typeLeft == Symbols.FLOAT && typeRight == Symbols.FLOAT){
                return Symbols.FLOAT;
            }
            else if( typeLeft == Symbols.INTEGER && typeRight == Symbols.FLOAT){
                return Symbols.FLOAT;
            }
            else if( typeLeft == Symbols.FLOAT && typeRight == Symbols.INTEGER){
                return Symbols.FLOAT;
            }
        }
        else if(isLogicalOp(operation)){
            if(typeLeft == Symbols.BOOL && typeRight == Symbols.BOOL){
                return Symbols.BOOL;
            }

        }
        else if (isRelationshipOp(operation)) {
            if(typeLeft == Symbols.INTEGER && typeRight == Symbols.INTEGER){
                return Symbols.BOOL;
            }
            else if( typeLeft == Symbols.FLOAT && typeRight == Symbols.FLOAT){
                return Symbols.BOOL;
            }
            else if( typeLeft == Symbols.INTEGER && typeRight == Symbols.FLOAT){
                return Symbols.BOOL;
            }
            else if( typeLeft == Symbols.FLOAT && typeRight == Symbols.INTEGER){
                return Symbols.BOOL;
            }

        } else if (operation == Symbols.ASSIGN){

            if(typeLeft == Symbols.INTEGER && typeRight == Symbols.INTEGER){
                return Symbols.INTEGER;
            }
            else if( typeLeft == Symbols.FLOAT && typeRight == Symbols.FLOAT){
                return Symbols.FLOAT;
            }
            else if( typeLeft == Symbols.INTEGER && typeRight == Symbols.FLOAT){
                return Symbols.INTEGER;
            }
            else if( typeLeft == Symbols.FLOAT && typeRight == Symbols.INTEGER){
                return Symbols.FLOAT;
            }
            else if( typeLeft == Symbols.CHAR && typeRight == Symbols.CHAR) {
                return Symbols.CHAR;
            }
            else if( typeLeft == Symbols.STRING && typeRight == Symbols.STRING) {
                return Symbols.STRING;
            }
            else if( typeLeft == Symbols.STRING && typeRight == Symbols.CHAR) {
                return Symbols.STRING;
            }
            else if( typeLeft == Symbols.BOOL && typeRight == Symbols.BOOL){
                return  Symbols.BOOL;
            }
        }


        return -1;
    }

    public static boolean checkCallParamTypes(int typeLeft, int typeRight){
        if(typeLeft == typeRight ){
            return true;
        } else if(typeLeft == Symbols.INTEGER && typeRight == Symbols.FLOAT){
            return true;
        } else if(typeLeft == Symbols.FLOAT && typeRight == Symbols.INTEGER){
            return true;
        }
        return false;
    }

    public static boolean isMathOperation(int operation){
        return operation == Symbols.POW;
    }
    private static boolean isAritmeticOperation(int operation){
        return operation == Symbols.PLUS || operation == Symbols.MINUS
                || operation == Symbols.TIMES || operation == Symbols.DIV;
    }

    private static boolean isRelationshipOp(int operation){
        return operation == Symbols.GT || operation == Symbols.GE || operation == Symbols.LT
                || operation == Symbols.LE || operation == Symbols.EQ || operation == Symbols.NE;
    }

    private static boolean isLogicalOp(int operation){
        return operation == Symbols.AND || operation == Symbols.OR;
    }

    public static boolean checkReturnType(Integer typeLeft, Integer typeRight){
        if(typeLeft == typeRight ){
            return true;
        }
        else if( typeLeft == Symbols.FLOAT && typeRight == Symbols.FLOAT){
            return true;
        }
        else if( typeLeft == Symbols.INTEGER && typeRight == Symbols.FLOAT){
            return true;
        }
        else if( typeLeft == Symbols.FLOAT && typeRight == Symbols.INTEGER){
            return true;
        }
        return false;
    }
    public static boolean checkAllTypeReturn(StatementNode stmt, int funType){
        // se è uno stat di ritorno controllo il suo eventuale tipo, altrimenti eseguo ricorsivamente un controllo sugli altri costrutti
        if(stmt instanceof ReturnStatNode) {
            if (TypeChecker.checkReturnType(stmt.getType(), funType)) {
                return true;
            }else {
                return false;
            }
        }else {
            if (stmt instanceof IfStatNode) {
                IfStatNode ifstat = (IfStatNode) stmt;
                for (StatementNode x : ifstat.getBodyThen().getStmtNodeList())
                    if(!checkAllTypeReturn(x, funType)){
                        return false;
                    }
                if (ifstat.getBodyElse() != null) {
                    for (StatementNode x : ifstat.getBodyElse().getStmtNodeList())
                        if(!checkAllTypeReturn(x, funType)){
                            return false;
                        }
                }
            } else if (stmt instanceof ForStatNode) {
                ForStatNode forstat = (ForStatNode) stmt;
                for (StatementNode x : forstat.getBody().getStmtNodeList())
                    if(!checkAllTypeReturn(x, funType)){
                        return false;
                    }
            } else if (stmt instanceof WhileStatNode) {
                WhileStatNode whilestat = (WhileStatNode) stmt;
                for (StatementNode x : whilestat.getBody().getStmtNodeList())
                    if(!checkAllTypeReturn(x, funType)){
                        return false;
                    }
            }
            return true;
        }
    }

}
