package main.java;

import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ScannerBuffer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.regex.Pattern;

import parser.newLangTree.nodes.ProgramNode;
import parser.newLangTree.nodes.statements.IfStatNode;
import semantic.SymbolTable;
import visitor.ScopeVisitor;
import visitor.SemanticVisitor;
import visitor.SemanticVisitorTest;
import visitor.XMLTreeGenerator;
import parser.*;
import lexer.*;

import javax.xml.parsers.ParserConfigurationException;

public class Tester {
    private static ComplexSymbolFactory csf = new ComplexSymbolFactory();
    public static void main(String[] args) throws ParserConfigurationException {

        try {
            String[] filename = args[0].split(Pattern.quote("."))[0].split(Pattern.quote("/"));
            //Usato per restituire eventuali errori lessicali
            lessicalError(args[0]);

                ScannerBuffer lexer = new ScannerBuffer(new Lexer(new BufferedReader(new FileReader(args[0])), csf));
                // start parsing
                Parser p = new Parser(lexer, csf);

                ProgramNode program = (ProgramNode) p.parse().value;
                XMLTreeGenerator tr  = new XMLTreeGenerator(filename[1]);

                program.accept(tr);
                tr.flush();

                ScopeVisitor sv = new ScopeVisitor();
                program.accept(sv);

                SemanticVisitorTest sm = new SemanticVisitorTest();
                program.accept(sm);


                System.out.println("Linguaggio riconosciuto");



        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            System.err.println(e);
        }

    }

    private static void lessicalError(String file) throws Exception {
        FileReader inputFile = new FileReader(file);
        ScannerBuffer lexer = new ScannerBuffer(new Lexer(new BufferedReader(inputFile), csf));

        while ((lexer.next_token()).sym != Symbols.EOF){}
    }

}
