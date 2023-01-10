
float pippo1, lsl << 3;
float pippo2;

def stampaNNumeri(integer n) : string {
    string ciao << "CIAO";
    string ccciao << "CIAO";
    integer i << 1;
    boolean okay << true;

    while i <= n or okay loop {
        okay << false;

        if i != i / n then {
           string ciao << "CIAO";
           (i) -->!;
           i << i+1;
        }
        else
        {
           string ciao << "CIAO";
           okay << true;
        }
    }

    return ciao;
}


start: def main() : void {

    integer x;
    string p << "CIAOOO!";
    x <-- "Inserisci quanti numeri vuoi stampare";

    if x = 2 or x < 0 then {
        integer quanti << 1000000;
        integer p;

        x << quanti; ||Scherzetto!
        stampaNNumeri(x);
        stampaParole(quanti);
    }
    else {
        integer quanti << x;
        stampaNNumeri(quanti);
        stampaParole(quanti);
    }

}

def stampaSciocchezze(integer n | out integer volte) : void {

    for i << 0 to 101010 loop {
        string x << "1"&"volta";
        boolean fr << true;
        fr << not fr;
        volte << volte + i;
        (x) -->!;
    }
}

def stampaParole(integer quante) : void {
    string paroleACaso;

    integer i << 0;
    while i < quante loop {
        string parola << "parola";
        (parola) -->!;
        i << i+1;
    }


}

float pippo << 10, c << 3+2 , kasa;