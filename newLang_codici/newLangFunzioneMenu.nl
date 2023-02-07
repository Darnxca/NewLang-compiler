
def stampaMenu() : void {
    	("Che operazione vuoi eseguire") -->!;
    	("2 - Somma") -->!;
    	("3 - Sottrazione") -->!;
    	("4 - Moltiplicazione") -->!;
    	("5 - Divisione") -->!;
}

start: def main(): integer {

	integer  sceltaOp, continuaScelta << 1;
    float result, num1 <<0;
    var num2 << 0.0;
    var ciao << "CISIDIIDI";
    integer casa << 2 + (2 + 2.2 * (2 / -1));

	stampaMenu();
	sceltaOp <-- "Scegli l'operazione da effettuare";

	while (continuaScelta != 0 and (sceltaOp > 1  and sceltaOp < 6 )) loop {

		num1 <-- "Inserisci il primo numero";
		num2 <-- "Inserisci il secondo numero";

		if ( sceltaOp = 2 ) then {
			somma(num1, num2, result);
		}
		if ( sceltaOp = 3 ) then {

			sottrai(num1, num2, result);
		}
		if ( sceltaOp = 4 ) then {

			moltiplica(num1, num2, result);
		}
		if ( sceltaOp = 5 ) then {

			|* Se true, tutto okay
			   altrimenti, errore (divisione per zero)
            |
			if(not dividi(num1, num2, result)) then {
				("Errore operazione di divisione non effettuabile!!!"&"dsfs") -->;
			}

		}

		("Il risultato dell'operazione scelta tra " , num1 , num2 ,"è" , result) -->;
		continuaScelta <-- "Vuoi continuare? (0: NO, 1: SI)";
		while (continuaScelta != 0) and (continuaScelta != 1) loop {

			continuaScelta <-- "Inserire 0 per terminare, 1 per continuare!";

		}

		|| L'utente ha scelto di continuare
		if continuaScelta = 1 then {
			stampaMenu();
			sceltaOp <-- "Scegli loperazione da effettuare";
		}
	}

	return 0;
}

def somma(float a,b | out float result) : float {
    integer plal << 2, x << 3;
    float l, ldl, wdw;
    float ez;

	result << a + b;

	return 2.2;
}


def sottrai(float a,b | out float result) : void {

	result << a - b;

}


def moltiplica(float a,b | out float result) : void {
	result << a * b;
}

def dividi(float a,b | out float result) : boolean{

	if (b = 0 ) then {
		result << 0;
		return false;
	}
	else {
		result << a / b;
		return true;
	}

	return false;
}

integer x <<y;
integer y << x;
||string a << "S" & "&" & "&" & "casa" & "&2222" & "&fefwfw";