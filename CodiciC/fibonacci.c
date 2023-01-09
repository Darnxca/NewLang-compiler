#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

// prototipi delle funzioni
void stampaSequenzaFibonacci(int n);
int Nfibonacci(int n);
void main();

// inizializzazione delle variabili

//-----------Implementazione funzioni-----------
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
	int n = 7;
	printf("%s %d %s %d\n","Il ", n, " numero di fibonacci e'", Nfibonacci(n));
	fflush(stdout);
	printf("%s %d %s\n","La sequenza di fibonacci per ", n, " e':");
	fflush(stdout);
	stampaSequenzaFibonacci(7);
}
