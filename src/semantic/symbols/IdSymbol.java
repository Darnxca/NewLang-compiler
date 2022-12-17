package semantic.symbols;

import parser.Symbols;
import semantic.SymbolTypes;

public class IdSymbol extends Symbol {

    private Integer type;
    public IdSymbol(String id, Integer type) {
        super(id, SymbolTypes.VAR);
        this.type = type;
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
