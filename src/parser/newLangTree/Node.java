package parser.newLangTree;

import java_cup.runtime.ComplexSymbolFactory.Location;

public abstract class Node {

	public Location left, right;

	public Node(Location left, Location right){
		this.left = left;
		this.right = right;
	}

	public Node(){
		left = right = null;
	}


}
