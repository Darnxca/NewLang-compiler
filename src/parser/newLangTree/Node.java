package parser.newLangTree;

import java_cup.runtime.ComplexSymbolFactory.Location;

public abstract class Node {

    private Location left, right;

    public Node(Location left, Location right){
        this.left = left;
        this.right = right;
    }

    public Node(){
        this.left = null;
        this.right = null;
    }

    public Location getLeft() {
        return left;
    }

    public Location getRight() {
        return right;
    }
}
