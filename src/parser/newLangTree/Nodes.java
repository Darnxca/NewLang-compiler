package parser.newLangTree;

import java_cup.runtime.ComplexSymbolFactory.Location;

public abstract class Nodes {

    private Location left, right;

    public Nodes(Location left, Location right){
        this.left = left;
        this.right = right;
    }

    public Nodes(){
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
