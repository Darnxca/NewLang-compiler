#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

// prototipi delle funzioni
void prodotto_tramite_addizione();
int ottieni_dopo_virgola(int i,int num);

// inizializzazione delle variabili

//-----------Implementazione funzioni-----------
int ottieni_dopo_virgola (int i, int num ){
	// Dichiarazione variabili
	int num_virgola = (i - num);
	return num_virgola;
}
int main(int argc, char *argv[]){
	prodotto_tramite_addizione();
}
void prodotto_tramite_addizione ( ){
	// Dichiarazione variabili
	float num1;
	float num2;
	float totale = 0;
	int i = 0;
	printf("Inserisci primo numero: \n");
	fflush(stdout);
	scanf("%f", &num1);
	printf("Inserisci secondo numero: \n");
	fflush(stdout);
	scanf("%f", &num2);
	while((i < num2)){
		// Dichiarazione variabili
		totale = (totale + num1);
		i = (i + 1);
	}
	if((i != num2)){
		// Dichiarazione variabili
		totale = (totale * ottieni_dopo_virgola(i, num2));
	}
	printf("%s %f %s %f %s %f\n","Il prodotto tra ", num1, "e", num2, " è ", totale);
	fflush(stdout);
}
