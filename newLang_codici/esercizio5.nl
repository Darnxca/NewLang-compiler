def prodotto_tramite_addizione() : void{
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

def Nfibonacci(integer n): integer{
    if ( n = 0) then{
        return 0;
    }
    if ( n = 1) then{
       return 1;
    }

    return Nfibonacci(n-1) + Nfibonacci(n-2);
}


def stampaMenu() : void {
    	("Che operazione vuoi eseguire") -->!;
    	("1 - Somma di due numeri") -->!;
    	("2 - moltiplicazione di due numeri utilizzando la somma") -->!;
    	("3 - divisione intera fra due numeri positivi") -->!;
    	("4 - l’elevamento a potenza") -->!;
    	("5 - successione di fibonacci") -->!;
}

def dividi_interi(integer a,b | out integer result) : boolean{

	if (b = 0 ) then {
		result << 0;
		return false;
	}
	else {
		result << a / b;
		return true;
	}
}

start: def main() : void{
    integer  sceltaOp;
    float result, num1 <<0;
    var num2 << 0.0;
    integer continuaScelta << 1;

	stampaMenu();
	sceltaOp <-- "Scegli l'operazione da effettuare";

	while ( continuaScelta !=0 and sceltaOp >=1 and sceltaOp <=5 ) loop {


        if ( sceltaOp = 1 ) then {
            num1 <-- "Inserisci il primo numero";
            num2 <-- "Inserisci il secondo numero";
            ("La somma tra ", num1, " e ", num2, " e' ", num1 + num2) -->!;
        }
        if ( sceltaOp = 2 ) then {
            prodotto_tramite_addizione();
        }
        if ( sceltaOp = 3) then {
            integer result;
            num1 <-- "Inserisci il primo numero";
            num2 <-- "Inserisci il secondo numero";
            if( num1 >= 0 and num2 >= 0) then {
                if(dividi_interi(num1, num2,result)) then {
                    ("il risultato della divisione tra ", num1, " e ", num2, " ", result) -->!;
                } else {
                    ("Attenzione denominatore uguale a 0") -->!;
                }
            } else {
                ("Errore uno dei due numeri inseriti non è un intero positivo") -->!;
            }
        }
        if ( sceltaOp = 4 ) then {
            float num1, esponente;
            num1 <-- "Inserisci numero";
            esponente <-- "Inserisci esponente ";
            (num1, "^", esponente, " e' ", num1^esponente) -->!;
        }
        if ( sceltaOp = 5 ) then {
            integer num2, esponente;
            num2 <-- "Inserisci quanti numeri della sequenza di fibonacci visualizzare";
            if(num2 <= 0) then{
                ("Errore!: numero inserito negativo oppure uguale a 0") -->!;
            } else {
                ("La sequenza di fibonacci per ", num2, " e':") -->!;
                stampaSequenzaFibonacci(num2);
            }
        }

        continuaScelta <-- "Vuoi continuare? (0: NO, 1: SI)";
        while (continuaScelta != 0 and continuaScelta != 1) loop {
            continuaScelta <-- "Inserire 0 per terminare, 1 per continuare!";
        }

        || L'utente ha scelto di continuare
        if continuaScelta = 1 then {
            stampaMenu();
        	sceltaOp <-- "Scegli loperazione da effettuare";
        }

	}

	("Il programma è terminato!!!") -->!;

}

def stampaSequenzaFibonacci(integer n): void{
    integer i << 0;
    while (i < n) loop {
       (i, " -> ",Nfibonacci(i)) -->!;
       i << i+1;
    }
}