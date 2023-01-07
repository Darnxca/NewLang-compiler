#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

// prototipi delle funzioni
void main();

// inizializzazione delle variabili

//-----------Implementazione funzioni-----------
void main ( ){
	// Dichiarazione variabili
	int correctInputCheck = 0;
	int x;
	char*  b;
	printf("inserisci un intero e una stringa\n");
	fflush(stdout);
	b = malloc(20*sizeof(char));
	correctInputCheck = scanf("%d %s", &x, b);
	if (correctInputCheck != 2) {printf("si è verificato un errore, riprovare"); exit(1);}
}
