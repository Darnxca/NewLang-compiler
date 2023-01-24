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
// inizializzazione delle variabili globali

void initialize_global() {
}

//-----------Implementazione funzioni-----------
void prodotto_tramite_addizione ( ){
	// Dichiarazione variabili
	float num1;
	float totale;
	float num2;
	float tmp1;
	float tmp2;
	int i;
	// Assegnazione variabili
	totale = 0;
	i = 0;

	// Inizio Statement
	printf("Inserisci primo numero: \n");
	fflush(stdout);
	scanf("%f", &num1);
	printf("Inserisci secondo numero: \n");
	fflush(stdout);
	scanf("%f", &num2);
	if(((num1 < 0) && (num2 < 0))){


		// Inizio Statement
		tmp1 = (num1 * ( -1));
		tmp2 = (num2 * ( -1));

	}
	else{


		// Inizio Statement
		tmp1 = num1;
		tmp2 = num2;

	}
	if((tmp2 >= 0)){


		// Inizio Statement
		while((i < tmp2)){


			// Inizio Statement
			totale = (totale + tmp1);
			i = (i + 1);

		}

	}
	else{


		// Inizio Statement
		while((i < tmp1)){


			// Inizio Statement
			totale = (totale + tmp2);
			i = (i + 1);

		}

	}
	printf("%s %f %s %f %s %f\n","Il prodotto tra ", num1, "e", num2, " è ", totale);
	fflush(stdout);

}
int Nfibonacci (int n ){


	// Inizio Statement
	if((n == 0)){


		// Inizio Statement
		return 0;

	}
	if((n == 1)){


		// Inizio Statement
		return 1;

	}
	return (Nfibonacci((n - 1)) + Nfibonacci((n - 2)));

}
void stampaMenu ( ){


	// Inizio Statement
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
int dividi_interi (int a, int b, int *result ){


	// Inizio Statement
	if((b == 0)){


		// Inizio Statement
		*result = 0;
		return 0;

	}
	else{


		// Inizio Statement
		*result = (a / b);
		return 1;

	}

}
void stampaSequenzaFibonacci (int n ){
	// Dichiarazione variabili
	int i;
	// Assegnazione variabili
	i = 0;

	// Inizio Statement
	while((i < n)){


		// Inizio Statement
		printf("%d %s %d\n",i, " -> ", Nfibonacci(i));
		fflush(stdout);
		i = (i + 1);

	}

}
void main ( ){
	initialize_global();
	// Dichiarazione variabili
	int continuaScelta;
	float num1;
	float num2;
	int sceltaOp;
	float result;
	// Assegnazione variabili
	num1 = 0;
	continuaScelta = 1;
	num2 = 0.0;

	// Inizio Statement
	stampaMenu();
	printf("Scegli l'operazione da effettuare\n");
	fflush(stdout);
	scanf("%d", &sceltaOp);
	while((((continuaScelta != 0) && (sceltaOp >= 1)) && (sceltaOp <= 5))){


		// Inizio Statement
		if((sceltaOp == 1)){


			// Inizio Statement
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


			// Inizio Statement
			prodotto_tramite_addizione();

		}
		if((sceltaOp == 3)){
			// Dichiarazione variabili
			int result;
			// Assegnazione variabili

			// Inizio Statement
			printf("Inserisci il primo numero\n");
			fflush(stdout);
			scanf("%f", &num1);
			printf("Inserisci il secondo numero\n");
			fflush(stdout);
			scanf("%f", &num2);
			if(((num1 >= 0) && (num2 >= 0))){


				// Inizio Statement
				if(dividi_interi(num1, num2, &result)){


					// Inizio Statement
					printf("%s %f %s %f %s %d\n","il risultato della divisione tra ", num1, " e ", num2, " ", result);
					fflush(stdout);

				}
				else{


					// Inizio Statement
					printf("%s\n","Attenzione denominatore uguale a 0");
					fflush(stdout);

				}

			}
			else{


				// Inizio Statement
				printf("%s\n","Errore uno dei due numeri inseriti non è un intero positivo");
				fflush(stdout);

			}

		}
		if((sceltaOp == 4)){
			// Dichiarazione variabili
			float num1;
			float esponente;
			// Assegnazione variabili

			// Inizio Statement
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
			// Assegnazione variabili

			// Inizio Statement
			printf("Inserisci quanti numeri della sequenza di fibonacci visualizzare\n");
			fflush(stdout);
			scanf("%d", &num2);
			if((num2 <= 0)){


				// Inizio Statement
				printf("%s\n","Errore!: numero inserito negativo oppure uguale a 0");
				fflush(stdout);

			}
			else{


				// Inizio Statement
				printf("%s %d %s\n","La sequenza di fibonacci per ", num2, " e':");
				fflush(stdout);
				stampaSequenzaFibonacci(num2);

			}

		}
		printf("Vuoi continuare? (0: NO, 1: SI)\n");
		fflush(stdout);
		scanf("%d", &continuaScelta);
		while(((continuaScelta != 0) && (continuaScelta != 1))){


			// Inizio Statement
			printf("Inserire 0 per terminare, 1 per continuare!\n");
			fflush(stdout);
			scanf("%d", &continuaScelta);

		}
		if((continuaScelta == 1)){


			// Inizio Statement
			stampaMenu();
			printf("Scegli loperazione da effettuare\n");
			fflush(stdout);
			scanf("%d", &sceltaOp);

		}

	}
	printf("%s\n","Il programma è terminato!!!");
	fflush(stdout);

}
