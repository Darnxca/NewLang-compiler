
float pippo1, lsl << 3;
float pippo2;

def stampaNNumeri(integer n) : void {
    integer i << 1;
    boolean okay << true;
    while i <= n or okay loop {
        okay << false;

        if i != i / n then {
           (i) -->!;
           i << i+1;
        }
        else
        {
           okay << true;
        }
    }

    return;
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
    }
    else {
        integer quanti << x;

        stampaNNumeri(quanti);
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


float pippo << 10, c << 3+2 , kasa;