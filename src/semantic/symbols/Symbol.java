package semantic.symbols;

import parser.Symbols;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class Symbol {

	protected String identifier;
	protected Integer entryType;


	// simbolo che rappresenta un identificatore
	public Symbol(String id,Integer entryType) {
		identifier = id;
		this.entryType = entryType;
	}

	public Integer getEntryType() {
		return entryType;
	}

	public String getIdentifier() {
		return identifier;
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
