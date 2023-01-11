#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

// prototipi delle funzioni
void sottrai(float a,float b,float *result);
float somma(float a,float b,float *result);
int dividi(float a,float b,float *result);
void stampaMenu();
void moltiplica(float a,float b,float *result);
int main();
// inizializzazione delle variabili globali
int y;
int x;

void initialize_global() {
	x = y;
	y = x;
}

//-----------Implementazione funzioni-----------
void stampaMenu ( ){


	// Inizio Statement
	printf("%s\n","Che operazione vuoi eseguire");
	fflush(stdout);
	printf("%s\n","2 - Somma");
	fflush(stdout);
	printf("%s\n","3 - Sottrazione");
	fflush(stdout);
	printf("%s\n","4 - Moltiplicazione");
	fflush(stdout);
	printf("%s\n","5 - Divisione");
	fflush(stdout);

}
float somma (float a, float b, float *result ){
	// Dichiarazione variabili
	float ldl;
	float wdw;
	int plal;
	int x;
	float ez;
	float l;
	// Assegnazione variabili
	plal = 2;
	x = 3;

	// Inizio Statement
	*result = (a + b);
	return 2.2;

}
void sottrai (float a, float b, float *result ){


	// Inizio Statement
	*result = (a - b);

}
void moltiplica (float a, float b, float *result ){


	// Inizio Statement
	*result = (a * b);

}
int dividi (float a, float b, float *result ){


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
int main ( ){
	initialize_global();
	// Dichiarazione variabili
	int continuaScelta;
	int casa;
	float num1;
	float num2;
	int sceltaOp;
	char*  ciao;
	float result;
	// Assegnazione variabili
	continuaScelta = 1;
	num1 = 0;
	num2 = 0.0;
	ciao = "CISIDIIDI";

	casa = (2 + (2 + (2.2 * (2 / ( -1)))));
	// Inizio Statement
	stampaMenu();
	printf("Scegli l'operazione da effettuare\n");
	fflush(stdout);
	scanf("%d", &sceltaOp);
	while(((continuaScelta != 0) && ((sceltaOp > 1) && (sceltaOp < 6)))){


		// Inizio Statement
		printf("Inserisci il primo numero\n");
		fflush(stdout);
		scanf("%f", &num1);
		printf("Inserisci il secondo numero\n");
		fflush(stdout);
		scanf("%f", &num2);
		if((sceltaOp == 2)){


			// Inizio Statement
			somma(num1, num2, &result);

		}
		if((sceltaOp == 3)){


			// Inizio Statement
			sottrai(num1, num2, &result);

		}
		if((sceltaOp == 4)){


			// Inizio Statement
			moltiplica(num1, num2, &result);

		}
		if((sceltaOp == 5)){


			// Inizio Statement
			if(( !dividi(num1, num2, &result))){


				// Inizio Statement
				printf("%s","Errore operazione di divisione non effettuabile!!!");
				fflush(stdout);

			}

		}
		printf("%s %f %f %s %f","Il risultato dell'operazione scelta tra ", num1, num2, "è", result);
		fflush(stdout);
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

}
