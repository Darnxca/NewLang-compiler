start: def main() : void{
    integer i << 3;
    float x;
    float y;

    x << y + i;
    x, y << 2 +2, x+ 3;
    x, y << 2 +2, x+ 3;

    || x, y << 2 +2, "ciao"; || errore tipo non corrispondente
    || x, y, x << 2 +2, 2; || errore parametri assegnazione diversi numero identificatori
    || x, y << 2 +2, 2, 5; || errore parametri assegnazione diversi numero espressioni
}