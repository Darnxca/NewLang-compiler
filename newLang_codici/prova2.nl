
start: def main() : integer {
    var i << 1;
    while i<3 loop {
        (-i) -->;
        (" ") -->;
        i << i +1;
    }
    elseloop (string messaggio << "ciao"; var j << 1;) {
        (i)-->;
        (" ") -->;
        i << i + 1;
        j << j +1;

        if i=5 then{
            i << 0;
            (messaggio)-->!;
        }
    } when( j <3); || condizione nascosta del ciclo elseloop è i >= 3

}