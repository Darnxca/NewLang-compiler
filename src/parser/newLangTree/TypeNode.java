package parser.newLangTree;

import java_cup.runtime.ComplexSymbolFactory.Location;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public abstract class TypeNode {

	private Location left, right;
	private List<Integer> type;

	public TypeNode(Location left, Location right){
		this.left = left;
		this.right = right;
		this.type = new LinkedList<>();
	}

	public TypeNode(){
		this.left = this.right  = null;
		this.type = new LinkedList<>();
	}

	public Location getLeft() {
		return left;
	}

	public void setLeft(Location left) {
		this.left = left;
	}

	public Location getRight() {
		return right;
	}

	public void setRight(Location right) {
		this.right = right;
	}

	public List<Integer> getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type.add(type);
	}
}
