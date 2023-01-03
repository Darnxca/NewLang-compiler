package codeGenerator;

import semantic.VarTypes;
import semantic.symbols.FunSymbol;

import java.io.PrintWriter;

public class CodeGenerator {

    private static PrintWriter writer;

    public static void setWriter(PrintWriter writer) {
        CodeGenerator.writer = writer;
    }

    public static void generaLibrerie(){
        writer.println("#include <stdio.h>");
        writer.println("#include <string.h>");
        writer.println("#include <math.h>");
        writer.println("");
    }
    public static void generaPrototipoFunzione(FunSymbol fs){
        writer.print(fs.getTypeFromToken(fs.getReturnType()) +
                " "+ fs.getIdentifier()+"(");
        for(int i = 0; i< fs.getTypeOfParam().size(); i++){
            if(fs.getInOrOut().get(i) == VarTypes.OUT){
                if(i != fs.getTypeOfParam().size()-1){
                    writer.print(fs.getTypeFromToken(fs.getTypeOfParam().get(i))+" *"+ VariableGenerator.generaNomeVariabile()+", ");
                }
                else {
                    writer.print(fs.getTypeFromToken(fs.getTypeOfParam().get(i))+" *"+ VariableGenerator.generaNomeVariabile());

                }

            }
            else {
                if(i != fs.getTypeOfParam().size()-1){
                    writer.print(fs.getTypeFromToken(fs.getTypeOfParam().get(i))+" "+ VariableGenerator.generaNomeVariabile()+ ",");
                }
                else {
                    writer.print(fs.getTypeFromToken(fs.getTypeOfParam().get(i))+" "+ VariableGenerator.generaNomeVariabile());
                }

            }
        }
        writer.println(");");

    }

}
