import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ScannerBuffer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.regex.Pattern;

import parser.newLangTree.*;
import parser.newLangTree.visitor.XMLTreeGenerator;
import org.w3c.dom.Document;
import parser.*;
import lexer.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class Tester {
    private static ComplexSymbolFactory csf = new ComplexSymbolFactory();
    public static void main(String[] args) throws ParserConfigurationException {

        try {
            String[] filename = args[0].split(Pattern.quote("."))[0].split(Pattern.quote("/"));
            //Usato per restituire eventuali errori lessicali
            lessicalError(args[0]);

            try{
                ScannerBuffer lexer = new ScannerBuffer(new Lexer(new BufferedReader(new FileReader(args[0])), csf));
                // start parsing
                Parser p = new Parser(lexer, csf);

                ProgramNode program = (ProgramNode) p.parse().value;
                XMLTreeGenerator tr  = new XMLTreeGenerator(filename[1]);

                program.accept(tr);
                tr.flush();

                System.out.println("linguaggio riconosciuto");

            } catch (Exception e){
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }



    }

    private static void lessicalError(String file) throws Exception {
        FileReader inputFile = new FileReader(file);
        ScannerBuffer lexer = new ScannerBuffer(new Lexer(new BufferedReader(inputFile), csf));

        while ((lexer.next_token()).sym != Symbols.EOF){}
    }

    private static void xmlTreeGenerator(Document document){
        String xmlFilePath = "xmlFileToyProgram.xml";
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(xmlFilePath));

            transformer.transform(domSource, streamResult);

            System.out.println("File Xml creato!");

        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

}
