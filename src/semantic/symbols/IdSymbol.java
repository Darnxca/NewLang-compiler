package semantic.symbols;

import parser.Symbols;

public class IdSymbol extends Symbol {

    private Integer type;
    private boolean isPointer; //Utilizzato per la gestione dei puntatori
    private boolean isParameter; //Utilizzato per la gestione dei puntatori


    public IdSymbol(String id, Integer type) {
        super(id, SymbolTypes.VAR);
        this.type = type;
        this.isPointer = false;
        this.isParameter = false;
    }

    public IdSymbol(String id, Integer type, boolean isPointer, boolean isParameter){
        super(id, SymbolTypes.VAR);
        this.type = type;
        this.isPointer = isPointer;
        this.isParameter = isParameter;
    }
    public boolean isParameter() {
        return isParameter;
    }

    public boolean isPointer() {
        return isPointer;
    }

    public Integer getType(){
        if (entryType != SymbolTypes.VAR)
            return null;

        return this.type;
    }


    @Override
    public String toString() {
        String str= "Symbol{" +
                "identifier='" + identifier + '\'' +
                ", entryType=" + SymbolTypes.entryNames[entryType] +
                ", type=";

        str+= Symbols.terminalNames[type]+" X ";
        String str2="";
        str2 = str.substring(0, str.lastIndexOf("X")-1);
        str2 += "}\n";

        return str2;
    }
}
