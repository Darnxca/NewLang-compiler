package main.java;

import exception.FileExtensionNotMatch;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ScannerBuffer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Document;
import parser.newLangTree.nodes.ProgramNode;
import visitor.*;
import parser.*;
import lexer.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class NewLangTranspiler {
    private static ComplexSymbolFactory csf = new ComplexSymbolFactory();

    public static void main(String[] args) throws ParserConfigurationException {

        String filename = isNewLangFile(new File(args[0]));
        String secondoparametro = "";

        if (args.length == 1){
            secondoparametro ="-nsp";
        }else{
            secondoparametro = args[1];
        }

        List<String> opzioniAggiuntive = Arrays.asList(secondoparametro.split("-"));

        try {
            ScannerBuffer lexer = new ScannerBuffer(new Lexer(new BufferedReader(new FileReader(args[0])), csf));

            // start parsing
            Parser p = new Parser(lexer, csf);

            ProgramNode program = (ProgramNode) p.parse().value;

            if (opzioniAggiuntive.contains("xml")){
                XMLTreeGenerator tr = new XMLTreeGenerator();
                Document xml = (Document) program.accept(tr);
                generaXML(xml, filename);
            }

            ScopeVisitor sv = new ScopeVisitor();
            program.accept(sv);

            if (opzioniAggiuntive.contains("sp")){
                PrintScopeVisitor psv = new PrintScopeVisitor();
                program.accept(psv);
            }

            SemanticVisitor sm = new SemanticVisitor();
            program.accept(sm);

            CGenVisitor cg = new CGenVisitor(filename);
            program.accept(cg);
            cg.flush();

            System.out.println("---> Linguaggio riconosciuto!!");

        } catch (Exception e) {
            System.err.println(e);
        }

    }



    public static String isNewLangFile(File file){
        String filename = file.getName();
        String regex = "(?:^.+\\.(nl)$)";
        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(filename);

        if (matcher.find())
            return filename.split("\\.nl")[0];
        else
            throw new FileExtensionNotMatch("Errore nella lettura del file \n -> Il file "+ filename + " non ha estensione newLang");
    }

    private static void generaXML(Document document, String filename){
        String xmlFilePath = "XMLTreeCode/"+filename+".xml";
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");


            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(xmlFilePath));

            transformer.transform(domSource, streamResult);

            System.out.println("File Xml creato!");

        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }


}
