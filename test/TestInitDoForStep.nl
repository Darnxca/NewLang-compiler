

start: def main() : integer {

    init (integer i << 1, j << 1)
    do {
    ("ciao, ") -->;
    (i)-->;
    (", ") -->;
    (j)-->!;
    }
    for(i < 20 and j >-20)
    step(i<<i+1, j<<j-1);

    init ()
    do {}
    for()
    step();

    init(var i<< 1.1)
    do{
    ("ciao, ") -->;
    (i)-->!; }
    for(i < 10.1)
    step (i<<i+1);

    return 10;

}