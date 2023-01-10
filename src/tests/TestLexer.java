package tests;

import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ScannerBuffer;
import lexer.Lexer;
import java_cup.runtime.Symbol;
import parser.Symbols;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import static parser.Symbols.terminalNames;

public class TestLexer {

    private static ComplexSymbolFactory csf = new ComplexSymbolFactory();
    private static ArrayList<String> oracles = new ArrayList<>();
    private static ArrayList<String> tokenNames = new ArrayList<>();
    public static void main(String[] args) {
        try
        {
            BufferedReader readerOracle = new BufferedReader(new FileReader(args[1]));
            String line;
            ScannerBuffer lexer = new ScannerBuffer(new Lexer(new BufferedReader(new FileReader(args[0])), csf));
            Symbol token = null;

            while ((line = readerOracle.readLine()) != null) {
                oracles.add(line);
            }

            while ((token = lexer.next_token()).sym != Symbols.EOF){
                tokenNames.add(terminalNames[token.sym]);
            }

            if(oracles.size() == tokenNames.size()){
                for( int i = 0; i< oracles.size(); i++ ){
                    if(!oracles.get(i).equals(tokenNames.get(i))){
                        System.out.println(oracles.get(i)+ i+"- "+ tokenNames.get(i));
                        break;
                    }
                }
                System.out.println("TEST PASS!");
            }


        } catch (Exception e) {
            System.err.println(e);
        }
    }


}
