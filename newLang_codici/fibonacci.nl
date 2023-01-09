def Nfibonacci(integer n): integer{
    if ( n = 0) then{
        return 0;
    }
    if ( n = 1) then{
       return 1;
    }

    return Nfibonacci(n-1) + Nfibonacci(n-2);
}

start: def main() : void{
    integer n << 7;
    ("Il ", n, " numero di fibonacci e'",Nfibonacci(n)) -->!;
    ("La sequenza di fibonacci per ", n, " e':") -->!;
    stampaSequenzaFibonacci(7);
}

def stampaSequenzaFibonacci(integer n): void{
    integer i << 0;
    while (i < n) loop {
       (i, " -> ",Nfibonacci(i)) -->!;
       i << i+1;
    }
}