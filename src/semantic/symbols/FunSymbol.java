package semantic.symbols;

import parser.Symbols;
import semantic.SymbolTypes;
import semantic.VarTypes;

import java.util.ArrayList;
import java.util.List;

public class FunSymbol extends Symbol {

    public static final int SEPARATOR = -1;
    private List<Integer> typeOfParam; //per specificare il tipo primitivo del parametro es. Int, float, bool, ecc
    private List<Integer> inOrOut; //per specificare che il parametro della funzione è di in o out
    private Integer returnType;


    public FunSymbol(String id, List<Integer> typeOfParam, List<Integer> inOrOut, Integer returnType) {
        super(id, SymbolTypes.FUNCTION);
        this.typeOfParam = typeOfParam;
        this.inOrOut = inOrOut;
        this.returnType = returnType;
    }

    public List<Integer> getTypeOfParam() {
        return typeOfParam;
    }

    public List<Integer> getInOrOut() {
        return inOrOut;
    }

    public Integer getReturnType() {
        return returnType;
    }

    public String getTypeFromToken(int token){
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




}
