# Esercitazione 4

Implementazione della Grammatica di newLang e creazione dell'albero sintattico.

## Grammatica originale
( \
N = {Program, DeclList, MainFunDecl, VarDecl, Type, IdInitList,IdInitObblList,
Const, FunDecl, Body, ParamDeclList, NonEmptyParamDeclList, ParDecl,
TypeOrVoid, VarDeclList, StatList, Stat, IfStat, Else, WhileStat, ReadStat, IdList,
WriteStat, AssignStat, FunCall, ExprList, Expr},

T = {MAIN, DEF, SEMI, COMMA, LPAR, RPAR, COLON, LBRAC, RBRAC, PIPE, OUT,
STRING, CHAR, VOID, VAR, INTEGER, BOOL, FLOAT, FOR, TO, IF, WHILE,
ELSE, READ, WRITE, WRITELN, THEN, LOOP, ASSIGN,
PLUS, MINUS, TIMES, DIV, AND, OR, NOT, POW, STR_CONCAT, STRING_CONST, ID,
INTEGER_CONST, REAL_CONST, CHAR_CONST, TRUE, FALSE, GT, GE, LT, LE,
EQ, NE, RETURN},

S = {Program}

P = {

          Program -> DeclList MainFunDecl DeclList
          ;
          DeclList -> VarDecl DeclList | FunDecl DeclList | /* empty */
          ;
          MainFunDecl -> MAIN FunDecl
          ;
          VarDecl ::= Type IdInitList SEMI
                      | VAR IdInitObblList SEMI
          ;
          Type ::= INTEGER | BOOL | FLOAT | STRING | CHAR
          ;
          IdInitList ::= ID
                        | IdInitList COMMA ID
                        | ID ASSIGN Expr
                        | IdInitList COMMA ID ASSIGN Expr
          ;
          IdInitObblList ::= ID ASSIGN Const
                          | IdInitObblList COMMA ID ASSIGN Const
          ;
          Const ::= INTEGER_CONST | REAL_CONST | TRUE | FALSE | STRING_CONST | CHAR_CONST
          ;
          FunDecl -> DEF ID LPAR ParamDeclList RPAR COLON TypeOrVoid Body
          ;
          Body -> LBRAC VarDeclList StatList RBRAC
          ;
          ParamDeclList ::= /*empty */
                          | NonEmptyParamDeclList
          ;
          NonEmptyParamDeclList ::= ParDecl
                                  | NonEmptyParamDeclList PIPE ParDecl
          ;
          ParDecl ::= Type IdList
                    | OUT Type IdList
          ;
          TypeOrVoid ::= Type | VOID
          ;
          VarDeclList ::= /* empty */
                        | VardDecl VarDeclList
          ;
          StatList ::= Stat
                      | Stat StatList
          ;
          Stat ::= IfStat
                  | ForStat
                  | ReadStat SEMI
                  | WriteStat SEMI
                  | AssignStat SEMI
                  | WhileStat
                  | FunCall SEMI
                  | RETURN Expr SEMI
                  | RETURN SEMI
                  | /* empty */
          ;
          IfStat ::= IF Expr THEN Body Else
          ;
          Else ::= /* empty */
                  | ELSE Body
          ;
          WhileStat ::= WHILE Expr LOOP Body
          ;
          ForStat ::= FOR ID ASSIGN INTEGER_CONST TO INTEGER_CONST LOOP Body
          ;
          ReadStat ::= IdList READ STRING_CONST
                    |  IdList READ
          ;
          IdList ::= ID
                    | IdList COMMA ID
          ;
          WriteStat ::=  LPAR ExprList RPAR WRITE
                      |  LPAR ExprList RPAR WRITELN

          ;
          AssignStat ::=  IdList ASSIGN ExprList
          ;  
          FunCall ::= ID LPAR ExprList RPAR   
                    | ID LPAR RPAR
          ;
          ExprList ::= Expr
                    | Expr COMMA ExprList
          ;
          Expr ::= TRUE                            
                  | FALSE                           
                  | INTEGER_CONST                    
                  | REAL_CONST
                  | STRING_CONST
                  | CHAR_CONST
                  | ID
                  | FunCall
                  | Expr  PLUS Expr
                  | Expr  MINUS Expr
                  | Expr  TIMES Expr
                  | Expr  DIV Expr
                  | Expr  AND Expr
                  | Expr POW Expr
                  | Expr STR_CONCAT Expr
                  | Expr  OR Expr
                  | Expr  GT Expr
                  | Expr  GE Expr
                  | Expr  LT Expr
                  | Expr  LE Expr
                  | Expr  EQ Expr
                  | Expr  NE Expr
                  | MINUS Expr
                  | NOT Expr
                  | LPAR Expr RPAR
          ;
)
### Modifiche alla grammatica
Le modifiche apportate alla grammatica sono di tre tipi:
- Per risolvere conflitti shift/reduce;
- Per risolvere conflitti reduce/reduce;
- Per ottimizzare la costruzione dell'albero.

#### Conflitti shift/reduce
Per risolvere i conflitti shift/reduce che si verificavano nelle produzioni
del non terminale Expr, non abbiamo modificato veramente la grammatica,
abbiamo utilizzato le precedenze:
- precedence left AND;
- precedence left OR;
- precedence left PLUS;
- precedence nonassoc LT, GT, LE, GE, EQ, NE;
- precedence right ASSIGN;
- precedence right MINUS;
- precedence left TIMES, DIV, POW;
- precedence left  STR_CONCAT.

#### Conflitti reduce/reduce
La risoluzione di questi conflitti ha portato delle modifiche alla grammatica:
- Nel non terminale Stat è stata eliminata la produzione che andava a vuoto,
  questo ha portato ad avere nel non terminale Body un'altra produzione senza StatList;
- Nel non terminale VarDecList è stata eliminata la produzione che andava
  a vuoto, questo ha portato da avere in VarDecList una produzione che va solo a VarDecl,
  è stata aggiunta anche una produzione al non terminale Body in cui non è
  presente VarDecList;
- Visto che sia StatList e VarDecList possono scomparire nel non terminale
  Body è stata aggiunta una produzione in cui non sono presenti;
- Il non terminale Else è stato eliminato e la sua produzione è stata portata
  Nel non terminale IfStat;
- A FunDecl è stato aggiunta una produzione senza ParamDeclList visto che
  quest'ultima poteva scomparire.

#### Modifiche per ottimizzare la costruzione dell'albero
- La produzione NonEmptyParamDeclList è stata eliminata e le sue produzioni
  sono state portate in ParamDecList;
- In Expr la produzione con FunCall è stata eliminata e sostituita direttamente
  con le produzioni del non terminale FunCall.

### Grammatica modificata
( \
N = {Program, DeclList, MainFunDecl, VarDecl, Type, IdInitList,IdInitObblList,
Const, FunDecl, Body, ParamDeclList, ParDecl, TypeOrVoid, VarDeclList, StatList,
Stat, IfStat, WhileStat, ReadStat, IdList,WriteStat, AssignStat,
FunCall, ExprList, Expr},

T = {MAIN, DEF, SEMI, COMMA, LPAR, RPAR, COLON, LBRAC, RBRAC, PIPE, OUT,
STRING, CHAR, VOID, VAR, INTEGER, BOOL, FLOAT, FOR, TO, IF, WHILE, READ, WRITE, WRITELN, THEN, LOOP, ASSIGN,
PLUS, MINUS, TIMES, DIV, AND, OR, NOT, POW, STR_CONCAT, STRING_CONST, ID,
INTEGER_CONST, REAL_CONST, CHAR_CONST, TRUE, FALSE, GT, GE, LT, LE,
EQ, NE, RETURN},

S = {Program}

P = {

P = {

    Program ::= DeclList MainFunDecl DeclList
    ;
    DeclList ::=   /* empty */          
              | VarDecl:vd DeclList
              | FunDecl:fd DeclList
    ;
    MainFunDecl ::= MAIN FunDecl
    ;
    VarDecl ::= Type IdInitList SEMI
              | VAR IdInitObblList SEMI
    ;
    Type ::=  INTEGER                                      
            | BOOL                                       
            | FLOAT                                        
            | STRING                                      
            | CHAR                                        
    ;
    IdInitList ::= ID
                | IdInitList:idl COMMA ID
                | ID ASSIGN Expr
                | IdInitList COMMA ID ASSIGN Expr
    ;
    
    IdInitObblList ::= ID ASSIGN Const
                     | IdInitObblList COMMA ID ASSIGN Const
    ;
    Const ::= INTEGER_CONST
            | REAL_CONST
            | TRUE
            | FALSE
            | STRING_CONST
            | CHAR_CONST
    ;

    FunDecl ::= DEF ID LPAR ParamDeclList RPAR COLON TypeOrVoid Body     
              |  DEF ID LPAR RPAR COLON TypeOrVoid Body
    ;
    Body ::= LBRAC VarDeclList StatList RBRAC
          | LBRAC VarDeclList RBRAC
          | LBRAC StatList RBRAC
          | LBRAC RBRAC
    ;
    ParamDeclList ::= ParDecl
                    | ParamDeclList PIPE ParDecl
    ;
    ParDecl ::= Type IdList
              | OUT Type IdList
    ;
    TypeOrVoid ::= Type                             
                 | VOID                               
    ;
    VarDeclList ::= VarDecl VarDeclList
                  | VarDecl
    ;
    StatList ::= Stat
               | Stat StatList
    ;
    Stat ::=  IfStat                        
            | ForStat                      
            | ReadStat SEMI                
            | WriteStat SEMI                
            | AssignStat SEMI              
            | WhileStat                    
            | FunCall SEMI                
            | RETURN Expr SEMI
            | RETURN SEMI
    ;
    IfStat ::= IF Expr THEN Body
             | IF Expr THEN Body ELSE Body
    ;
    WhileStat ::= WHILE Expr LOOP Body
    ;
    ForStat ::= FOR ID ASSIGN INTEGER_CONST TO INTEGER_CONST LOOP Body
    ;
    ReadStat ::= IdList READ STRING_CONST   
               | IdList READ           
    ;
    IdList ::= ID   
             | IdList COMMA ID     
    ;
    WriteStat ::= LPAR ExprList RPAR WRITE  
                | LPAR ExprList RPAR WRITELN
    ;
    AssignStat ::=  IdList ASSIGN ExprList
    ;
    FunCall ::= ID LPAR ExprList RPAR
              | ID LPAR RPAR
    ;
    ExprList ::= Expr                        
               | Expr COMMA ExprList        
    ;
    Expr ::= TRUE
           | FALSE
           | INTEGER_CONST
           | REAL_CONST
           | STRING_CONST
           | CHAR_CONST
           | ID
           | ID LPAR ExprList RPAR          
           | ID LPAR RPAR                       
           | Expr  PLUS Expr              
           | Expr  MINUS Expr				
           | Expr  TIMES Expr				
           | Expr  DIV Expr               
           | Expr  AND Expr      		
           | Expr POW Expr       			
           | Expr STR_CONCAT Expr			
           | Expr  OR Expr				
           | Expr  GT Expr             
           | Expr  GE Expr               
           | Expr  LT Expr              
           | Expr  LE Expr              
           | Expr  EQ Expr               
           | Expr  NE Expr                
           | MINUS Expr                 
           | NOT Expr                         
           | LPAR Expr RPAR                    
    ;
}
)
