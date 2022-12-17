package semantic.symbols;

import parser.Symbols;
import semantic.SymbolTypes;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FunSymbol extends Symbol {

    public static final int SEPARATOR = -1;
    public List<Integer> typeForParams;

    /* Simbolo che rappresenta una funzione
		(int x, int y | out float somma) : void
	    -> type = LIST of INTEGER -> { sym.INT, sym.INT, -1, sym.FLOAT, -1, sym.VOID } */
    public FunSymbol(String id, List<Integer> INparamList, List<Integer> OUTparamList , Integer returnType) {
        super(id, SymbolTypes.FUNCTION);
        typeForParams = new LinkedList<>();

        if (INparamList != null)
            typeForParams.addAll(INparamList);
        typeForParams.add(SEPARATOR); // Separo param di in da param di out
        if (OUTparamList != null)
            typeForParams.addAll(OUTparamList);
        typeForParams.add(SEPARATOR); // Separo param di out da return tipo
        typeForParams.add(returnType);
    }

    public ArrayList<List<Integer>> getParamAndReturnTypes(){
        if (entryType != SymbolTypes.FUNCTION)
            return null;

        ArrayList<List<Integer>> lists = new ArrayList<>();

        int sepIn = typeForParams.indexOf(-1);
        lists.add(typeForParams.subList(0, sepIn)); //recupero valore parametri in
        int sepOut = typeForParams.lastIndexOf(-1);
        lists.add(typeForParams.subList(sepIn + 1, sepOut)); //recupero valore parametri out
        lists.add(typeForParams.subList(sepOut+1, typeForParams.size())); //Recupero valore ritorno

        return lists;
    }

    public static void main(String[] args) {
        List<Integer> in = new ArrayList<>();
        List<Integer> out = new ArrayList<>();

        in.add(1);
        in.add(2);
        out.add(3);
        out.add(4);

        List<Integer> myList = new ArrayList<>();
        myList.addAll(in);
        myList.add(SEPARATOR);
        myList.addAll(out);
        myList.add(SEPARATOR);
        myList.add(6);

        System.out.println("Test myList: " + myList);

        ArrayList<List<Integer>> lists = new ArrayList<>();
        int sep = myList.indexOf(-1);
        lists.add(myList.subList(0, sep));

        int sep2 = myList.lastIndexOf(-1);
        lists.add(myList.subList(sep + 1, sep2));
        lists.add(myList.subList(sep2+1, myList.size()));

        System.out.println("Test myList: " +lists);

    }

    public String toString() {
        String str= "Symbol{" +
                "identifier='" + identifier + '\'' +
                ", entryType=" + SymbolTypes.entryNames[entryType] +
                ", type=";

        for (int x: typeForParams){
            if(x != -1){str+= Symbols.terminalNames[x]+" X ";}
        }
        String str2="";
        str2 = str.substring(0, str.lastIndexOf("X")-1);
        str2 += "}\n";

        return str2;
    }



}
