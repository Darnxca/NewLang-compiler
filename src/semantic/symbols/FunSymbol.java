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

    @Override
    public String toString() {
        String str= "Symbol{" +
                "identifier='" + identifier + '\'' +
                ", entryType=" + SymbolTypes.entryNames[entryType] +
                ", type= (";

        String str2="";
        if (typeOfParam.size() > 0) {
            for (int i= 0; i < typeOfParam.size(); i ++) {
                str += Symbols.terminalNames[typeOfParam.get(i)] + " X ";
            }
            str2 = str.substring(0, str.lastIndexOf("X"));
            str2 += ")";
        }else{
            str += ")";
            str2 = str;
        }

        str2 += " --> "+ Symbols.terminalNames[returnType];

        str2 += "}\n";

        return str2;
    }





}
