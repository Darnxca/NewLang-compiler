package parser.newLangTree.statements;

import java_cup.runtime.ComplexSymbolFactory;
import parser.newLangTree.TypeNode;
import visitor.Visitable;
import visitor.Visitor;

public abstract class StatementNode extends TypeNode implements Visitable {

    public StatementNode(ComplexSymbolFactory.Location left, ComplexSymbolFactory.Location right) {
        super(left, right);
    }

    public StatementNode() {
    }

    @Override
    public abstract Object accept(Visitor v);
}
