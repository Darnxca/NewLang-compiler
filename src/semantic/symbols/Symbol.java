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
}
