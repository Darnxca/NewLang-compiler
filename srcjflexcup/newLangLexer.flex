package lexer;
import java_cup.runtime.Symbol;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.Location;
import parser.*;
import exception.LexicalError;
%%

%public
%class Lexer
%implements Symbols
%unicode
%cup
%char
%line
%column


%{
    StringBuffer string = new StringBuffer();
    StringBuffer charBuffer = new StringBuffer();

    public Lexer(java.io.Reader in, ComplexSymbolFactory sf){
	    this(in);
	    symbolFactory = sf;
    }
    ComplexSymbolFactory symbolFactory;

  private Symbol symbol(String name, int sym) {
      return symbolFactory.newSymbol(name, sym, new Location(yyline+1,yycolumn+1,(int)yychar), new Location(yyline+1,yycolumn+yylength(),(int) yychar+yylength()));
  }

  private Symbol symbol(String name, int sym, Object val) { //Terzo parametro per i terminali
      Location left = new Location(yyline+1,yycolumn+1,(int)yychar);
      Location right= new Location(yyline+1,yycolumn+yylength(), (int) yychar+yylength());
      return symbolFactory.newSymbol(name, sym, left, right,val);
  }
  private Symbol symbol(String name, int sym, Object val,int buflength) { //Quarto parametro per stringhe e caratteri
      Location left = new Location(yyline+1,yycolumn+yylength()-buflength, (int) yychar+yylength()-buflength);
      Location right= new Location(yyline+1,yycolumn+yylength(), (int) yychar+yylength());
      return symbolFactory.newSymbol(name, sym, left, right,val);
  }
  private void error(String message) {
    throw new LexicalError("Errore (riga: "+(yyline+1)+", colonna: "+(yycolumn+1)+") \n-> "+message);
  }
%}

%eofval{
     return symbolFactory.newSymbol("EOF", EOF, new Location(yyline+1,yycolumn+1,(int)yychar), new Location(yyline+1,yycolumn+1,(int)yychar+1));
%eofval}

/* formattatori di testo*/
LineTerminator =    \r|\n|\r\n
InputCharacter =    [^\r\n]
Whitespace     =    {LineTerminator} | [ \t\f]

/* commenti */
TraditionalComment  = "|*" [^*] ~"|" | "|*"+ "|"
LineComment     = "||" {InputCharacter}* {LineTerminator}?
Comment = {TraditionalComment} | {LineComment}

// letterali
underscore = [_]
letter = [a-zA-Z]
letter_$ = [$\_a-zA-Z]

// numeri
zero = [0]
digit = [0-9]
digit1 = [1-9]
digits = {digit}+


Identifier = ({letter_$}|({letter_$}+{digit}*)*)
FloatNumber = (({digit1}+{digit}* | {zero}))(\.{digits})?
IntegerNumber = (({digit1}+{digit}* | {zero}))

%state STRING_STATE

%%

{Whitespace}    { /* ignore */ }

<YYINITIAL> {

//Keywords
"start:"        {return symbol("MAIN", MAIN);}
"if"            {return symbol("IF", IF);}
"then"          {return symbol("THEN", THEN);}
"else"          {return symbol("ELSE", ELSE);}
"while"         {return symbol("WHILE", WHILE);}
"loop"          {return symbol("LOOP", LOOP);}
"to"            {return symbol("TO", TO);}
"for"           {return symbol("FOR", FOR);}
"def"           {return symbol("DEF", DEF);}
"out"           {return symbol("OUT", OUT);}
"return"        {return symbol("RETURN", RETURN);}
"true"          {return symbol("TRUE", TRUE);}
"false"         {return symbol("FALSE", FALSE);}
"init"          {return symbol("INIT", INIT);}
"do"            {return symbol("DO", DO);}
"step"          {return symbol("STEP", STEP);}


//Tipi
"integer"       {return symbol("INTEGER", INTEGER, INTEGER);}
"float"         {return symbol("FLOAT", FLOAT, FLOAT);}
"var"           {return symbol("VAR", VAR, VAR); }
"boolean"       {return symbol("BOOL", BOOL, BOOL); }
"string"        {return symbol("STRING", STRING, STRING); }
"char"          {return symbol("CHAR", CHAR, CHAR); }
"void"          {return symbol("VOID", VOID, VOID); }

//Operatori aritmetici, relazionali, ecc
"<--"           {return symbol("READ", READ); }
"-->"           {return symbol("WRITE", WRITE);}
"-->!"          {return symbol("WRITELN", WRITELN);}
"<"             {return symbol("LT", LT);}
">"             {return symbol("GT", GT);}
"<="            {return symbol("LQ", LE);}
">="            {return symbol("GQ", GE);}
"!="            {return symbol("NE", NE);}
"<>"            {return symbol("NE", NE);}
"="             {return symbol("EQ", EQ); }
"<<"            {return symbol("ASSIGN", ASSIGN); }
"+"             {return symbol("PLUS", PLUS);  }
"-"             {return symbol("MINUS", MINUS);  }
"*"             {return symbol("TIMES", TIMES);  }
"/"             {return symbol("DIV", DIV);  }
"and"           {return symbol("AND", AND);  }
"or"            {return symbol("OR", OR);  }
"not"           {return symbol("NOT", NOT);}
"^"             {return symbol("POW", POW);}
"&"             {return symbol("STR_CONCAT", STR_CONCAT);}

//Separatori
":"             {return symbol("COLON", COLON);}
";"             {return symbol("SEMI", SEMI);}
","             {return symbol("COMMA", COMMA); }
"|"             {return symbol("PIPE", PIPE);}
"("             {return symbol("LPAR", LPAR); }
")"             {return symbol("RPAR", RPAR); }
"{"             {return symbol("LBRAC", LBRAC); }
"}"             {return symbol("RBRAC", RBRAC); }




    /* identifiers */
    {Identifier}        { return symbol("ID", ID,  yytext()); }

    /*numeri*/
    {IntegerNumber}      {return symbol("INTEGER_CONST", INTEGER_CONST, Integer.parseInt(yytext()));}
    {FloatNumber}        {return symbol("REAL_CONST", REAL_CONST, Float.parseFloat(yytext())); }

    /* Stringhe letterali */
    \"                  { string.setLength(0); yybegin(STRING_STATE); }
    //Caratteri
    \'.\'                { return symbol("CHAR_CONST", CHAR_CONST, yytext().charAt(1), 1); }

    /* Commenti */
    {Comment} { /* ignore */}

    /* Whitespace */
    {Whitespace} { /* ignore */ }

}
<STRING_STATE> {
    \"              {yybegin(YYINITIAL); return symbol("STRING_CONST",STRING_CONST, string.toString(), string.length()); }
    [^\n\r\"\\]+    { string.append( yytext() ); }
    \\t             { string.append('\t'); }
    \\n             { string.append('\n'); }
    \\r             { string.append('\r'); }
    \\\"            { string.append('\"'); }
    \\              { string.append('\\'); }
    <<EOF>>         {yybegin(YYINITIAL); error("La stringa non è stata correttamente chiusa!! ;)");}
}



/* error fallback */
[^]             {
		    error("Questo carattere ("+ yytext()+") potrebbe essere un futuro simbolo del linguaggio... ma non lo è oggi! :( ");
                  }
