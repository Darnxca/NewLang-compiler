package visitor;

public interface Visitable {
    public Object accept(Visitor v);
}
