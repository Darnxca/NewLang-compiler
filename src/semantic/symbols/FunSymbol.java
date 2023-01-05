package semantic.symbols;

import parser.Symbols;

import java.util.List;

public class FunSymbol extends Symbol {

    public static final int SEPARATOR = -1;
    private List<ParamFunSymbol> listOfParams; //per specificare il tipo primitivo del parametro es. Int, float, bool, ecc
    private Integer returnType;


    public FunSymbol(String id, List<ParamFunSymbol> listOfParams, Integer returnType) {
        super(id, SymbolTypes.FUNCTION);
        this.listOfParams = listOfParams;
        this.returnType = returnType;
    }

    public List<ParamFunSymbol> getListOfParams() {
        return listOfParams;
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
        if (listOfParams.size() > 0) {
            for (int i = 0; i < listOfParams.size(); i ++) {
                str += Symbols.terminalNames[listOfParams.get(i).getPrimitiveTypeOfParam()] + " X ";
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
