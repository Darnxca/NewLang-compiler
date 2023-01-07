def funz1(out string p) : string{
     integer da;
     p , da , p<< "ciao", 5, "carmine";
}

def funz3(out string p) : string{
     integer da;
     string d << " car";
     p << "ciao";
     p << p & d;
}

var dkdkd << "clclc";
string stringa;

start: def test(): void{
    string  p << "a";
    string pa;
    string  c << "a";
    boolean isEq, isGt, isLt, isLe, isGe, isNe;
    string concatenata;

    |* funz1(p);

    ("Funz1 eseguita: la variabile 'p' vale: ", p) -->!;

    ("Funz2 in esecuzione: la variabile 'c' vale: ", c) -->!;
    c << funz2(p);

    ("Funz2 eseguita: la variabile 'p' vale: ", p, "mentre la variabile 'c' vale: ", c) -->!;

    funz3(p);

    ("Funz3 eseguita: la variabile 'p' vale: ", p) -->!;
    ("pow: ", 2^2) -->!;

    p <-- "Inserisci una stringa in 'p'";
    ("Hai inserito: ", p) -->!;
    *|
    concatenata << compareString(c, p, isEq, isGt, isLt, isGe, isLe, isNe);
    (isEq) -->!;
    (isGt) -->!;
    (isLt) -->!;
    (isGe) -->!;
    (isLe) -->!;
    (isNe) -->!;
    (concatenata) -->!;
    (dkdkd&"ciaooooooo") -->!;

}

def funz2(out string p) : string{
     integer da;
     string d << " car";
     p << "ciao";
     p << d;
     return d;
}

def compareString(string a, b | out boolean isEq, isGt, isLt, isGe, isLe, isNe) : string {

    isEq << a = b;
    isGt << a > b;
    isLt << a < b;
    isGe << a >= b;
    isLe << a <= b;
    isNe << a != b;

    return a&b;

}