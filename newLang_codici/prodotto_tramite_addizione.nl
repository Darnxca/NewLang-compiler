start: def prodotto_tramite_addizione() : void{
    float num1, num2,tmp1,tmp2;
    float totale << 0;
    integer i << 0;

    num1 <-- "Inserisci primo numero: ";
    num2 <-- "Inserisci secondo numero: ";

    if( num1 <0 and num2 <0) then { tmp1 << num1 * -1; tmp2 << num2 * -1;}
    else {tmp1 << num1; tmp2 << num2;}
    if( tmp2 >= 0) then {
        while i < tmp2 loop {
            totale << totale + tmp1;
            i << i + 1;
        }
    } else {
        while i < tmp1 loop {
            totale << totale + tmp2;
            i << i + 1;
        }
    }

    ("Il prodotto tra ", num1, "e", num2, " è ", totale) -->!;
}
