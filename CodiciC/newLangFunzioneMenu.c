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

// inizializzazione delle variabili

//-----------Implementazione funzioni-----------
void stampaMenu ( ){
	// Dichiarazione variabili
	int correctInputCheck = 0;
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
float somma (float a, float b, float * result ){
	// Dichiarazione variabili
	int correctInputCheck = 0;
	float l;
	float ldl;
	float wdw;
	float ez;
	int plal = 2;
	int x = 3;
	* result = (a + b);
	return 2.2;
}
void sottrai (float a, float b, float * result ){
	// Dichiarazione variabili
	int correctInputCheck = 0;
	* result = (a - b);
}
void moltiplica (float a, float b, float * result ){
	// Dichiarazione variabili
	int correctInputCheck = 0;
	* result = (a * b);
}
int dividi (float a, float b, float * result ){
	// Dichiarazione variabili
	int correctInputCheck = 0;
	if((b == 0)){
		// Dichiarazione variabili
		int correctInputCheck = 0;
		* result = 0;
		return 0;
	}
	else{
		// Dichiarazione variabili
		int correctInputCheck = 0;
		* result = (a / b);
		return 1;
	}
}
int main ( ){
	// Dichiarazione variabili
	int correctInputCheck = 0;
	int sceltaOp;
	float result;
	int continuaScelta = 1;
	float num1 = 0;
	float num2 = 0.0;
	char*  ciao = "CISIDIIDI";
	int casa = (2 + (2 + (2.2 * (2 / ( -1)))));
	stampaMenu();
	printf("Scegli l'operazione da effettuare\n");
	fflush(stdout);
	scanf("%d", &sceltaOp);
	while(((continuaScelta != 0) && ((sceltaOp > 1) && (sceltaOp < 6)))){
		// Dichiarazione variabili
		int correctInputCheck = 0;
		printf("Inserisci il primo numero\n");
		fflush(stdout);
		scanf("%f", &num1);
		printf("Inserisci il secondo numero\n");
		fflush(stdout);
		scanf("%f", &num2);
		if((sceltaOp == 2)){
			// Dichiarazione variabili
			int correctInputCheck = 0;
			somma(num1, num2, &result);
		}
		if((sceltaOp == 3)){
			// Dichiarazione variabili
			int correctInputCheck = 0;
			sottrai(num1, num2, &result);
		}
		if((sceltaOp == 4)){
			// Dichiarazione variabili
			int correctInputCheck = 0;
			moltiplica(num1, num2, &result);
		}
		if((sceltaOp == 5)){
			// Dichiarazione variabili
			int correctInputCheck = 0;
			if(( !dividi(num1, num2, &result))){
				// Dichiarazione variabili
				int correctInputCheck = 0;
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
			// Dichiarazione variabili
			int correctInputCheck = 0;
			printf("Inserire 0 per terminare, 1 per continuare!\n");
			fflush(stdout);
			scanf("%d", &continuaScelta);
		}
		if((continuaScelta == 1)){
			// Dichiarazione variabili
			int correctInputCheck = 0;
			stampaMenu();
			printf("Scegli loperazione da effettuare\n");
			fflush(stdout);
			scanf("%d", &sceltaOp);
		}
	}
}
