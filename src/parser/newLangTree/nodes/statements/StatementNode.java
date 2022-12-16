package parser.newLangTree.nodes.statements;

import java_cup.runtime.ComplexSymbolFactory;
import parser.newLangTree.SingleTypeNode;
import visitor.Visitable;
import visitor.Visitor;

public abstract class StatementNode extends SingleTypeNode implements Visitable {

    public StatementNode(ComplexSymbolFactory.Location left, ComplexSymbolFactory.Location right) {
        super(left, right);
    }

    public StatementNode() {
    }

    @Override
    public abstract Object accept(Visitor v) throws Exception;
}
