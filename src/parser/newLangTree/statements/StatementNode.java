package parser.newLangTree.statements;

import java_cup.runtime.ComplexSymbolFactory;
import parser.newLangTree.Node;
import parser.newLangTree.visitor.Visitable;
import parser.newLangTree.visitor.Visitor;

public abstract class StatementNode extends Node implements Visitable {

    public StatementNode(ComplexSymbolFactory.Location left, ComplexSymbolFactory.Location right) {
        super(left, right);
    }

    public StatementNode() {
    }

    @Override
    public abstract Object accept(Visitor v);
}
