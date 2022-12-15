package semantic;

import parser.Symbols;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Symbol {

	public static final int SEPARATOR = -1;
	
	public String identifier;
	public Integer entryType;
	public List<Integer> type;

	// simbolo che rappresenta un identificatore
	public Symbol(String id,Integer entryType, Integer type) {
		identifier = id;
		this.entryType = entryType;
		this.type = new LinkedList<>();
		this.type.add(type);
	}

	/* Simbolo che rappresenta una funzione
		(int x, int y | out float somma) : void
	    -> type = LIST of INTEGER -> { sym.INT, sym.INT, -1, sym.FLOAT, -1, sym.VOID } */
	public Symbol(String id, List<Integer> INparamList, List<Integer> OUTparamList , Integer returnType) {
		identifier = id;
		this.entryType = SymbolTypes.FUNCTION;
		type = new LinkedList<>();
		
		if (INparamList != null)
			type.addAll(INparamList);
		type.add(SEPARATOR); // Separo param di in da param di out
		if (OUTparamList != null)
			type.addAll(OUTparamList);
		type.add(SEPARATOR); // Separo param di out da return tipo
		type.add(returnType);
	}

	public ArrayList<List<Integer>> getParamAndReturnTypes(){
		if (entryType != SymbolTypes.FUNCTION)
			return null;

		ArrayList<List<Integer>> lists = new ArrayList<>();

		int sepIn = type.indexOf(-1);
		lists.add(type.subList(0, sepIn)); //recupero valore parametri in
		int sepOut = type.lastIndexOf(-1);
		lists.add(type.subList(sepIn + 1, sepOut)); //recupero valore parametri out
		lists.add(type.subList(sepOut+1, type.size())); //Recupero valore ritorno

		return lists;
	}

	public Integer getType(){
		if (entryType != SymbolTypes.VAR)
			return null;

		return type.get(0);
	}

	@Override
	public String toString() {
		String str= "Symbol{" +
				"identifier='" + identifier + '\'' +
				", entryType=" + SymbolTypes.entryNames[entryType] +
				", type=";

		for (int x: type){
			if(x != -1){str+= Symbols.terminalNames[x]+" X ";}
		}
		String str2="";
		str2 = str.substring(0, str.lastIndexOf("X")-1);
		str2 += "}\n";

		return str2;
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
	
}
