package parser.newLangTree;

import java_cup.runtime.ComplexSymbolFactory.Location;

public abstract class SingleTypeNode extends Node {


	private Integer type;

	public SingleTypeNode(Location left, Location right){
		super(left, right);
		this.type = null;
	}

	public SingleTypeNode(){
		super();
		this.type = null;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
