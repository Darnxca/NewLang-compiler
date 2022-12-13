package semantic;

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
		this.type.add(type);
	}

	// simbolo che rappresenta una funzione
	public Symbol(String id, List<Integer> INparamList, List<Integer> OUTparamList , Integer returnType) {
		identifier = id;
		this.entryType = SymbolTypes.FUNCTION;
		type = new LinkedList<>();
		
		if (INparamList != null)
			type.addAll(INparamList);
		type.add(SEPARATOR); // usato per separare i tipi dei parametri in input con quelli per riferimento
		if (OUTparamList != null)
			type.addAll(OUTparamList);
		type.add(SEPARATOR); // usato per separare i tipi dei parametri con il tipo di ritorno ritorno
		type.add(returnType);
	}

	public ArrayList<List<Integer>> getParamAndReturnTypes(){
		if (entryType != SymbolTypes.FUNCTION)
			return null;

		ArrayList<List<Integer>> lists = new ArrayList<>();

		int sep = type.indexOf(-1);
		lists.add(type.subList(0, sep));
		lists.add(type.subList(sep + 1, type.size()));

		return lists;
	}

	public Integer getType(){
		if (entryType != SymbolTypes.VAR)
			return null;

		return type.get(0);
	}
	
}
