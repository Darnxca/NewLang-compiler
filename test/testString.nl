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

start: def test(): void{
    string  p;
    string  c << "s";

    funz1(p);

    ("Funz1 eseguita: la variabile 'p' vale: ", p) -->!;

    ("Funz2 in esecuzione: la variabile 'c' vale: ", c) -->!;
    c << funz2(p);

    ("Funz2 eseguita: la variabile 'p' vale: ", p, "mentre la variabile 'c' vale: ", c) -->!;

    funz3(p);

    ("Funz3 eseguita: la variabile 'p' vale: ", p) -->!;
    ("pow: ", 2^2) -->!;

    p <-- "Inserisci una stringa in 'p'";
    ("Hai inserito: ", p) -->!;
}

def funz2(out string p) : string{
     integer da;
     string d << " car";
     p << "ciao";
     p << d;
     return d;
}