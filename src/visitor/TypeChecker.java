package visitor;

import parser.Symbols;

public class TypeChecker {

    public static int checkUnaryExpr(Integer operation, Integer type){

        if(operation == Symbols.MINUS){
            if(type == Symbols.INTEGER ){
                return Symbols.INTEGER;
            }
            if(type == Symbols.FLOAT){
                return Symbols.FLOAT;
            }

        }

        if(operation == Symbols.NOT){
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

    private static boolean isAritmeticOperation(int operation){
        return operation == Symbols.PLUS || operation == Symbols.MINUS
                || operation == Symbols.TIMES || operation == Symbols.DIV || operation == Symbols.POW;
    }

    private static boolean isRelationshipOp(int operation){
        return operation == Symbols.GT || operation == Symbols.GE || operation == Symbols.LT
                || operation == Symbols.LE || operation == Symbols.EQ || operation == Symbols.NE;
    }

    private static boolean isLogicalOp(int operation){
        return operation == Symbols.AND || operation == Symbols.OR;
    }


}
