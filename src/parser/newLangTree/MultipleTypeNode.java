package parser.newLangTree;

import java_cup.runtime.ComplexSymbolFactory.Location;
import java.util.LinkedList;
import java.util.List;

public abstract class MultipleTypeNode extends Nodes { //Applicabile al caso dei parametri di una funzione che possonno avere diversi tipi

    private List<Integer> typeList; //Avrò più tipi

    public MultipleTypeNode(){
        super();
        this.typeList = new LinkedList<>();
    }

    public MultipleTypeNode(Location left, Location right){
        super(left, right);
        this.typeList = new LinkedList<>();
    }

    public Integer getType(){
        return this.typeList.get(0);
    }

    private void setTypeList(Integer typeList) {
        this.typeList.add(typeList);
    }

    public void setType(Integer type){
        this.typeList.add(type);
    }

}
