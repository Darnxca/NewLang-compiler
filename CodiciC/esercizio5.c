#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

// prototipi delle funzioni
void prodotto_tramite_addizione();
void stampaMenu();
void stampaSequenzaFibonacci(int n);
int Nfibonacci(int n);
void main();
int dividi_interi(int a,int b,int *result);

// inizializzazione delle variabili

//-----------Implementazione funzioni-----------
void prodotto_tramite_addizione ( ){
	// Dichiarazione variabili
	float num1;
	float num2;
	float tmp1;
	float tmp2;
	float totale = 0;
	int i = 0;
	printf("Inserisci primo numero: \n");
	fflush(stdout);
	scanf("%f", &num1);
	printf("Inserisci secondo numero: \n");
	fflush(stdout);
	scanf("%f", &num2);
	if(((num1 < 0) && (num2 < 0))){
		// Dichiarazione variabili
		tmp1 = (num1 * ( -1));
		tmp2 = (num2 * ( -1));
	}
	else{
		// Dichiarazione variabili
		tmp1 = num1;
		tmp2 = num2;
	}
	if((tmp2 >= 0)){
		// Dichiarazione variabili
		while((i < tmp2)){
			// Dichiarazione variabili
			totale = (totale + tmp1);
			i = (i + 1);
		}
	}
	else{
		// Dichiarazione variabili
		while((i < tmp1)){
			// Dichiarazione variabili
			totale = (totale + tmp2);
			i = (i + 1);
		}
	}
	printf("%s %f %s %f %s %f\n","Il prodotto tra ", num1, "e", num2, " è ", totale);
	fflush(stdout);
}
int Nfibonacci (int n ){
	// Dichiarazione variabili
	if((n == 0)){
		// Dichiarazione variabili
		return 0;
	}
	if((n == 1)){
		// Dichiarazione variabili
		return 1;
	}
	return (Nfibonacci((n - 1)) + Nfibonacci((n - 2)));
}
void stampaMenu ( ){
	// Dichiarazione variabili
	printf("%s\n","Che operazione vuoi eseguire");
	fflush(stdout);
	printf("%s\n","1 - Somma di due numeri");
	fflush(stdout);
	printf("%s\n","2 - moltiplicazione di due numeri utilizzando la somma");
	fflush(stdout);
	printf("%s\n","3 - divisione intera fra due numeri positivi");
	fflush(stdout);
	printf("%s\n","4 - l’elevamento a potenza");
	fflush(stdout);
	printf("%s\n","5 - successione di fibonacci");
	fflush(stdout);
}
int dividi_interi (int a, int b, int * result ){
	// Dichiarazione variabili
	if((b == 0)){
		// Dichiarazione variabili
		* result = 0;
		return 0;
	}
	else{
		// Dichiarazione variabili
		* result = (a / b);
		return 1;
	}
}
void stampaSequenzaFibonacci (int n ){
	// Dichiarazione variabili
	int i = 0;
	while((i < n)){
		// Dichiarazione variabili
		printf("%d %s %d\n",i, " -> ", Nfibonacci(i));
		fflush(stdout);
		i = (i + 1);
	}
}
void main ( ){
	// Dichiarazione variabili
	int sceltaOp;
	float result;
	float num1 = 0;
	int continuaScelta = 1;
	float num2 = 0.0;
	stampaMenu();
	printf("Scegli l'operazione da effettuare\n");
	fflush(stdout);
	scanf("%d", &sceltaOp);
	while((((continuaScelta != 0) && (sceltaOp >= 1)) && (sceltaOp <= 5))){
		// Dichiarazione variabili
		if((sceltaOp == 1)){
			// Dichiarazione variabili
			printf("Inserisci il primo numero\n");
			fflush(stdout);
			scanf("%f", &num1);
			printf("Inserisci il secondo numero\n");
			fflush(stdout);
			scanf("%f", &num2);
			printf("%s %f %s %f %s %f\n","La somma tra ", num1, " e ", num2, " e' ", (num1 + num2));
			fflush(stdout);
		}
		if((sceltaOp == 2)){
			// Dichiarazione variabili
			prodotto_tramite_addizione();
		}
		if((sceltaOp == 3)){
			// Dichiarazione variabili
			int result;
			printf("Inserisci il primo numero\n");
			fflush(stdout);
			scanf("%f", &num1);
			printf("Inserisci il secondo numero\n");
			fflush(stdout);
			scanf("%f", &num2);
			if(((num1 >= 0) && (num2 >= 0))){
				// Dichiarazione variabili
				if(dividi_interi(num1, num2, &result)){
					// Dichiarazione variabili
					printf("%s %f %s %f %s %d\n","il risultato della divisione tra ", num1, " e ", num2, " ", result);
					fflush(stdout);
				}
				else{
					// Dichiarazione variabili
					printf("%s\n","Attenzione denominatore uguale a 0");
					fflush(stdout);
				}
			}
			else{
				// Dichiarazione variabili
				printf("%s\n","Errore uno dei due numeri inseriti non è un intero positivo");
				fflush(stdout);
			}
		}
		if((sceltaOp == 4)){
			// Dichiarazione variabili
			float num1;
			float esponente;
			printf("Inserisci numero\n");
			fflush(stdout);
			scanf("%f", &num1);
			printf("Inserisci esponente \n");
			fflush(stdout);
			scanf("%f", &esponente);
			printf("%f %s %f %s %f\n",num1, "^", esponente, " e' ", pow(num1,esponente));
			fflush(stdout);
		}
		if((sceltaOp == 5)){
			// Dichiarazione variabili
			int num2;
			int esponente;
			printf("Inserisci quanti numeri della sequenza di fibonacci visualizzare\n");
			fflush(stdout);
			scanf("%d", &num2);
			if((num2 <= 0)){
				// Dichiarazione variabili
				printf("%s\n","Errore!: numero inserito negativo oppure uguale a 0");
				fflush(stdout);
			}
			else{
				// Dichiarazione variabili
				printf("%s %d %s\n","La sequenza di fibonacci per ", num2, " e':");
				fflush(stdout);
				stampaSequenzaFibonacci(num2);
			}
		}
		printf("Vuoi continuare? (0: NO, 1: SI)\n");
		fflush(stdout);
		scanf("%d", &continuaScelta);
		while(((continuaScelta != 0) && (continuaScelta != 1))){
			// Dichiarazione variabili
			printf("Inserire 0 per terminare, 1 per continuare!\n");
			fflush(stdout);
			scanf("%d", &continuaScelta);
		}
		if((continuaScelta == 1)){
			// Dichiarazione variabili
			stampaMenu();
			printf("Scegli loperazione da effettuare\n");
			fflush(stdout);
			scanf("%d", &sceltaOp);
		}
	}
	printf("%s\n","Il programma è terminato!!!");
	fflush(stdout);
}
