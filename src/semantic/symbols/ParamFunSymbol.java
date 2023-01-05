package semantic.symbols;

public class ParamFunSymbol extends Symbol {

    private String nameOfParam;
    private Integer typeOfParam; //Tipo del parametro come simbolo nella tabella (in: parametro input, out: parametro out)
    private int primitiveTypeOfParam; //Tipo primitivo del parametro (int, float, ecc)

    public ParamFunSymbol(String id, Integer entryType, int primitiveTypeOfParam) {
        super(id, entryType);
        this.primitiveTypeOfParam = primitiveTypeOfParam;
        this.typeOfParam = VarTypes.IN;
    }

    public ParamFunSymbol(String id, Integer entryType, int primitiveTypeOfParam, int typeOfParam) {
        super(id, entryType);
        this.primitiveTypeOfParam = primitiveTypeOfParam;
        this.typeOfParam = typeOfParam;
    }

    public int getPrimitiveTypeOfParam() {
        return primitiveTypeOfParam;
    }

    public boolean isOut() {
        return this.typeOfParam == VarTypes.OUT;
    }

}
